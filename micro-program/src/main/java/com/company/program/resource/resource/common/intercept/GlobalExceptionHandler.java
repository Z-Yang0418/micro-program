/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.company.program.resource.resource.common.intercept;

import com.company.program.resource.common.domain.BusinessException;
import com.company.program.resource.common.domain.ErrorCode;
import com.company.program.resource.common.domain.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice
public class GlobalExceptionHandler {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public RestResponse<Nullable> exceptionGet(Exception e) {
		if (e instanceof BusinessException) {
			BusinessException be = (BusinessException) e;
			if(ErrorCode.CUSTOM.equals(be.getErrorCode())){
				return new RestResponse<Nullable>(be.getErrorCode().getCode(), be.getMessage());
			}else{
				return new RestResponse<Nullable>(be.getErrorCode().getCode(), be.getErrorCode().getDesc());
			}
		}
		
		LOGGER.error("【系统异常】{}", e);
		return  new RestResponse<Nullable>(ErrorCode.UNKOWN.getCode(),ErrorCode.UNKOWN.getDesc());
	}

}
