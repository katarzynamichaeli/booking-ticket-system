package pl.michaeli.spring.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import pl.michaeli.model.Seat;
import pl.michaeli.model.SeatMapper;

@Component
public class SeatsDAO {
	JdbcTemplate jdbcTemplate;

	private final String SQL_FIND_AVAILABLE_SEATS = "SELECT * FROM SEATS S WHERE S.ROOM_NO=? AND S.ID NOT IN (SELECT BS.SEAT_ID FROM BOOKED_SEATS BS WHERE BS.SCREENING_ID=?);";
	private final String SQL_GET_SEAT_BY_ID = "SELECT * FROM SEATS WHERE ID=?;";

	@Autowired
	public SeatsDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Seat> getAvailableSeats(int screeningId, int roomNo) {
		return jdbcTemplate.query(SQL_FIND_AVAILABLE_SEATS, new Object[] {roomNo, screeningId}, new SeatMapper());
	}
	
	public Seat getSeatById(int seatId) {
		return jdbcTemplate.queryForObject(SQL_GET_SEAT_BY_ID, new Object[] {seatId}, new SeatMapper());
	}
}
