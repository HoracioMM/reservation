package model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Qr implements Serializable {

	private static final long serialVersionUID = -2817403522882362922L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int qrId;
	private String qrCode;
	private String alarmTrigger;

	@OneToOne(mappedBy = "qr", cascade = CascadeType.ALL)
	private Traveller traveller;

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getAlarmTrigger() {
		return alarmTrigger;
	}

	public void setAlarmTrigger(String alarmTrigger) {
		this.alarmTrigger = alarmTrigger;
	}

	public Traveller getTraveller() {
		return traveller;
	}

	public void setTraveller(Traveller traveller) {
		this.traveller = traveller;
	}

	public int getQrId() {
		return qrId;
	}

	@Override
	public String toString() {
		return "Qr [qrId=" + qrId + ", qrCode=" + qrCode + ", alarmTrigger=" + alarmTrigger + ", traveller=" + traveller
				+ "]";
	}

}
