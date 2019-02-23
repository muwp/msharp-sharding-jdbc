package com.msharp.sharding.jdbc.jtemplate.strategy;

/**
 * VerticalHashRouterStrategy
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class VerticalHashRouterStrategy implements RouterStrategy {

	/**
	 * 结点数
	 */
	private int nodeNum;

	/**
	 * 每个结果的数据库数
	 */
	private int dbNum;

	/**
	 * 每个数据库的表个数
	 */
	private int tableNum;

	public VerticalHashRouterStrategy() {

	}

	public VerticalHashRouterStrategy(int nodeNum, int dbNum, int tableNum) {
		this.nodeNum = nodeNum;
		this.dbNum = dbNum;
		this.tableNum = tableNum;
	}

	public int getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}

	public int getTableNum() {
		return tableNum;
	}

	public void setTableNum(int tableNum) {
		this.tableNum = tableNum;
	}

	public int getDbNum() {
		return dbNum;
	}

	public void setDbNum(int dbNum) {
		this.dbNum = dbNum;
	}

	@Override
	public int getNodeNo(final Object partitionKey) {
		final int hashCode = calcHashCode(partitionKey);
		return hashCode % nodeNum;
	}

	@Override
	public int getDatabasebNo(final Object partitionKey) {
		int hashCode = calcHashCode(partitionKey);
		return hashCode / nodeNum % dbNum;
	}

	@Override
	public int getTableNo(final Object partitionKey) {
		final int hashCode = calcHashCode(partitionKey);
		return hashCode / nodeNum / dbNum % tableNum;
	}

	private int calcHashCode(final Object partitionKey) {
		final int hashCode = partitionKey.hashCode();
		return hashCode;
	}
}
