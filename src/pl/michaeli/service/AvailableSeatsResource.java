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

import pl.michaeli.model.ErrorMessage;
import pl.michaeli.model.Screening;
import pl.michaeli.model.Seat;
import pl.michaeli.spring.config.AppConfig;
import pl.michaeli.spring.dao.ScreeningsDAO;
import pl.michaeli.spring.dao.SeatsDAO;

@Path("availableseats")
public class AvailableSeatsResource {
	
    @GET
    @Path("s/{screeningId}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getAvailableSeats(@PathParam("screeningId") int screeningId) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		SeatsDAO seatsDAO=context.getBean(SeatsDAO.class);
		ScreeningsDAO screeningsDAO=context.getBean(ScreeningsDAO.class);
		context.close();
		
		if(!screeningsDAO.doesExist(screeningId)) {
			ErrorMessage errorMessage = new ErrorMessage("Podany seans nie istnieje!");
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
		}
		
		Screening screening=screeningsDAO.getScreeningById(screeningId);
		int roomNo=screening.getRoomNo();
		List<Seat> seats = seatsDAO.getAvailableSeats(screeningId, roomNo);
		GenericEntity<List<Seat>> myEntity = new GenericEntity<List<Seat>>(seats) {};
	    return Response.status(200).entity(myEntity).build();
	}

}
