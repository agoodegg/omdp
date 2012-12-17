package cn.com.sunrise.util.report.model;

public class Translator {
	public static Comparable translateC2E(Object chinese) {
		Comparable result = chinese instanceof Comparable ? (Comparable) chinese
				: "";
		if ("东区".equals(chinese)) {
			result = "dongqu";
		} else if ("南区".equals(chinese)) {
			result = "naqu";
		} else if ("西区".equals(chinese)) {
			result = "xiqu";
		} else if ("北区".equals(chinese)) {
			result = "beiqu";
		} else if ("中区".equals(chinese)) {
			result = "zhongqu";
		} else if ("集客".equals(chinese)) {
			result = "jike";
		} else if ("城区".equals(chinese)) {
			result = "chengqu";
		} else if ("服务厅".equals(chinese)) {
			result = "cfuwuding";
		} else if ("代理商".equals(chinese)) {
			result = "bdailishan";
		} else if ("客户经理".equals(chinese)) {
			result = "akefujingli";
		} else if ("10086".equals(chinese)) {
			result = "zzzzzzzz";
		} else if ("集团客户中心".equals(chinese)) {
			result = "diyiyingxiaozhongxin";
		} else if ("营销服务中心".equals(chinese)) {
			result = "dieryingxiaozhongxin";
		}
		return result;
	}
}
