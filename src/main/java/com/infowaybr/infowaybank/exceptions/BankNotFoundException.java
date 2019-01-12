package com.infowaybr.infowaybank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class BankNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -236858111066082392L;

}
