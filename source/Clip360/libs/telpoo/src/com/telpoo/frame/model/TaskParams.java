/**
 * 
 */
package com.telpoo.frame.model;

/**
 * @author NAQ
 *
 */
public class TaskParams {
	private String[] stringParams = null;
	private int[] intParams = null;
	private boolean[] booleanParams = null;
	
	public TaskParams () {
		
	}
	
	public TaskParams (String[] stringParams) {
		this.stringParams = stringParams;
	}
	
	public TaskParams (String[] stringParams, int[] intParams, boolean[] booleanParams) {
		this.stringParams = stringParams;
		this.intParams = intParams;
		this.booleanParams = booleanParams;
	}
	
	/**
	 * @return the stringParams
	 */
	public String[] getStringParams() {
		return stringParams;
	}
	/**
	 * @param stringParams the stringParams to set
	 */
	public void setStringParams(String[] stringParams) {
		this.stringParams = stringParams;
	}
	/**
	 * @return the intParams
	 */
	public int[] getIntParams() {
		return intParams;
	}
	/**
	 * @param intParams the intParams to set
	 */
	public void setIntParams(int[] intParams) {
		this.intParams = intParams;
	}
	/**
	 * @return the booleanParams
	 */
	public boolean[] getBooleanParams() {
		return booleanParams;
	}
	/**
	 * @param booleanParams the booleanParams to set
	 */
	public void setBooleanParams(boolean[] booleanParams) {
		this.booleanParams = booleanParams;
	}
	
}
