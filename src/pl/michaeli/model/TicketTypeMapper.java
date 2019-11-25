package pl.michaeli.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TicketTypeMapper implements RowMapper<TicketType>{
	public TicketType mapRow(ResultSet resultSet, int i) throws SQLException {
		TicketType ticketType = new TicketType();
		ticketType.setTicketTypeId(resultSet.getInt("ID"));
		ticketType.setType(resultSet.getString("TYPE"));
		ticketType.setPrice(resultSet.getDouble("PRICE"));
		return ticketType;
	}
}
