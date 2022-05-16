package com.fd.finema.utils;

import com.fd.finema.bom.Response;
import com.fd.finema.bom.ResponseEnum;

public class ResponseUtil {

    public static Response createSuccessResponse(String message){
        return new Response(ResponseEnum.SUCCESS,message,(short)200);
    }
}
