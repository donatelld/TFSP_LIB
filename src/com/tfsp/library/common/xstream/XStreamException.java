package com.tfsp.library.common.xstream;


public class XStreamException extends Exception{
	private static final long	serialVersionUID	= -246297078212015737L;

	public XStreamException() {
		super();
	}
	
	public XStreamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public XStreamException(String message, Throwable cause) {
		super(message, cause);
	}

	public XStreamException(String message) {
		super(message);
	}

	public XStreamException(Throwable cause) {
		super(cause);
	}
	
	
}

