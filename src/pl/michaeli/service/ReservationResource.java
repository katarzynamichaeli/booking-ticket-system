package pl.michaeli.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.michaeli.model.BookedSeat;
import pl.michaeli.model.ErrorMessage;
import pl.michaeli.model.Reservation;
import pl.michaeli.spring.config.AppConfig;
import pl.michaeli.spring.dao.BookedSeatsDAO;
import pl.michaeli.spring.dao.ReservationsDAO;

@Path("reservation")
public class ReservationResource {
	
    @GET
    @Path("{reservationId}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response getReservationDetails(@PathParam("reservationId") int reservationId) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    	ReservationsDAO reservationsDAO=context.getBean(ReservationsDAO.class);
    	BookedSeatsDAO bookedSeatsDAO=context.getBean(BookedSeatsDAO.class);
		context.close();
		
		if(!reservationsDAO.doesExist(reservationId)) {
			ErrorMessage errorMessage = new ErrorMessage("Podana rezerwacja nie istnieje!");
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
		}
		
    	Reservation reservationDetails = reservationsDAO.getReservationById(reservationId);
    	List<BookedSeat> bookedSeats =  bookedSeatsDAO.getBookedSeatsByReservationId(reservationId);
    	reservationDetails.setBookedSeats(bookedSeats);
    	System.out.println(reservationDetails);
    	GenericEntity<Reservation> myEntity = new GenericEntity<Reservation>(reservationDetails) {};
        return Response.status(200).entity(myEntity).build();
    }
}
