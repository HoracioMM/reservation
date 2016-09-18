package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TravelAgency implements Serializable {

	private static final long serialVersionUID = -6129185177177795720L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int travelAgencyId;
	private String name;
	private String email;
	private String address;

	@OneToMany(mappedBy = "travelAgency", cascade = CascadeType.ALL)
	private Set<Traveller> travellers = new HashSet<Traveller>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Traveller> getTravellers() {
		return travellers;
	}

	public void setTravellers(Set<Traveller> travellers) {
		this.travellers = travellers;
	}

	public int getTravelAgencyId() {
		return travelAgencyId;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "TravelAgency [travelAgencyId=" + travelAgencyId + ", name=" + name + ", email=" + email + ", address="
				+ address + ", travellers=" + (travellers != null ? toString(travellers, maxLen) : null) + "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

}
