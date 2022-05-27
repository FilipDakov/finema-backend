package com.fd.finema.bpcs;

import com.fd.finema.bom.Response;
import com.fd.finema.bom.Seat;
import com.fd.finema.interfaces.ReservationDTO;
import com.fd.finema.interfaces.ScreeningDTO;
import com.fd.finema.services.ReservationService;
import com.fd.finema.utils.ResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RestController
@Api(tags = "LockController")
@CrossOrigin(origins = "*")
public class ReservationController {

    ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @POST
    @Path("/reservation/freeSeats")
    @CrossOrigin
    @RequestMapping(value = "/reservation/freeSeats", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Seat[] getFreeSeats(@RequestBody ScreeningDTO screeningDto){
        return reservationService.getFreeSeats(screeningDto);
    }

    @POST
    @Path("/reservation/create")
    @CrossOrigin
    @RequestMapping(value = "/reservation/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createReservation(@RequestBody ReservationDTO reservationDto){
        reservationService.createReservation(reservationDto);
        return ResponseUtil.createSuccessResponse("Screening added successfully");
    }
}
