package pl.michaeli.spring.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import pl.michaeli.model.Film;
import pl.michaeli.model.FilmMapper;

@Component
public class FilmsDAO {
	JdbcTemplate jdbcTemplate;

	private final String SQL_GET_FILM_BY_ID = "SELECT * FROM FILMS WHERE ID = ?;";

	@Autowired
	public FilmsDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Film getFilmById(int id) {
		return jdbcTemplate.queryForObject(SQL_GET_FILM_BY_ID, new Object[] {id}, new FilmMapper());
	}

}
