package com.fd.finema.bpcs;

import com.fd.finema.bom.Movie;
import com.fd.finema.bom.Response;
import com.fd.finema.interfaces.MovieDTO;
import com.fd.finema.services.MovieService;
import com.fd.finema.utils.ResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@RestController
@Api(tags = "MovieController")
@CrossOrigin(origins = "*")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @POST
    @Path("/movies/addMovie")
    @CrossOrigin
    @RequestMapping(value = "/movies/addMovie", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addNewMovie(@RequestBody MovieDTO movie) throws Exception {
        movieService.addMovie(movie);
        return ResponseUtil.createSuccessResponse("Movie added successfully");
    }


    @GET
    @Path("/movies/getMovies")
    @CrossOrigin
    @RequestMapping(value = "/movies/getMovies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MovieDTO> getActiveMovies() throws Exception {
        try {
            return movieService.getListActiveMovies();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("/movies/getCurrentMovies")
    @RequestMapping(value = "/movies/getCurrentMovies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MovieDTO> getCurrentMovies() throws Exception {
        try {
            return movieService.getCurrentMovies();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
