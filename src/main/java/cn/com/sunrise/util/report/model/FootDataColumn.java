package cn.com.sunrise.util.report.model;

/**
 * 数据表脚
 * 用于显示特定数据的表脚
 * @author hegang
 *
 */
public class FootDataColumn extends FootColumn{

	Object data;
	
	public FootDataColumn(Foot foot, Column positionColumn) {
		super(foot, positionColumn);
	}

	public Object getData(GroupCell gc) {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	

}
