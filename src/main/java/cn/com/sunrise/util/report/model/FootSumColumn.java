package cn.com.sunrise.util.report.model;


/**
 * 汇总表脚
 * 用于列的纵向累计
 * @author hegang
 *
 */
public class FootSumColumn extends FootComputeColumn {

	public FootSumColumn(Foot foot, Column positionColumn) {
		super(foot, positionColumn);
		this.addRelativeColumnKey("COLUMN",positionColumn.getKey());
		this.setExpression("SUM(COLUMN)");
	}

}
