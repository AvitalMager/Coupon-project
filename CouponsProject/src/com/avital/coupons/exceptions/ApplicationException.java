package com.avital.coupons.exceptions;

import com.avital.coupons.enums.ErrorType;

public class ApplicationException extends Exception {

	private ErrorType errorType;

	public ApplicationException(ErrorType errorType, String message) {
		super(message);
		this.errorType = errorType;
	}

	public ApplicationException(Exception e, ErrorType errorType, String message) {
		super(message, e);
		this.errorType = errorType;
	}

	public ErrorType getErrorType() {
		return errorType;
	}
}
