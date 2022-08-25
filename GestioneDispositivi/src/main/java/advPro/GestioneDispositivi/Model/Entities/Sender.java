package advPro.GestioneDispositivi.Model.Entities;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Sender")
public class Sender implements Serializable {

	private static final long serialVersionUID = 1847253395173328425L;

	private int id;	
	private String name;
	
	private Set<Device> devices = new HashSet<Device>();

	//Get Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public int getId() {
		return this.id;
	}

	//Set Id
	public void setId(int id) {
		this.id = id;
	}
	
	//Get Name
	@Column(name = "Name", nullable = false, unique = true)
	public String getName() {
		return this.name;
	}

	//Set Name
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Sender - Id: " + id + ", Name: " + name;
	}

	//RELATIONSHIPS
	
	//One sender has many devices
	@OneToMany(mappedBy="sender")
	public Set<Device> getDevices() {
		return this.devices;
	}
	
	public void addDevice(Device device) {
		device.setSender(this);
		this.devices.add(device);		
    }
	
	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}	
}