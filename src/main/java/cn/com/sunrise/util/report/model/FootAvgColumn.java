package cn.com.sunrise.util.report.model;


/**
 * 用于显示列平均值的表脚列
 * @author hegang
 *
 */
public class FootAvgColumn extends FootComputeColumn {

	public FootAvgColumn(Foot foot, Column positionColumn) {
		super(foot, positionColumn);
		this.addRelativeColumnKey("COLUMN",positionColumn.getKey());
		this.setExpression("AVG(COLUMN)");
	}


}
