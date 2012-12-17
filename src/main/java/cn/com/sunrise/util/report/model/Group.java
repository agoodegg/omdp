package cn.com.sunrise.util.report.model;

import java.util.Iterator;

public class Group extends Groupable{
	
	Groupable parentGroup;
	Column column;
	Sorter groupSorter = Sorter.getDefaultSorter();


	public Group(Groupable parentGroup){
		this.parentGroup=parentGroup;
		parentGroup.setChildGroup(this);
	}
	
	public Group(Groupable parentGroup,Column column){
		this.parentGroup=parentGroup;
		parentGroup.setChildGroup(this);
		this.column = column;
	}
	
	public Column getGroupColumn() {
		return column==null ? column=doGetColumn() : column;
	}
	
	private Column doGetColumn() {
		Iterator itr=getReport().getBottomColumns().iterator();		
		Column c=null;
		Groupable g=getParentGroup();
		while(g!=null){
			c=(Column) itr.next();
			g=g.getParentGroup();
		}
		return c;
	}

	public Report getReport(){
		return parentGroup.getReport();
	}
	
	public Groupable getParentGroup(){
		return parentGroup;
	}


	public Sorter getGroupSorter() {
		return groupSorter;
	}


	public void setGroupSorter(Sorter sorter) {
		this.groupSorter = sorter;
	}



	
	
}
