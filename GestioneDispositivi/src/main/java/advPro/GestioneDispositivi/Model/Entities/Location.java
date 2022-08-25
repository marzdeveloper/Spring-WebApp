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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Location")
public class Location  implements Serializable {
	
	private static final long serialVersionUID = 1847253395173328425L;
		
	private int id;	
	private String name;
	private String description;
	
	private Set<Workstation> workstations = new HashSet<Workstation>();
	private Set<Positioning> positionings = new HashSet<Positioning>();
	
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
	
	//Get Description
	@Column(name = "Description")
	public String getDescription() {
		return this.description;
	}
		
	
	//Set LocationId
	public void setId(int id) {
		this.id = id;
	}
	
	//Set Name
	public void setName(String name) {
		this.name = name;
	}
		
	//Set Description
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Location - Id: " + id + ", Name: " + name + 
				", Description: " + description;
	}
	
	//RELATIONSHIPS
	
	//One location has many workstations
	@OneToMany(mappedBy="location", cascade=CascadeType.ALL,
			orphanRemoval=true, fetch = FetchType.LAZY)
	public Set<Workstation> getWorkstations() {
		return this.workstations;
	}
	
	public void setWorkstations(Set<Workstation> workstations) {
		this.workstations = workstations;
	}	
	
	//One location has many positionings
	@OneToMany(mappedBy="location", cascade=CascadeType.ALL,
			orphanRemoval=true, fetch = FetchType.LAZY)
	public Set<Positioning> getPositionings() {
		return this.positionings;
	}
	
	public void setPositionings(Set<Positioning> positionings) {
		this.positionings = positionings;
	}
	
	public void addPositioning(Positioning positioning) {
		positioning.setLocation(this);
		this.positionings.add(positioning);		
    }

}
