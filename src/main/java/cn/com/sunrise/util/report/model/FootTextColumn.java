package cn.com.sunrise.util.report.model;

/**
 * 文本表脚
 * 用于显示特定文本的表脚
 * @author hegang
 *
 */
public class FootTextColumn extends FootDataColumn {

	public FootTextColumn(Foot foot, Column positionColumn) {
		super(foot, positionColumn);
		this.setLabelProvider(LabelProvider.getDefaultLabelProvider());
	}

	public void setText(String text){
		this.setData(text);
	}
	
	public String getText(){
		return (String)getData(null);
	}


}
