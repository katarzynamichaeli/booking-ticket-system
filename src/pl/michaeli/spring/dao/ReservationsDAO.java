package pl.michaeli.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import pl.michaeli.model.Reservation;
import pl.michaeli.model.ReservationMapper;


@Component
public class ReservationsDAO {
	JdbcTemplate jdbcTemplate;

	private final String SQL_GET_RESERVATION_BY_ID = "SELECT * FROM RESERVATIONS WHERE ID = ?;";
	private final String SQL_UPDATE_RESERVATION_BY_VOUCHER = "UPDATE RESERVATIONS SET VOUCHER_ID=?,TOTAL_AMOUNT=TOTAL_AMOUNT/2 WHERE ID=?";
	private final String SQL_DOES_EXIST = "SELECT EXISTS(SELECT * FROM RESERVATIONS WHERE ID=?);";
	private final String SQL_ADD_RESERVATION = "INSERT INTO RESERVATIONS(NAME,SURNAME,SCREENING_ID,TOTAL_AMOUNT,EXPIRATION_DATE) VALUES (?,?,?,?,?);";
	
	@Autowired
	public ReservationsDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Reservation getReservationById(int id) {
		return jdbcTemplate.queryForObject(SQL_GET_RESERVATION_BY_ID, new Object[] {id}, new ReservationMapper());
	}
	
	public boolean updateReservationByVoucher(int voucherId, int reservationId) {
		return jdbcTemplate.update(SQL_UPDATE_RESERVATION_BY_VOUCHER, voucherId, reservationId) > 0;
	}
	
	public boolean doesExist(int id) {
		return jdbcTemplate.queryForObject(SQL_DOES_EXIST, new Object[] {id},  Boolean.class);
	}
	
	public Reservation addReservation(Reservation reservation) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL_ADD_RESERVATION, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, reservation.getName());
				ps.setString(2, reservation.getSurname());
				ps.setInt(3, reservation.getScreeningId());
				ps.setDouble(4, reservation.getTotalAmount());
				ps.setTimestamp(5, new java.sql.Timestamp(reservation.getExpirationTime().getTime()));
				return ps;
			}
		}, holder);

		int newReservationId = holder.getKey().intValue();
		reservation.setReservationId(newReservationId);
		return reservation;
	}
	
}
