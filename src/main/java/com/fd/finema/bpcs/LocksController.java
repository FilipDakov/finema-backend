package com.fd.finema.bpcs;


import com.fd.finema.bom.Response;
import com.fd.finema.bom.ResponseEnum;
import com.fd.finema.interfaces.LockDTO;
import com.fd.finema.services.LockService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@RestController
@Api(tags = "LockController")
@CrossOrigin(origins = "*")
public class LocksController {
    LockService lockService;

    public LocksController(LockService lockService) {
        this.lockService = lockService;
    }

    @GET
    @Path("/locks/remove")
    @CrossOrigin
    @RequestMapping(value = "/locks/remove", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response removeUnused() throws Exception {
        lockService.unlockExpiredSeats();
        return new Response(ResponseEnum.SUCCESS,"",(short)200);
    }

    @POST
    @Path("/locks/lock")
    @CrossOrigin
    @RequestMapping(value = "/locks/lock", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response lockSeat(@RequestBody LockDTO lockDto) throws Exception {
        lockService.lockSeat(lockDto);
        return new Response(ResponseEnum.SUCCESS,"",(short)200);
    }

    @POST
    @Path("/locks/unlock")
    @CrossOrigin
    @RequestMapping(value = "/locks/unlock", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response unlockSeat(@RequestBody LockDTO lockDto) throws Exception {
        lockService.unlockSeat(lockDto);
        return new Response(ResponseEnum.SUCCESS,"",(short)200);
    }
}
