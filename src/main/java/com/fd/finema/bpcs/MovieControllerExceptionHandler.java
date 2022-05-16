package com.fd.finema.bpcs;

import com.fd.finema.bom.Response;
import com.fd.finema.bom.ResponseEnum;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = MovieController.class)
public class MovieControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleException(Exception e){
        return new Response(ResponseEnum.ERROR,e.getMessage(),(short)500);
    }
}
