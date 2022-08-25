package advPro.GestioneDispositivi.Model.Entities;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Positioning")
public class Positioning  implements Serializable {
	
	private static final long serialVersionUID = 1847253395173328425L;
		
	private int id;
	private Date datePositioning;

	private Device device;
	private Location location;
	private Employee employee;
	
	//Get Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public int getId() {
		return this.id;
	}
	
	//Get DatePositioning
	@Temporal(TemporalType.DATE)
	@Column(name = "DatePositioning")
	public Date getDatePositioning() {
		return this.datePositioning;
	}
	
	
	//Set Id
	public void setId(int id) {
		this.id = id;
	}
	
	//Set DatePositioning
	public void setDatePositioning(Date datePositioning) {
		this.datePositioning = datePositioning;
	}
	
	@Override
	public String toString() {
		return "Positioning - Id: " + id + ", DatePositioning: " + datePositioning;
	}
	
	//RELATIONSHIPS
	
	//Many positionings have one device
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Device_id", nullable=false)
	public Device getDevice() {
		return this.device;
	}
	
	public void setDevice(Device device) {
		this.device = device;
	}
	
	//Many positionings have one location
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Location_id", nullable=true)
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}		
	
	//Many positionings have one employee
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Employee_id", nullable=true)
	public Employee getEmployee() {
		return this.employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}		
		
}
