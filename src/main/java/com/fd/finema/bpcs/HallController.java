package com.fd.finema.bpcs;

import com.fd.finema.interfaces.MovieDTO;
import com.fd.finema.services.HallService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@RestController
@Api(tags = "HallController")
@CrossOrigin(origins = "*")
public class HallController {

    private HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GET
    @Path("/hall/getHalls")
    @CrossOrigin
    @RequestMapping(value = "/hall/getHalls", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Integer> getActiveMovies() throws Exception {
       return hallService.getAvailableHalls();
    }
}
