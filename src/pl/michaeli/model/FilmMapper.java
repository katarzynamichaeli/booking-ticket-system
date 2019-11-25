package pl.michaeli.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FilmMapper implements RowMapper<Film>{
	public Film mapRow(ResultSet resultSet, int i) throws SQLException {
		Film film = new Film();
		film.setFilmId(resultSet.getInt("ID"));
		film.setTitle(resultSet.getString("TITLE"));
		return film;
	}
}
