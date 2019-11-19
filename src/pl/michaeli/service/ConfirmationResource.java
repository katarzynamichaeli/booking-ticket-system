package pl.michaeli.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.michaeli.model.BookedSeat;
import pl.michaeli.model.ErrorMessage;
import pl.michaeli.model.Reservation;
import pl.michaeli.model.Seat;
import pl.michaeli.spring.config.AppConfig;
import pl.michaeli.spring.dao.BookedSeatsDAO;
import pl.michaeli.spring.dao.ReservationsDAO;
import pl.michaeli.spring.dao.ScreeningsDAO;
import pl.michaeli.spring.dao.SeatsDAO;
import pl.michaeli.spring.dao.TicketTypesDAO;


@Path("confirm")
public class ConfirmationResource {

	@POST
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response confirmReservation(Reservation reservation) {

		String name = reservation.getName();
		String surname = reservation.getSurname();
		int screeningId = reservation.getScreeningId();
		List<BookedSeat> seatsToBook = reservation.getBookedSeats();

		if(name.length()<3 || surname.length()<3) {
			ErrorMessage errorMessage = new ErrorMessage("Za krótkie imiê lub nazwisko!");
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
		}
		if(checkName(name)==false) {
			ErrorMessage errorMessage = new ErrorMessage("B³êdne imiê!");
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
		}
		if(checkSurname(surname)==false) {
			ErrorMessage errorMessage = new ErrorMessage("B³êdne nazwisko!");
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
		}
		if(seatsToBook==null || seatsToBook.size()==0) {
			ErrorMessage errorMessage = new ErrorMessage("Trzeba wybraæ minimum jedno miejsce!");
			return Response.serverError().type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
		}
		if(!checkIsAvailable(seatsToBook,screeningId)) {
			ErrorMessage errorMessage = new ErrorMessage("Wybrano zajête miejsce!");
			return Response.serverError().type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
		}
		if(checkIfFreeSpaceLeft(seatsToBook,screeningId)) {
			ErrorMessage errorMessage = new ErrorMessage("Nie mo¿na zostawiæ jednego miejsca wolnego miêdzy dwoma zajêtymi!");
			return Response.serverError().type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
		}
		
		seatsToBook.forEach((seatToBook) -> seatToBook.setScreeningId(screeningId));

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		ScreeningsDAO screeningsDAO=context.getBean(ScreeningsDAO.class);
		
		Date screeningDate = (screeningsDAO.getScreeningById(screeningId)).getScreeningDate();
		reservation.setExpirationTime(this.countExpirationDate(screeningDate));
		reservation.setTotalAmount(this.countTotalAmount(seatsToBook,screeningDate));

		ReservationsDAO reservationsDAO=context.getBean(ReservationsDAO.class);
		BookedSeatsDAO bookedSeatsDAO=context.getBean(BookedSeatsDAO.class);

		reservation=reservationsDAO.addReservation(reservation);
		bookedSeatsDAO.addBookedSeats(reservation);
		reservation.setBookedSeats(bookedSeatsDAO.getBookedSeatsByReservationId(reservation.getReservationId()));

		context.close();

        GenericEntity<Reservation> myEntity = new GenericEntity<Reservation>(reservation) {};
        return Response.status(200).entity(myEntity).build();
    }
		
	private Boolean checkName(String name) {
		Boolean isCorrect = Character.isUpperCase(name.charAt(0));
        for(int i=1;i<name.length();i++) {
            if(isCorrect) {
                isCorrect = Character.isLowerCase(name.charAt(i));
            }
        }
		return isCorrect;
	}
	
	private Boolean checkSurname(String surname) {
		if(surname.contains("-")) {
			int index = surname.indexOf("-");
			String firstPart=surname.substring(0, index);
			String secondPart=surname.substring(index+1);
			return (checkName(firstPart)&&checkName(secondPart));
			
		}else {
			return checkName(surname);
		}
	}
	
