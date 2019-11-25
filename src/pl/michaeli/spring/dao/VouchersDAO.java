package pl.michaeli.spring.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import pl.michaeli.model.Voucher;
import pl.michaeli.model.VoucherMapper;

@Component
public class VouchersDAO {
	JdbcTemplate jdbcTemplate;

	private final String SQL_GET_VOUCHER_BY_ID = "SELECT * FROM VOUCHERS WHERE ID = ?;";
	private final String SQL_GET_VOUCHER_BY_CODE = "SELECT * FROM VOUCHERS WHERE CODE = ?";
	private final String SQL_CHECK_IS_VALID = "SELECT EXISTS(SELECT * FROM VOUCHERS WHERE CODE=? AND STATE='NEW');";
	private final String SQL_PUT_USED_STATE = "UPDATE VOUCHERS SET STATE = 'USED' WHERE CODE = ?";

	@Autowired
	public VouchersDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Voucher getVoucherById(int id) {
		return jdbcTemplate.queryForObject(SQL_GET_VOUCHER_BY_ID, new Object[] {id}, new VoucherMapper());
	}
	
	public Voucher getVoucherByCode(String code) {
		return jdbcTemplate.queryForObject(SQL_GET_VOUCHER_BY_CODE, new Object[] {code}, new VoucherMapper());
	}
	
	public boolean isValid(String code) {
		return jdbcTemplate.queryForObject(SQL_CHECK_IS_VALID, new Object[] {code},  Boolean.class);
	}
	
	public boolean putUsedState(String code) {
		return jdbcTemplate.update(SQL_PUT_USED_STATE, code) > 0;
	}
}
