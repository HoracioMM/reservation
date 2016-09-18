package model;

import javax.persistence.Entity;

@Entity
public enum UserType {

	TRAVELLER,VISISTOR,TRAVEL_AGENCY,AIRPORT_CREW,ADMIN,OTHER;

}
