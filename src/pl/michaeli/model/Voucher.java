package pl.michaeli.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Voucher {
	private int voucherId;
	private String code;
	private String state;
	
	@XmlAttribute
	public int getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(int voucherId) {
		this.voucherId = voucherId;
	}
	@XmlAttribute
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@XmlAttribute
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}