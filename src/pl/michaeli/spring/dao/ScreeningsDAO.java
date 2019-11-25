package pl.michaeli.spring.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import pl.michaeli.model.Screening;
import pl.michaeli.model.ScreeningMapper;

@Component
public class ScreeningsDAO {
	JdbcTemplate jdbcTemplate;

	private final String SQL_GET_SCREENING_BY_ID = "SELECT * FROM SCREENINGS WHERE ID = ?;";
	private final String SQL_GET_ALL_AVAILABLE_BY_DATE = "SELECT * FROM SCREENINGS S JOIN FILMS F ON S.FILM_ID=F.ID WHERE DATE BETWEEN ? AND ? ORDER BY F.TITLE, S.DATE;";
	private final String SQL_DOES_EXIST = "SELECT EXISTS(SELECT * FROM SCREENINGS WHERE ID=?);";
	
	@Autowired
	public ScreeningsDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Screening getScreeningById(int screeningId) {
		return jdbcTemplate.queryForObject(SQL_GET_SCREENING_BY_ID, new Object[] {screeningId}, new ScreeningMapper());
	}
	
	public List<Screening> getScreeningsByDate(Long dateFrom, Long dateTo) {
		java.sql.Timestamp mysqlDateFrom = new java.sql.Timestamp(dateFrom);
		java.sql.Timestamp mysqlDateTo = new java.sql.Timestamp(dateTo);
		return jdbcTemplate.query(SQL_GET_ALL_AVAILABLE_BY_DATE, new Object[] {mysqlDateFrom, mysqlDateTo}, new ScreeningMapper());
	}
	
	public boolean doesExist(int screeningId) {
		return jdbcTemplate.queryForObject(SQL_DOES_EXIST, new Object[] {screeningId},  Boolean.class);
	}
}
