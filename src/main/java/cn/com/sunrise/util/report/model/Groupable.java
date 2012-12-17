package cn.com.sunrise.util.report.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Groupable {

	Group childGroup;
	List foots = new LinkedList();
	Sorter rowSorter;
	
	abstract Report getReport();
	
	Column getGroupColumn(){
		return null;
	}
	
	Groupable getParentGroup(){
		return null;
	}


	void addFoot(Foot foot) {
		foots.add(foot);		
	}
	
	public void setChildGroup(Group group){
		this.childGroup=group;
	}

	Iterator unGroupedColumns() {
		Iterator itr=getReport().getBottomColumns().iterator();
		Groupable g=getParentGroup();
		while(g!=null){
			itr.next();
			g=g.getParentGroup();
		}
		return itr;
	}

	
	public Sorter getRowSorter() {
		return rowSorter==null ? this.getParentGroup().getRowSorter() : rowSorter;
	}

	public void setRowSorter(Sorter rowSorter) {
		this.rowSorter = rowSorter;
	}
	
	
	
	

}
