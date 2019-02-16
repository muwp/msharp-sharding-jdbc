package com.muwp.sharding.jdbc.core.strategy;

/**
 * VerticalHashSplitStrategy
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class VerticalHashSplitStrategy implements SplitStrategy {

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

	public VerticalHashSplitStrategy() {

	}

	public VerticalHashSplitStrategy(int nodeNum, int dbNum, int tableNum) {
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
	public int getNodeNo(Object splitKey) {
		int hashCode = calcHashCode(splitKey);
		return hashCode % nodeNum;
	}

	@Override
	public int getDbNo(Object splitKey) {
		int hashCode = calcHashCode(splitKey);
		return hashCode / nodeNum % dbNum;
	}

	@Override
	public int getTableNo(Object splitKey) {
		int hashCode = calcHashCode(splitKey);
		return hashCode / nodeNum / dbNum % tableNum;
	}

	private int calcHashCode(Object splitKey) {
		int hashCode = splitKey.hashCode();
		return hashCode;
	}
}
