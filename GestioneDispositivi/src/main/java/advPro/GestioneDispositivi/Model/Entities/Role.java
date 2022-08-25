package advPro.GestioneDispositivi.Model.Entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Role")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 1847253395173328425L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @Column
    private String name;
 
    public Long getId() {
    	return this.id;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public boolean isAdmin() {
    	return this.name.equals("ADMIN");
    }
}