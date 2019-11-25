package pl.michaeli.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookedSeat {
	private int bookedSeatId;
	private int reservationId;
	private int seatId;
	private int ticketTypeId;
	private int screeningId;
	
	@XmlAttribute
	public int getBookedSeatId() {
		return bookedSeatId;
	}
	public void setBookedSeatId(int bookedSeatId) {
		this.bookedSeatId = bookedSeatId;
	}
	@XmlAttribute
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	@XmlAttribute
	public int getSeatId() {
		return seatId;
	}
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	@XmlAttribute
	public int getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(int ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	@XmlAttribute
	public int getScreeningId() {
		return screeningId;
	}
	public void setScreeningId(int screeningId) {
		this.screeningId = screeningId;
	}
	@Override
	public String toString() {
		return "BookedSeat [bookedSeatId=" + bookedSeatId + ", reservationId=" + reservationId + ", seatId=" + seatId
				+ ", ticketTypeId=" + ticketTypeId + ", screeningId=" + screeningId + "]";
	}
}
