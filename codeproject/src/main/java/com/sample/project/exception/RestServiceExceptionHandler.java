package com.sample.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.project.model.ResponseInfo;


@ControllerAdvice
public class RestServiceExceptionHandler {
    
    
    /**
     * Exception Handler of Type Throwable
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    ResponseEntity<ResponseInfo> handleThrowable(Throwable ex) {


        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseInfo error =
                new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error");
        return new ResponseEntity<ResponseInfo>(error, httpStatus);
    }
    
    /**
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(RestServiceException.class)
    @ResponseBody
    ResponseEntity<ResponseInfo> handleRestException(RestServiceException ex) {


        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseInfo error =
                new ResponseInfo(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return new ResponseEntity<ResponseInfo>(error, httpStatus);
    }
    
}
