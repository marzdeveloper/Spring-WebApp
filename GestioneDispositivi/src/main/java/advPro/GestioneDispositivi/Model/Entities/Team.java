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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Team")
public class Team  implements Serializable {
	
	private static final long serialVersionUID = 1847253395173328425L;
		
	private int id;	
	private String name;
	private String type;
	private String description;
	
	private Set<Job> jobs = new HashSet<Job>();
	private Set<Employee> employees = new HashSet<Employee>();
	
	//Get Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public int getId() {
		return this.id;
	}
	
	//Get Name
	@Column(name = "Name", nullable = false, unique = true)
	public String getName() {
		return this.name;
	}
	
	//Get Type
	@Column(name = "Type")
	public String getType() {
		return this.type;
	}
	
	//Get Description
	@Column(name = "Description")
	public String getDescription() {
		return this.description;
	}
		
	
	//Set Id
	public void setId(int id) {
		this.id = id;
	}
	
	//Set Name
	public void setName(String name) {
		this.name = name;
	}
	
	//Set Type
	public void setType(String type) {
		this.type = type;
	}
	
	//Set Description
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Team - Id: " + id + ", Name: " + name + ", Type: " + type +
				", Description: " + description;
	}
	
	//RELATIONSHIPS
	
	//One team has many jobs
	@OneToMany(mappedBy="team", cascade=CascadeType.ALL,
			orphanRemoval=true, fetch = FetchType.LAZY)
	public Set<Job> getJobs() {
		return this.jobs;
	}
	
	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}
	
	public void addJob(Job job) {
		job.setTeam(this);
		this.jobs.add(job);		
    }
	
	//Many teams have many employees
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="Membership",
		 joinColumns = @JoinColumn(name = "Team_id"),
		 inverseJoinColumns = @JoinColumn(name = "Employee_id"))
	public Set<Employee> getEmployees() {
		return this.employees;
	}	
	
	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	
	public void addEmployee(Employee employee) {
		employee.getTeams().add(this);
		this.employees.add(employee);		
    }
	
	public void removeEmployee(Employee employee) {
		employee.getTeams().remove(this);
		this.employees.remove(employee);		
    }
}
