package com.fd.finema.bpcs;

import com.fd.finema.bom.Response;
import com.fd.finema.interfaces.MovieDTO;
import com.fd.finema.interfaces.MovieScreeningDTO;
import com.fd.finema.interfaces.ScreeningDTO;
import com.fd.finema.services.MovieService;
import com.fd.finema.services.ScreeningService;
import com.fd.finema.utils.ResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@Api(tags = "ScreeningController")
public class ScreeningController {

    private ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @POST
    @CrossOrigin
    @Path("/screening/addScreening")
    @RequestMapping(value = "/screening/addScreening", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addScreening(@RequestBody ScreeningDTO screeningDTO) throws Exception {

        this.screeningService.createScreening(screeningDTO);
        return ResponseUtil.createSuccessResponse("Screening added successfully");
    }

    @GET
    @CrossOrigin
    @Path("/screening/currentScreenings")
    @RequestMapping(value = "/screening/currentScreenings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getScreeningsForWeek() throws Exception {

        this.screeningService.getCurrentWeekScreenings();
        return ResponseUtil.createSuccessResponse("Screening added successfully");
    }

    @GET
    @CrossOrigin
    @Path("/screening/currentScreenings/{day}")
    @RequestMapping(value = "/screening/currentScreenings/{day}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MovieScreeningDTO> getScreeningsForDay(@PathVariable String day) throws Exception {
        return this.screeningService.getCurrentScreeningsForDay(day);
    }


    @GET
    @CrossOrigin
    @Path("/today")
    @RequestMapping(value = "/today", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getTodayDay() throws Exception {
        return new Response(this.screeningService.getTodayDay(),(short)200);

    }
}
