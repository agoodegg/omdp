package cn.com.sunrise.util.report.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Sorter {
	public static final int ASCENT=1;
	public static final int DESCENT=2;
	
	private static final Sorter DefaultSorter=new Sorter();
	
	public static final Sorter getDefaultSorter(){
		return DefaultSorter;
	}
	
	public static final Sorter getSorter(int type){
		return getSorter(new Column[]{},type);
	}
	
	public static final Sorter getSorter(Column column,int type){
		return getSorter(new Column[]{column},type);
	}
	
	public static final Sorter getSorter(final Column[] columns,final int type){
		
		return new Sorter(){			
			
			public Collection sortRowKeys(final DataTable dataTable, final Collection rowKeys) {
				final ArrayList list=new ArrayList(rowKeys);
				Collections.sort(list, new Comparator(){
					public int compare(final Object o1, final Object o2){
						int result=0;
						for(int i=0;i<columns.length;i++){
							final Column column=columns[i];
							final Object v1=dataTable.getData(o1, column.getKey());
							final Object v2=dataTable.getData(o2, column.getKey());
							Comparable c=v1 instanceof Comparable ? (Comparable)v1 : column.getLabelProvider().getLabel(v1);
							c=c==null ? "" : c;
							result=Translator.translateC2E(c).compareTo(Translator.translateC2E(v2));
							if(result!=0)break;							
						}
						return DESCENT==type ?  -result : result;
					}
				});
				return list;
			}

			public Collection sortGroupKeys(Groupable group,Collection groupKeys) {
				final ArrayList list=new ArrayList(groupKeys);
				final LabelProvider lb=group.getGroupColumn().getLabelProvider();
				Collections.sort(list,new Comparator(){
					public int compare(Object o1, Object o2) {
						int result=0;
						Comparable c=o1 instanceof Comparable ? (Comparable)o1 : lb.getLabel(o1);
						c=c==null ? "" : c;
						result=Translator.translateC2E(c).compareTo(Translator.translateC2E(o2));
						return DESCENT==type ?  -result : result;
					}
					
				});
				return list;
				
			}
			
		};
	}
	
	
	public Collection sortRowKeys(DataTable dataTable, Collection rowKeys) {		
		return rowKeys;
	}
	
	public Collection sortGroupKeys(Groupable group,Collection groupKeys){
		return groupKeys;
	}

}
