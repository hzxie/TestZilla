package com.trunkshell.testzilla.exception;

public class TimeRangeException extends Exception{
	/**
	 * 唯一的序列化标识符.
	 */
	private static final long serialVersionUID = 2084565252857040008L;

	public TimeRangeException(String message) {
		super(message);
	}
	
	public TimeRangeException() {
		
	}
}
