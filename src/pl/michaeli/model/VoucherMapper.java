package pl.michaeli.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VoucherMapper implements RowMapper<Voucher>{
	public Voucher mapRow(ResultSet resultSet, int i) throws SQLException {
		Voucher voucher = new Voucher();
		voucher.setVoucherId(resultSet.getInt("ID"));
		voucher.setCode(resultSet.getString("CODE"));
		voucher.setState(resultSet.getString("STATE"));
		return voucher;
	}
}
