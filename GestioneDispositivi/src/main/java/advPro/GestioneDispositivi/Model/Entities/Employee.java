package advPro.GestioneDispositivi.Model.Entities;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee  implements Serializable {
	
	private static final long serialVersionUID = 1847253395173328425L;
		
	private int id;	
	private String name;
	private String surname;
	
	private Set<Team> teams = new HashSet<Team>();
	private Set<Positioning> positionings = new HashSet<Positioning>();
	private Set<Workstation> workstations = new HashSet<Workstation>();
	private Set<Job> jobs = new HashSet<Job>();
	
	/*************************************************************************************
	 * GETTERS
	 *************************************************************************************/
	//Get Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public int getId() {
		return this.id;
	}
	
	//Get Name
	@Column(name = "Name", nullable = false)
	public String getName() {
		return this.name;
	}
	
	//Get Surname
	@Column(name = "Surname", nullable = false)
	public String getSurname() {
		return this.surname;
	}
	
	/*************************************************************************************
	 * SETTERS
	 *************************************************************************************/
	//Set Id
	public void setId(int id) {
		this.id = id;
	}
	
	//Set Name
	public void setName(String name) {
		this.name = name;
	}
	
	//Set Surname
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	/*************************************************************************************
	 * RELATIONSHIPS
	 *************************************************************************************/
	//Many employees have many teams
	@ManyToMany(mappedBy= "employees", fetch = FetchType.EAGER)
	public Set<Team> getTeams() {
		return this.teams;
	}
	
	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}
	
	public void addTeam(Team team) {
		team.getEmployees().add(this);
		this.teams.add(team);		
    }
	
	public void removeTeam(Team team) {
		team.getEmployees().remove(this);
		this.teams.remove(team);		
    }
	
	//One employee has many positionings
	@OneToMany(mappedBy="employee", cascade=CascadeType.ALL,
			orphanRemoval=true, fetch = FetchType.LAZY)
	public Set<Positioning> getPositionings() {
		return this.positionings;
	}
	
	public void setPositionings(Set<Positioning> positionings) {
		this.positionings = positionings;
	}
	
	public void addPositioning(Positioning positioning) {
		positioning.setEmployee(this);
		this.positionings.add(positioning);		
    }
	
	//One employee has many workstations
	@OneToMany(mappedBy="employee", cascade=CascadeType.ALL,
			orphanRemoval=true, fetch = FetchType.LAZY)
	public Set<Workstation> getWorkstations() {
		return this.workstations;
	}
	
	public void setWorkstations(Set<Workstation> workstations) {
		this.workstations = workstations;
	}
	
	//One employee has many jobs
	@OneToMany(mappedBy="employee", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
	public Set<Job> getJobs() {
		return this.jobs;
	}
	
	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}
	
	public void addJob(Job job) {
		job.setEmployee(this);
		this.jobs.add(job);		
    }
	
	/*************************************************************************************
	 * TO STRING
	 *************************************************************************************/
	@Override
	public String toString() {
		return "Employee - Id: " + id + ", Name: " + name + ", Surname: " + surname;
	}
}
