package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Badge implements Serializable {

	private static final long serialVersionUID = 83808341765996729L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int badgeId;
	private String badgeNr;
	private String validationDate;
	
	@Column(name = "UserType")
	@Enumerated(EnumType.ORDINAL)
	private UserType type;

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getBadgeNr() {
		return badgeNr;
	}

	public void setBadgeNr(String badgeNr) {
		this.badgeNr = badgeNr;
	}

	public String getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(String validationDate) {
		this.validationDate = validationDate;
	}

	public int getBadgeId() {
		return badgeId;
	}

	@Override
	public String toString() {
		return "Badge [badgeId=" + badgeId + ", badgeNr=" + badgeNr + ", validationDate=" + validationDate + "]";
	}

}
