package com.fd.finema.bpcs;

import com.fd.finema.bom.Response;
import com.fd.finema.bom.ResponseEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = ScreeningController.class)
public class ScreeningControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response handleException(Exception e){
        e.printStackTrace();
        return new Response(ResponseEnum.ERROR,e.getMessage(),(short)501);
    }
}
