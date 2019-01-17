package com.infowaybr.infowaybank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InsufficientBalanceException extends RuntimeException {

	private static final long serialVersionUID = 1850107139958312714L;

	public InsufficientBalanceException(String msg) {
		super(msg);
	}

}
