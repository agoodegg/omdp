package com.omdp.webapp.security;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.security.Authentication;
import org.springframework.security.acls.AccessControlEntry;
import org.springframework.security.acls.Acl;
import org.springframework.security.acls.AlreadyExistsException;
import org.springframework.security.acls.ChildrenExistException;
import org.springframework.security.acls.MutableAcl;
import org.springframework.security.acls.MutableAclService;
import org.springframework.security.acls.NotFoundException;
import org.springframework.security.acls.domain.AccessControlEntryImpl;
import org.springframework.security.acls.jdbc.AclCache;
import org.springframework.security.acls.jdbc.JdbcAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.objectidentity.ObjectIdentity;
import org.springframework.security.acls.objectidentity.ObjectIdentityImpl;
import org.springframework.security.acls.sid.GrantedAuthoritySid;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.security.acls.sid.Sid;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;


public class RoleBaseMutableAclService extends JdbcAclService implements MutableAclService{

	//~ Instance fields ================================================================================================

	private boolean foreignKeysInDatabase = true;
    private AclCache aclCache;
    private String deleteEntryByObjectIdentityForeignKey = "delete from acl_entry where acl_object_identity=?";
    private String deleteObjectIdentityByPrimaryKey = "delete from acl_object_identity where id=?";
    private String classIdentityQuery = "call identity()"; // should be overridden for postgres : select currval('acl_class_seq')
    private String sidIdentityQuery = "call identity()"; // should be overridden for postgres : select currval('acl_siq_seq')
    private String insertClass = "insert into acl_class (id, class) values (?, ?)";
    private String insertEntry = "insert into acl_entry "
        + "(id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)"
        + "values (SEQ_ACL_ENTRY.nextval, ?, ?, ?, ?, ?, ?, ?)";
    private String insertObjectIdentity = "insert into acl_object_identity "
        + "(id, object_id_class, object_id_identity, owner_sid, entries_inheriting) " + "values (SEQ_ACL_OBJECT_IDENTITY.nextval, ?, ?, ?, ?)";
    private String insertSid = "insert into acl_sid (id, principal, sid) values (?, ?, ?)";
    private String selectClassPrimaryKey = "select id from acl_class where class=?";
    private String selectObjectIdentityPrimaryKey = "select acl_object_identity.id from acl_object_identity, acl_class "
        + "where acl_object_identity.object_id_class = acl_class.id and acl_class.class=? "
        + "and acl_object_identity.object_id_identity = ?";
    private String selectSidPrimaryKey = "select id from acl_sid where principal=? and sid=?";
    private String updateObjectIdentity = "update acl_object_identity set "
        + "parent_object = ?, owner_sid = ?, entries_inheriting = ?" + " where id = ?";
    
    private String getNextSidId = "select SEQ_ACL_SID.nextval from dual";
    private String getNextAclClassId = "select SEQ_ACL_CLASS_ID.nextval from dual";

    //~ Constructors ===================================================================================================

