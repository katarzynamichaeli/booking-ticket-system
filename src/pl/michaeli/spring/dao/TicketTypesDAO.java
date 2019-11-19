package pl.michaeli.spring.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import pl.michaeli.model.TicketTypeMapper;

@Component
public class TicketTypesDAO {
	JdbcTemplate jdbcTemplate;

	private final String SQL_GET_TICKET_TYPE_BY_ID = "SELECT * FROM TICKET_TYPES WHERE ID = ?;";

	@Autowired
	public TicketTypesDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public double getPrice(int id) {
		return jdbcTemplate.queryForObject(SQL_GET_TICKET_TYPE_BY_ID, new Object[] {id}, new TicketTypeMapper()).getPrice();
	}

}
