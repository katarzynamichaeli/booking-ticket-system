package pl.michaeli.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SeatMapper implements RowMapper<Seat>{
	public Seat mapRow(ResultSet resultSet, int i) throws SQLException {
		Seat seat = new Seat();
		seat.setSeatId(resultSet.getInt("ID"));
		seat.setRoomNo(resultSet.getInt("ROOM_NO"));
		seat.setRowNo(resultSet.getInt("ROW_NO"));
		seat.setSeatNo(resultSet.getInt("SEAT_NO"));
		return seat;
	}
}
