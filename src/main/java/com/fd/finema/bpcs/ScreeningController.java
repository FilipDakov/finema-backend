package com.fd.finema.bpcs;

import com.fd.finema.bom.Response;
import com.fd.finema.interfaces.MovieDTO;
import com.fd.finema.interfaces.ScreeningDTO;
import com.fd.finema.services.MovieService;
import com.fd.finema.utils.ResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@RestController
@Api(tags = "ScreeningController")
public class ScreeningController {

    private final MovieService movieService;

    public ScreeningController(MovieService movieService) {
        this.movieService = movieService;
    }

    @POST
    @Path("screening/addScreening")
    @RequestMapping(value = "screening/addScreening", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addNewMovie(@RequestBody ScreeningDTO screeningDTO) throws Exception {

        return ResponseUtil.createSuccessResponse("Screening added successfully");
    }




}
