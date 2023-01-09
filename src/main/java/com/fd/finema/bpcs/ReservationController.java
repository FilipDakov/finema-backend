package com.fd.finema.bpcs;

import com.fd.finema.bom.Response;
import com.fd.finema.bom.Seat;
import com.fd.finema.interfaces.PersonDTO;
import com.fd.finema.interfaces.ReservationDTO;
import com.fd.finema.interfaces.ScreeningDTO;
import com.fd.finema.services.ReservationService;
import com.fd.finema.utils.ResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

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
        return ResponseUtil.createSuccessResponse("Reservation added successfully");
    }

    @POST
    @Path("/reservation/getReservations")
    @CrossOrigin
    @RequestMapping(value = "/reservation/getReservations", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationDTO> getReservations(@RequestBody PersonDTO reservationDto){
         return reservationService.getAllReservationByPerson(reservationDto);
    }

    @GET
    @Path("/reservation/getReservations")
    @CrossOrigin
    @RequestMapping(value = "/reservation/getReservations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationDTO> getReservationsByNames(@RequestParam(required = false) String firstName,@RequestParam(required = false) String middleName,
                                                       @RequestParam(required = false) String lastName ){
        return reservationService.getAllReservationByPerson(firstName, middleName, lastName);
    }

    @POST
    @Path("/reservation/confirm")
    @CrossOrigin
    @RequestMapping(value = "/reservation/confirm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response confirmReservation(@RequestBody ReservationDTO reservationDto){
         reservationService.confirmReservation(reservationDto);
         return ResponseUtil.createSuccessResponse("Reservation confirmed");
    }

    @POST
    @Path("/reservation/delete")
    @CrossOrigin
    @RequestMapping(value = "/reservation/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteReservation(@RequestBody ReservationDTO reservationDto){
        reservationService.deleteReservation(reservationDto);
        return ResponseUtil.createSuccessResponse("Reservation deleted");
    }

    @GET
    @Path("/reservation/getReservations/{user}")
    @CrossOrigin
    @RequestMapping(value = "/reservation/getReservations/{user}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationDTO> getReservationsForUser(@PathVariable String user){
        return reservationService.getReservationsForUser(user);
    }

    @GET
    @Path("/reservation/getAllReservations")
    @CrossOrigin
    @RequestMapping(value = "/reservation/getAllReservations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationDTO> getAllReservations(){
        return reservationService.getAllReservations();
    }
}
