package com.springbootvue.exception;

import com.springbootvue.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(), se.getMessage());
    }
}
