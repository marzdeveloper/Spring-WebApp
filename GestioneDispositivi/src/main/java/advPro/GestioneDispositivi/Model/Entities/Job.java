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
@Table(name = "Job")
public class Job  implements Serializable {
	
	private static final long serialVersionUID = 1847253395173328425L;
		
	private int id;
	private String description;
	private Date start;
	private Date end;

	private Device device;
	private Team team;
	private Employee employee;
	
	//Get JobId
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public int getId() {
		return this.id;
	}
	
	//Get Description
	@Column(name = "Description")
	public String getDescription() {
		return this.description;
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
	
	//Set JobId
	public void setId(int id) {
		this.id = id;
	}
	
	//Set Description
	public void setDescription(String description) {
		this.description = description;
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
		return "Job - Id: " + id + ", Description: " + description +
				", Start: " + start + ", End: " + end;
	}
	
	//RELATIONSHIPS
	
	//Many jobs have one device
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Device_id", nullable=false)
	public Device getDevice() {
		return this.device;
	}
	
	public void setDevice(Device device) {
		this.device = device;
	}
	
	//Many jobs have one team
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Team_id", nullable=true)
	public Team getTeam() {
		return this.team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}	
	
	//Many jobs have one employee
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Employee_id", nullable=true)
	public Employee getEmployee() {
		return this.employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}	
		
}
