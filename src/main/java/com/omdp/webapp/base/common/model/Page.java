package com.omdp.webapp.base.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Page {

	public Page() {
		pageNo = 1;
		pageSize = 20;
		orderBy = null;
		order = "asc";
		autoCount = true;
		result = new ArrayList();
		totalCount = 0L;
	}

	public Page(int pageSize) {
		pageNo = 1;
		this.pageSize = 20;
		orderBy = null;
		order = "asc";
		autoCount = true;
		result = new ArrayList();
		totalCount = 0L;
		setPageSize(pageSize);
	}

	public Page(int pageSize, boolean autoCount) {
		pageNo = 1;
		this.pageSize = 20;
		orderBy = null;
		order = "asc";
		this.autoCount = true;
		result = new ArrayList();
		totalCount = 0L;
		setPageSize(pageSize);
		this.autoCount = autoCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if (pageNo < 1)
			this.pageNo = 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirst() {
		return (pageNo - 1) * pageSize;
	}

	public int getEnd() {
		return getFirst() + getPageSize();
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(orderBy);
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		String orders[] = StringUtils.split(order, ',');
		String as[];
		int j = (as = orders).length;
		for (int i = 0; i < j; i++) {
			String orderStr = as[i];
			if (!StringUtils.equalsIgnoreCase("desc", orderStr) && !StringUtils.equalsIgnoreCase("asc", orderStr))
				throw new IllegalArgumentException((new StringBuilder("\u6392\u5E8F\u65B9\u5411")).append(orderStr)
						.append("\u4E0D\u662F\u5408\u6CD5\u503C").toString());
		}

		this.order = order.toLowerCase();
	}

	public String getPageRequest() {
		return (new StringBuilder(String.valueOf(getPageNo()))).append("|").append(
				StringUtils.defaultString(getOrderBy())).append("|").append(getOrder()).toString();
	}

	public void setPageRequest(String pageRequest) {
		if (StringUtils.isBlank(pageRequest))
			return;
		String params[] = StringUtils.splitPreserveAllTokens(pageRequest, '|');
		if (StringUtils.isNumeric(params[0]))
			setPageNo(Integer.valueOf(params[0]).intValue());
		if (StringUtils.isNotBlank(params[1]))
			setOrderBy(params[1]);
		if (StringUtils.isNotBlank(params[2]))
			setOrder(params[2]);
	}

	public boolean isAutoCount() {
		return autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public List getResult() {
		if (result == null)
			return Collections.emptyList();
		else
			return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		if (totalCount < 0L)
			return -1;
		int count = (int) (totalCount / (long) pageSize);
		if (totalCount % (long) pageSize > 0L)
			count++;
		return count;
	}

	public boolean isHasNext() {
		return pageNo + 1 <= getTotalPages();
	}

	public int getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	public boolean isHasPre() {
		return pageNo - 1 >= 1;
	}

	public int getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}

	public String getInverseOrder() {
		String orders[] = StringUtils.split(order, ',');
		for (int i = 0; i < orders.length; i++)
			if ("desc".equals(orders[i]))
				orders[i] = "asc";
			else
				orders[i] = "desc";

		return StringUtils.join(orders);
	}
	

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}




	public static final String ASC = "asc";
	public static final String DESC = "desc";
	public static final int MIN_PAGESIZE = 5;
	public static final int DEFUALT_PAGESIZE = 20;
	public static final int MAX_PAGESIZE = 200;
	protected int pageNo;
	protected int pageSize;
	protected String orderBy;
	protected String order;
	protected boolean autoCount;
	protected List result;
	protected long totalCount;
	
	protected double totalAmount;
}
