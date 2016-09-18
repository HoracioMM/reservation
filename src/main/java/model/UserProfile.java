package model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class UserProfile implements Serializable {

	private static final long serialVersionUID = -6192717597537924745L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int userProfileId;
	private int discountPercentage;
	// could be travel agency, traveler, visitor...
	private String userType;
	@OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL)
	private Traveller traveller;
	@OneToOne
	Badge badge;
	
	@Autowired
	private UserType type;

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Traveller getTraveller() {
		return traveller;
	}

	public void setTraveller(Traveller traveller) {
		this.traveller = traveller;
	}

	public Badge getBadge() {
		return badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
	}

	@Override
	public String toString() {
		return "UserProfile [userProfileId=" + userProfileId + ", discountPercentage=" + discountPercentage
				+ ", userType=" + userType + ", traveller=" + traveller + ", badge=" + badge + "]";
	}

}
