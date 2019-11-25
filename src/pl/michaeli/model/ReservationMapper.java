package pl.michaeli.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ReservationMapper implements RowMapper<Reservation>{
	public Reservation mapRow(ResultSet resultSet, int i) throws SQLException {
		Reservation reservation = new Reservation();
		reservation.setReservationId(resultSet.getInt("ID"));
		reservation.setName(resultSet.getString("NAME"));
		reservation.setSurname(resultSet.getString("SURNAME"));
		reservation.setScreeningId(resultSet.getInt("SCREENING_ID"));
		reservation.setVoucherId(resultSet.getInt("VOUCHER_ID"));
		reservation.setTotalAmount(resultSet.getDouble("TOTAL_AMOUNT"));
		reservation.setExpirationTime(new java.util.Date(resultSet.getTimestamp("EXPIRATION_DATE").getTime()));
		return reservation;
	}
}
