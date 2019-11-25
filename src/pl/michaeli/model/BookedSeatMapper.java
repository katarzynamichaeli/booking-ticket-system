package pl.michaeli.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BookedSeatMapper implements RowMapper<BookedSeat>{
	public BookedSeat mapRow(ResultSet resultSet, int i) throws SQLException {
		BookedSeat bookedSeat = new BookedSeat();
		bookedSeat.setBookedSeatId(resultSet.getInt("ID"));
		bookedSeat.setReservationId(resultSet.getInt("RESERVATION_ID"));
		bookedSeat.setSeatId(resultSet.getInt("SEAT_ID"));
		bookedSeat.setTicketTypeId(resultSet.getInt("TICKET_TYPE_ID"));
		bookedSeat.setScreeningId(resultSet.getInt("SCREENING_ID"));
		return bookedSeat;
	}
}