    public RoleBaseMutableAclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache) {
    	super(dataSource, lookupStrategy);
        Assert.notNull(aclCache, "AclCache required");
        this.aclCache = aclCache;
    }

    //~ Methods ========================================================================================================

    public MutableAcl createAcl(ObjectIdentity objectIdentity)
        throws AlreadyExistsException {
        Assert.notNull(objectIdentity, "Object Identity required");

        // Check this object identity hasn't already been persisted
        if (retrieveObjectIdentityPrimaryKey(objectIdentity) != null) {
            throw new AlreadyExistsException("Object identity '" + objectIdentity + "' already exists");
        }

        // Need to retrieve the current principal, in order to know who "owns" this ACL (can be changed later on)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalSid sid = new PrincipalSid("1");

        // Create the acl_object_identity row
        createObjectIdentity(objectIdentity, sid);

        // Retrieve the ACL via superclass (ensures cache registration, proper retrieval etc)
        Acl acl = readAclById(objectIdentity);
        Assert.isInstanceOf(MutableAcl.class, acl, "MutableAcl should be been returned");

        return (MutableAcl) acl;
    }

    /**
     * Creates a new row in acl_entry for every ACE defined in the passed MutableAcl object.
     *
     * @param acl containing the ACEs to insert
     */
    protected void createEntries(final MutableAcl acl) {
    	
        jdbcTemplate.batchUpdate(insertEntry,
            new BatchPreparedStatementSetter() {
                public int getBatchSize() {
                    return acl.getEntries().length;
                }

                public void setValues(PreparedStatement stmt, int i)
                        throws SQLException {
                    AccessControlEntry entry_ = (AccessControlEntry) Array.get(acl.getEntries(), i);
                    Assert.isTrue(entry_ instanceof AccessControlEntryImpl, "Unknown ACE class");

                    AccessControlEntryImpl entry = (AccessControlEntryImpl) entry_;

                    stmt.setLong(1, ((Long) acl.getId()).longValue());
                    stmt.setInt(2, i);
                    stmt.setLong(3, createOrRetrieveSidPrimaryKey(entry.getSid(), true).longValue());
                    stmt.setInt(4, entry.getPermission().getMask());
                    stmt.setBoolean(5, entry.isGranting());
                    stmt.setBoolean(6, entry.isAuditSuccess());
                    stmt.setBoolean(7, entry.isAuditFailure());
                }
            });
    }

    /**
     * Creates an entry in the acl_object_identity table for the passed ObjectIdentity. The Sid is also
     * necessary, as acl_object_identity has defined the sid column as non-null.
     *
     * @param object to represent an acl_object_identity for
     * @param owner for the SID column (will be created if there is no acl_sid entry for this particular Sid already)
     */
    protected void createObjectIdentity(ObjectIdentity object, Sid owner) {
        Long sidId = createOrRetrieveSidPrimaryKey(owner, true);
        Long classId = createOrRetrieveClassPrimaryKey(object.getJavaType(), true);
        jdbcTemplate.update(insertObjectIdentity,
            new Object[] {classId, object.getIdentifier().toString(), sidId, new Boolean(true)});
    }

    /**
     * Retrieves the primary key from acl_class, creating a new row if needed and the allowCreate property is
     * true.
     *
     * @param clazz to find or create an entry for (this implementation uses the fully-qualified class name String)
     * @param allowCreate true if creation is permitted if not found
     *
     * @return the primary key or null if not found
     */
    protected Long createOrRetrieveClassPrimaryKey(Class clazz, boolean allowCreate) {
        List classIds = jdbcTemplate.queryForList(selectClassPrimaryKey, new Object[] {clazz.getName()}, Long.class);
        Long classId = null;

        if (classIds.isEmpty()) {
            if (allowCreate) {
                classId = new Long(jdbcTemplate.queryForLong(getNextAclClassId));
                jdbcTemplate.update(insertClass, new Object[] {classId, clazz.getName()});
                Assert.isTrue(TransactionSynchronizationManager.isSynchronizationActive(),
                        "Transaction must be running");
                //classId = new Long(jdbcTemplate.queryForLong(getNextAclClassId));
            }
        } else {
            classId = (Long) classIds.iterator().next();
        }

        return classId;
    }
    
    

    /**
     * Retrieves the primary key from acl_sid, creating a new row if needed and the allowCreate property is
     * true.
     *
     * @param sid to find or create
     * @param allowCreate true if creation is permitted if not found
     *
     * @return the primary key or null if not found
     *
     * @throws IllegalArgumentException DOCUMENT ME!
     */
    protected Long createOrRetrieveSidPrimaryKey(Sid sid, boolean allowCreate) {
        Assert.notNull(sid, "Sid required");

        String sidName = null;
        boolean principal = true;

        if (sid instanceof PrincipalSid) {
            sidName = ((PrincipalSid) sid).getPrincipal();
        } else if (sid instanceof GrantedAuthoritySid) {
            sidName = ((GrantedAuthoritySid) sid).getGrantedAuthority();
            principal = false;
        } else {
            throw new IllegalArgumentException("Unsupported implementation of Sid");
        }

        List sidIds = jdbcTemplate.queryForList(selectSidPrimaryKey, new Object[] {new Boolean(principal), sidName},
                Long.class);
        Long sidId = null;

        if (sidIds.isEmpty()) {
            if (allowCreate) {
                sidId = new Long(jdbcTemplate.queryForLong(getNextSidId));
                jdbcTemplate.update(insertSid, new Object[] {sidId, new Boolean(principal), sidName});
                Assert.isTrue(TransactionSynchronizationManager.isSynchronizationActive(),
                        "Transaction must be running");
                //sidId = new Long(jdbcTemplate.queryForLong(sidIdentityQuery));
            }
        } else {
            sidId = (Long) sidIds.iterator().next();
        }

        return sidId;
    }
    
    /**
     * @param isTrue
     * @return int
     */
    private char convertBooleanToChar(boolean isTrue) {
    	return isTrue ? '1' : '0' ; 
    }


    public void deleteAcl(ObjectIdentity objectIdentity, boolean deleteChildren)
        throws ChildrenExistException {
        Assert.notNull(objectIdentity, "Object Identity required");
        Assert.notNull(objectIdentity.getIdentifier(), "Object Identity doesn't provide an identifier");

        if (deleteChildren) {
        	ObjectIdentity[] children = findChildren(objectIdentity);
        	if (children != null) {
            	for (int i = 0; i < children.length; i++) {
                    deleteAcl(children[i], true);
                }
        	}
        } else {
        	if (!foreignKeysInDatabase) {
        		// We need to perform a manual verification for what a FK would normally do
        		// We generally don't do this, in the interests of deadlock management
        		ObjectIdentity[] children = findChildren(objectIdentity);
        		if (children != null) {
                    throw new ChildrenExistException("Cannot delete '" + objectIdentity + "' (has " + children.length
                            + " children)");
        		}
        	}
        }

        Long oidPrimaryKey = retrieveObjectIdentityPrimaryKey(objectIdentity);
        
        // Delete this ACL's ACEs in the acl_entry table
        deleteEntries(oidPrimaryKey);

        // Delete this ACL's acl_object_identity row
        deleteObjectIdentity(oidPrimaryKey);

        // Clear the cache
        aclCache.evictFromCache(objectIdentity);
    }

    /**
     * Deletes all ACEs defined in the acl_entry table belonging to the presented ObjectIdentity primary key.
     *
     * @param oidPrimaryKey the rows in acl_entry to delete
     */
    protected void deleteEntries(Long oidPrimaryKey) {
    	jdbcTemplate.update(deleteEntryByObjectIdentityForeignKey,
                new Object[] {oidPrimaryKey});
    }

    /**
     * Deletes a single row from acl_object_identity that is associated with the presented ObjectIdentity primary key.
     * 
     * <p>
     * We do not delete any entries from acl_class, even if no classes are using that class any longer. This is a
     * deadlock avoidance approach.
     * </p>
     *
     * @param oidPrimaryKey to delete the acl_object_identity
     */
    protected void deleteObjectIdentity(Long oidPrimaryKey) {
        // Delete the acl_object_identity row
        jdbcTemplate.update(deleteObjectIdentityByPrimaryKey, new Object[] {oidPrimaryKey});
    }

    /**
     * Retrieves the primary key from the acl_object_identity table for the passed ObjectIdentity. Unlike some
     * other methods in this implementation, this method will NOT create a row (use {@link
     * #createObjectIdentity(ObjectIdentity, Sid)} instead).
     *
     * @param oid to find
     *
     * @return the object identity or null if not found
     */
    protected Long retrieveObjectIdentityPrimaryKey(ObjectIdentity oid) {
        try {
            return new Long(jdbcTemplate.queryForLong(selectObjectIdentityPrimaryKey,
                    new Object[] {oid.getJavaType().getName(), oid.getIdentifier()}));
        } catch (DataAccessException notFound) {
            return null;
        }
    }

    /**
     * This implementation will simply delete all ACEs in the database and recreate them on each invocation of
     * this method. A more comprehensive implementation might use dirty state checking, or more likely use ORM
     * capabilities for create, update and delete operations of {@link MutableAcl}.
     *
     * @param acl DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws NotFoundException DOCUMENT ME!
     */
    public MutableAcl updateAcl(MutableAcl acl) throws NotFoundException {
        Assert.notNull(acl.getId(), "Object Identity doesn't provide an identifier");

        // Delete this ACL's ACEs in the acl_entry table
        deleteEntries(retrieveObjectIdentityPrimaryKey(acl.getObjectIdentity()));

        // Create this ACL's ACEs in the acl_entry table
        createEntries(acl);

        // Change the mutable columns in acl_object_identity
        updateObjectIdentity(acl);

        // Clear the cache, including children
        clearCacheIncludingChildren(acl.getObjectIdentity());

        // Retrieve the ACL via superclass (ensures cache registration, proper retrieval etc)
        return (MutableAcl) super.readAclById(acl.getObjectIdentity());
    }
    
    private void clearCacheIncludingChildren(ObjectIdentity objectIdentity) {
    	Assert.notNull(objectIdentity, "ObjectIdentity required");
        ObjectIdentity[] children = findChildren(objectIdentity);
        if (children != null) {
        	for (int i = 0; i < children.length; i++) {
        		clearCacheIncludingChildren(children[i]);
        	}
        }
        aclCache.evictFromCache(objectIdentity);
    }

    /**
     * Updates an existing acl_object_identity row, with new information presented in the passed MutableAcl
     * object. Also will create an acl_sid entry if needed for the Sid that owns the MutableAcl.
     *
     * @param acl to modify (a row must already exist in acl_object_identity)
     *
     * @throws NotFoundException DOCUMENT ME!
     */
    protected void updateObjectIdentity(MutableAcl acl) {
        Long parentId = null;

        if (acl.getParentAcl() != null) {
            Assert.isInstanceOf(ObjectIdentityImpl.class, acl.getParentAcl().getObjectIdentity(),
                "Implementation only supports ObjectIdentityImpl");

            ObjectIdentityImpl oii = (ObjectIdentityImpl) acl.getParentAcl().getObjectIdentity();
            parentId = retrieveObjectIdentityPrimaryKey(oii);
        }

        Assert.notNull(acl.getOwner(), "Owner is required in this implementation");

        Long ownerSid = createOrRetrieveSidPrimaryKey(acl.getOwner(), true);
        int count = jdbcTemplate.update(updateObjectIdentity,
                new Object[] {parentId, ownerSid, new Boolean(acl.isEntriesInheriting()), acl.getId()});

        if (count != 1) {
            throw new NotFoundException("Unable to locate ACL to update");
        }
    }

	public void setClassIdentityQuery(String identityQuery) {
		Assert.hasText(identityQuery, "New identity query is required");
		this.classIdentityQuery = identityQuery;
	}

	public void setSidIdentityQuery(String identityQuery) {
		Assert.hasText(identityQuery, "New identity query is required");
		this.sidIdentityQuery = identityQuery;
	}
	/**
	 * @param foreignKeysInDatabase if false this class will perform additional FK constrain checking, which may
	 * cause deadlocks (the default is true, so deadlocks are avoided but the database is expected to enforce FKs)
	 */
	public void setForeignKeysInDatabase(boolean foreignKeysInDatabase) {
		this.foreignKeysInDatabase = foreignKeysInDatabase;
	}
}
