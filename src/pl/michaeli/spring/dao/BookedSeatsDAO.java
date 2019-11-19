package pl.michaeli.spring.dao;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import pl.michaeli.model.BookedSeat;
import pl.michaeli.model.BookedSeatMapper;
import pl.michaeli.model.Reservation;



@Component
public class BookedSeatsDAO {
	JdbcTemplate jdbcTemplate;

	private final String SQL_GET_BOOKED_SEATS_BY_RESERVATION = "SELECT * FROM BOOKED_SEATS WHERE RESERVATION_ID = ?;";
	private final String SQL_ADD_BOOKED_SEAT = "INSERT INTO BOOKED_SEATS(RESERVATION_ID,SEAT_ID,TICKET_TYPE_ID,SCREENING_ID) VALUES (?,?,?,?);";
	private final String SQL_GET_BOOKED_SEATS_BY_SCREENING = "SELECT * FROM BOOKED_SEATS WHERE SCREENING_ID = ?;";
	private final String SQL_DOES_EXIST_BY_SEAT_AND_SCREENING = "SELECT EXISTS(SELECT * FROM BOOKED_SEATS WHERE SEAT_ID=? AND SCREENING_ID=?);";

	@Autowired
	public BookedSeatsDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<BookedSeat> getBookedSeatsByReservationId(int reservationId) {
		return jdbcTemplate.query(SQL_GET_BOOKED_SEATS_BY_RESERVATION, new Object[] {reservationId}, new BookedSeatMapper());
	}
	
	public void addBookedSeats(Reservation reservation) {
		List<BookedSeat> bookedSeats = reservation.getBookedSeats();
		int reservationId = reservation.getReservationId();
		bookedSeats.forEach((bookedSeat) -> jdbcTemplate.update(SQL_ADD_BOOKED_SEAT, reservationId, bookedSeat.getSeatId(), bookedSeat.getTicketTypeId(), bookedSeat.getScreeningId()));
	}
	
	public List<BookedSeat> getBookedSeatsByScreeningId(int screeningId) {
		return jdbcTemplate.query(SQL_GET_BOOKED_SEATS_BY_SCREENING, new Object[] {screeningId}, new BookedSeatMapper());
	}
	
	public boolean doesExist(int seatId, int screeningId) {
		return jdbcTemplate.queryForObject(SQL_DOES_EXIST_BY_SEAT_AND_SCREENING, new Object[] {seatId,screeningId},  Boolean.class);
	}
}
