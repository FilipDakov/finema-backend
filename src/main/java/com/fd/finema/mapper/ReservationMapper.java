package com.fd.finema.mapper;

import com.fd.finema.bom.Reservation;
import com.fd.finema.bom.Status;
import com.fd.finema.interfaces.ReservationDTO;
import com.fd.finema.security.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring",uses = {ScreeningMapper.class})
public abstract class ReservationMapper {

    public abstract List<ReservationDTO> mapReservationsToDTO(List<Reservation> reservations);
    public abstract ReservationDTO mapReservationToDTO(Reservation reservation);

    protected String mapUser(User value){
        return value.getEmail();
    }

    protected String mapStatus(Status status){
        return status.getValue();
    }

    @AfterMapping
    protected void mapComplexFields(@MappingTarget ReservationDTO dto, Reservation reservation){
        dto.getSeatNumbers().add(reservation.getSeatNumber());
    }

}
