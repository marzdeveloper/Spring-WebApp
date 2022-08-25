package advPro.GestioneDispositivi.Model.Entities;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Device")
public class Device  implements Serializable {
	
	private static final long serialVersionUID = 1847253395173328425L;

	private int id;
	private String serialNumber;
	private String brand;
	private String model;
	private String reason;
	private String document;
	
	private Date checkIn;
	private Date checkOut;
	
	private Sender sender;
	private Set<Job> jobs = new HashSet<Job>();		
	private Set<Positioning> positionings = new HashSet<Positioning>();
	
	//Get Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public int getId() {
		return this.id;
	}
	
	//Get SerialNumber
	@Column(name = "SerialNumber", nullable = false, unique = true)
	public String getSerialNumber() {
		return this.serialNumber;
	}
	
	//Get Brand
	@Column(name = "Brand", nullable = false)
	public String getBrand() {
		return this.brand;
	}
	
	//Get Model
	@Column(name = "Model", nullable = false)
	public String getModel() {
		return this.model;
	}
		
	//Get Reason
	@Column(name = "Reason")
	public String getReason() {
		return this.reason;
	}
	
	//Get Document
	@Column(name = "Document")
	public String getDocument() {
		return this.document;
	}
	
	//Get CheckIn
	@Temporal(TemporalType.DATE)
	@Column(name = "CheckIn", nullable = true)
	public Date getCheckIn() {
		return this.checkIn;
	}
	
	//Get CheckOut
	@Temporal(TemporalType.DATE)
	@Column(name = "CheckOut")
	public Date getCheckOut() {
		return this.checkOut;
	}
	
	//Set Id
	public void setId(int id) {
		this.id = id;
	}
	
	//Set SerialNumber
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	//Set Brand
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	//Set Model
	public void setModel(String model) {
		this.model = model;
	}
	
	//Set Reason
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	//Set Document
	public void setDocument(String document) {
		this.document = document;
	}
	
	//Set CheckIn
	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}
	
	//Set CheckOut
	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}
	
	@Override
	public String toString() {
		return "Device - Id: " + id + ", SerialNumber: " + serialNumber +
				", Brand: " + brand + ", Model: " + model + ", Reason: " + reason +
				", Document: " + document + ", CheckIn: " + checkIn + 
				", CheckOut: " + checkOut;
	}
	
	//RELATIONSHIPS
	
	//Many devices have one sender
	@ManyToOne
	@JoinColumn(name = "Sender_id", nullable=true)
	public Sender getSender() {
		return this.sender;
	}
	
	public void setSender(Sender sender) {
		this.sender = sender;
	}
	
	//One device has many jobs
	@OneToMany(mappedBy="device", cascade=CascadeType.ALL,
			orphanRemoval=true)
	public Set<Job> getJobs() {
		return this.jobs;
	}
	
	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}
	
	//One device has many positionings
	@OneToMany(mappedBy="device", cascade=CascadeType.ALL,
			orphanRemoval=true)
	public Set<Positioning> getPositionings() {
		return this.positionings;
	}
		
	public void setPositionings(Set<Positioning> positionings) {
		this.positionings = positionings;
	}

}
