package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WantedPerson implements Serializable {

	private static final long serialVersionUID = 4684975556947588921L;
	@Id
	private int wantedPersonId;

	public int getWantedPersonId() {
		return wantedPersonId;
	}

	public void setWantedPersonId(int wantedPersonId) {
		this.wantedPersonId = wantedPersonId;
	}

}
