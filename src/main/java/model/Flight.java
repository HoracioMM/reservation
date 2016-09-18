package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Flight implements Serializable {

	private static final long serialVersionUID = -5268490483639006406L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int flightId;
	@Column(unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFlightId() {
		return flightId;
	}

	@Override
	public String toString() {
		return "Flight [flightId=" + flightId + ", name=" + name + "]";
	}

}
