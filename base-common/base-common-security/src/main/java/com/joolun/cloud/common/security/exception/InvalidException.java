package com.joolun.cloud.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joolun.cloud.common.security.component.BaseAuth2ExceptionSerializer;

/**
 * @author
 */
@JsonSerialize(using = BaseAuth2ExceptionSerializer.class)
public class InvalidException extends BaseAuth2Exception {

	public InvalidException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_exception";
	}

	@Override
	public int getHttpErrorCode() {
		return 426;
	}

}
