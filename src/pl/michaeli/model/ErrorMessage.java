package pl.michaeli.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
	private final String message;

	public ErrorMessage(String message) {
		this.message = message;
	}
	
	@XmlAttribute
	public String getMessage() {
		return message;
	}

}