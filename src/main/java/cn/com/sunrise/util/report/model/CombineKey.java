package cn.com.sunrise.util.report.model;

/**
 * 组合键
 * @author hegang
 *
 */
public class CombineKey {
	
	private Object key1,key2,key3,key4;
	
	public CombineKey(Object key1){
		this.key1=key1;
	}

	public CombineKey(Object key1, Object key2) {
		super();
		this.key1 = key1;
		this.key2 = key2;
	}

	public CombineKey(Object key1, Object key2, Object key3) {
		super();
		this.key1 = key1;
		this.key2 = key2;
		this.key3 = key3;
	}

	public CombineKey(Object key1, Object key2, Object key3, Object key4) {
		super();
		this.key1 = key1;
		this.key2 = key2;
		this.key3 = key3;
		this.key4 = key4;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((key1 == null) ? 0 : key1.hashCode());
		result = PRIME * result + ((key2 == null) ? 0 : key2.hashCode());
		result = PRIME * result + ((key3 == null) ? 0 : key3.hashCode());
		result = PRIME * result + ((key4 == null) ? 0 : key4.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CombineKey other = (CombineKey) obj;
		if (key1 == null) {
			if (other.key1 != null)
				return false;
		} else if (!key1.equals(other.key1))
			return false;
		if (key2 == null) {
			if (other.key2 != null)
				return false;
		} else if (!key2.equals(other.key2))
			return false;
		if (key3 == null) {
			if (other.key3 != null)
				return false;
		} else if (!key3.equals(other.key3))
			return false;
		if (key4 == null) {
			if (other.key4 != null)
				return false;
		} else if (!key4.equals(other.key4))
			return false;
		return true;
	}

	
	
	
	
}
