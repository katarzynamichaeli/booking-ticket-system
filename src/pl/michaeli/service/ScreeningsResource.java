package pl.michaeli.service;

import java.util.Calendar;
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
import pl.michaeli.spring.config.AppConfig;
import pl.michaeli.spring.dao.FilmsDAO;
import pl.michaeli.spring.dao.ScreeningsDAO;

@Path("screenings")
public class ScreeningsResource {
	
    @GET
    @Path("{dateFrom}/{dateTo}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getScreenings(@PathParam("dateFrom") Long dateFrom,@PathParam("dateTo") Long dateTo) {
    	if(dateFrom>=dateTo) {
        	ErrorMessage errorMessage = new ErrorMessage("Z³y przedzia³ czasowy");
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
        }
    	
		if(dateFrom<getAllowedStartDate()) dateFrom=getAllowedStartDate();
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		ScreeningsDAO screeningsDAO=context.getBean(ScreeningsDAO.class);
		FilmsDAO filmsDAO=context.getBean(FilmsDAO.class);
		context.close();
		
		List<Screening> screenings = screeningsDAO.getScreeningsByDate(dateFrom,dateTo);
		screenings.forEach((screening) -> screening.setFilm(filmsDAO.getFilmById(screening.getFilmId())));
		GenericEntity<List<Screening>> myEntity = new GenericEntity<List<Screening>>(screenings) {};
	    return Response.status(200).entity(myEntity).build();
	}
	
	private Long getAllowedStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(System.currentTimeMillis()));
		cal.add(Calendar.MINUTE, 15);
		Long allowedStartDate = cal.getTime().getTime();
		return allowedStartDate;
	}
}
