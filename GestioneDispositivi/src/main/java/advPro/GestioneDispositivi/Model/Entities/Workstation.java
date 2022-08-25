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
@Table(name = "Workstation")
public class Workstation  implements Serializable {
	
	private static final long serialVersionUID = 1847253395173328425L;
		
	private int id;
	private Date start;
	private Date end;

	private Employee employee;
	private Location location;
	
	//Get Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public int getId() {
		return this.id;
	}
		
	//Get Start
	@Temporal(TemporalType.DATE)
	@Column(name = "Start")
	public Date getStart() {
		return this.start;
	}
	
	//Get End
	@Temporal(TemporalType.DATE)
	@Column(name = "End")
	public Date getEnd() {
		return this.end;
	}
	
	//Set Id
	public void setId(int id) {
		this.id = id;
	}
	
	//Set Start
	public void setStart(Date start) {
		this.start = start;
	}
	
	//Set End
	public void setEnd(Date end) {
		this.end = end;
	}
	
	@Override
	public String toString() {
		return "Workstation - Id: " + id +
				", Start: " + start + ", End: " + end;
	}
	
	//RELATIONSHIPS
	
	//Many workstations have one employee
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Employee_id", nullable=false)
	public Employee getEmployee() {
		return this.employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	//Many workstations have one location
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Location_id", nullable=false)
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}		
		
}
