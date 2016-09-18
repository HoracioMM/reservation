package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Account implements Serializable {

	private static final long serialVersionUID = -5268490483639006406L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int accountId;
	private Date date;
	private int credits;
	private String ipAddress;
	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
	private Traveller traveller;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Traveller getTraveller() {
		return traveller;
	}

	public void setTraveller(Traveller traveller) {
		this.traveller = traveller;
	}

	public int getAccountId() {
		return accountId;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", date=" + date + ", credits=" + credits + ", ipAddress="
				+ ipAddress + ", traveller=" + traveller + "]";
	}

}
