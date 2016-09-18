package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "traveller")
public class Traveller implements Serializable {

	private static final long serialVersionUID = -3925288807193818162L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int travellerId;

	@OneToOne
	@JoinColumn(name = "accountId")
	private Account account;

	@OneToOne
	@JoinColumn(name = "qrId")
	private Qr qr;

	// sms code
	private String smsCode;

	@ManyToOne
	@JoinColumn(name = "travelAgencyId")
	private TravelAgency travelAgency;

	// user profile with badge
	@OneToOne
	@JoinColumn(name = "userProfileId")
	private UserProfile userProfile;

	private String idCardNr;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String postalcode;
	private String country;
	private String email;
	private String mobileNr;
	private String pasportNr;

	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;

	private String travelDate;
	private String visitingDate;
	private String flightName;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Qr getQr() {
		return qr;
	}

	public void setQr(Qr qr) {
		this.qr = qr;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public TravelAgency getTravelAgency() {
		return travelAgency;
	}

	public void setTravelAgency(TravelAgency travelAgency) {
		this.travelAgency = travelAgency;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public String getIdCardNr() {
		return idCardNr;
	}

	public void setIdCardNr(String idCardNr) {
		this.idCardNr = idCardNr;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNr() {
		return mobileNr;
	}

	public void setMobileNr(String mobileNr) {
		this.mobileNr = mobileNr;
	}

	public String getPasportNr() {
		return pasportNr;
	}

	public void setPasportNr(String pasportNr) {
		this.pasportNr = pasportNr;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTravellerId() {
		return travellerId;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public String getVisitingDate() {
		return visitingDate;
	}

	public void setVisitingDate(String visitingDate) {
		this.visitingDate = visitingDate;
	}

	@Override
	public String toString() {
		return "Traveller [travellerId=" + travellerId + ", account=" + account + ", qr=" + qr + ", smsCode=" + smsCode
				+ ", travelAgency=" + travelAgency + ", userProfile=" + userProfile + ", idCardNr=" + idCardNr
				+ ", firstname=" + firstName + ", lastname=" + lastName + ", street=" + street + ", city=" + city
				+ ", postalcode=" + postalcode + ", country=" + country + ", email=" + email + ", mobileNr=" + mobileNr
				+ ", pasportNr=" + pasportNr + ", username=" + username + ", travelDate=" + travelDate + ", password="
				+ password + "]";
	}

}
