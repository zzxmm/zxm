package com.shouzan.common.exception;

/**
 * 
 * @author liyong
 *
 */
public class MSException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String code;

	public MSException(String message) {

		super(message);
	}

	public MSException(String code, String message) {

		super(message);
		this.code = code;
	}

	public MSException(Throwable cause) {

		super(cause);
		// TODO Auto-generated constructor stub
	}

	public String getCode() {

		return code;
	}

	public void setCode(String code) {

		this.code = code;
	}

}
