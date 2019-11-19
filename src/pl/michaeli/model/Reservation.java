package pl.michaeli.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reservation {
	private int reservationId;
	private String name;
	private String surname;
	private int screeningId;
	private int voucherId;
	private double totalAmount;
	private Date expirationTime;
	private List<BookedSeat> bookedSeats;
	
	@XmlAttribute
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	@XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	@XmlAttribute
	public int getScreeningId() {
		return screeningId;
	}
	public void setScreeningId(int screeningId) {
		this.screeningId = screeningId;
	}
	@XmlAttribute
	public int getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(int voucherId) {
		this.voucherId = voucherId;
	}
	@XmlAttribute
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	@XmlAttribute
	public Date getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}
	@XmlElementWrapper(name = "bookedSeats")
    @XmlElement(name = "bookedSeat")
	public List<BookedSeat> getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(List<BookedSeat> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", name=" + name + ", surname=" + surname
				+ ", screeningId=" + screeningId + ", voucherId=" + voucherId + ", totalAmount=" + totalAmount
				+ ", expirationTime=" + expirationTime + ", bookedSeats=" + bookedSeats + "]";
	}
}