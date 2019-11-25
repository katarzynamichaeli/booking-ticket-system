package pl.michaeli.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class ScreeningMapper implements RowMapper<Screening>{
	public Screening mapRow(ResultSet resultSet, int i) throws SQLException {
		Screening screening = new Screening();
		screening.setScreeningId(resultSet.getInt("ID"));
		screening.setRoomNo(resultSet.getInt("ROOM_NO"));
		screening.setScreeningDate(new java.util.Date(resultSet.getTimestamp("DATE").getTime()));
		screening.setFilmId(resultSet.getInt("FILM_ID"));
		return screening;
	}
}