	private double countTotalAmount(List<BookedSeat> bookedSeats, Date screeningDate) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		TicketTypesDAO ticketTypesDAO=context.getBean(TicketTypesDAO.class);
		context.close();
		double totalAmount=0;
		for(int i=0; i<bookedSeats.size(); i++) {
			totalAmount+=ticketTypesDAO.getPrice(bookedSeats.get(i).getTicketTypeId());
		}
		if(checkIsWeekend(screeningDate)) {
			totalAmount = totalAmount + 4*bookedSeats.size();
		}
		return totalAmount;
	}
	
	private Boolean checkIsWeekend(java.util.Date date) {
		Boolean isWeekend = false;
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    switch(cal.get(Calendar.DAY_OF_WEEK)){
	    	case Calendar.FRIDAY:
	    		if(cal.get(Calendar.HOUR_OF_DAY)>=14)
	                isWeekend=true;
	            break;
	        case Calendar.SATURDAY:
	            isWeekend=true;
	            break;
	        case Calendar.SUNDAY:
	            if(cal.get(Calendar.HOUR_OF_DAY)<23)
	                isWeekend=true;
	            break;
	    }
		return isWeekend;
	}
	
	private Date countExpirationDate(Date screeningDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(screeningDate);
		cal.add(Calendar.MINUTE, -15);
		Date expirationTime = cal.getTime();
		return expirationTime;
	}
	
	private Boolean checkIsAvailable(List<BookedSeat> seatsToBook,int screeningId) {
		Boolean isAvailable=true;
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		BookedSeatsDAO bookedSeatsDAO=context.getBean(BookedSeatsDAO.class);
		for(int i=0;i<seatsToBook.size();i++) {
			BookedSeat seatToBook=seatsToBook.get(i);
			if(bookedSeatsDAO.doesExist(seatToBook.getSeatId(), screeningId)) {
				isAvailable=false;
				break;
			}
		}
		context.close();
		return isAvailable;
	}
	
	private Boolean checkIfFreeSpaceLeft(List<BookedSeat> seatsToBook,int screeningId) {
		Boolean isFreeSpaceLeft=false;
		int[][]placesInRoom = new int[5][10];
		for(int i=0;i<5;i++) {
			for(int j=0;j<10;j++) {
				placesInRoom[i][j]=0;
			}
		}

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		SeatsDAO seatsDAO=context.getBean(SeatsDAO.class);
		for(int i=0;i<seatsToBook.size();i++) {
			Seat seat = seatsDAO.getSeatById(seatsToBook.get(i).getSeatId());
			int rowNo = seat.getRowNo();
			int seatNo = seat.getSeatNo();
			placesInRoom[rowNo-1][seatNo-1]=1;
		}

		BookedSeatsDAO bookedSeatsDAO=context.getBean(BookedSeatsDAO.class);
		List<BookedSeat> bookedSeats=bookedSeatsDAO.getBookedSeatsByScreeningId(screeningId);

		for(int i=0;i<bookedSeats.size();i++) {
			Seat seat = seatsDAO.getSeatById(bookedSeats.get(i).getSeatId());
			int rowNo = seat.getRowNo();
			int seatNo = seat.getSeatNo();
			placesInRoom[rowNo-1][seatNo-1]=1;
		}
		context.close();

		for(int i=0;i<5;i++) {
			for(int j=0;j<10;j++) {
				System.out.print(placesInRoom[i][j] + " ");
			}
			System.out.println();
		}
		for(int i=0;i<5;i++) {
			for(int j=0;j<10;j++) {
				if(j==0) {
					if(placesInRoom[i][j]==0 && placesInRoom[i][j+1]==1) {
						isFreeSpaceLeft=true;
					}
				}else if(j==9) {
					if(placesInRoom[i][j]==0 && placesInRoom[i][j-1]==1) {
						isFreeSpaceLeft=true;
					}
				}else {
					if(placesInRoom[i][j]==0 && placesInRoom[i][j-1]==1 && placesInRoom[i][j+1]==1) {
						isFreeSpaceLeft=true;
					}
				}
			}
		}
		return isFreeSpaceLeft;
	}
}
