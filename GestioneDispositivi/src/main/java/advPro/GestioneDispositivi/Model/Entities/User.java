package advPro.GestioneDispositivi.Model.Entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "USERS")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1847253395173328425L;
	
  @Id
  @Column(name = "USERNAME")
  private String username;

  @Column(name = "PASSWORD", nullable = false)
  private String password;

  @Column(name = "ENABLED", nullable = false)
  private boolean enabled;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles", 
      joinColumns = @JoinColumn(
        name = "username", referencedColumnName = "username"), 
      inverseJoinColumns = @JoinColumn(
        name = "role_id", referencedColumnName = "id")) 
  private Set<Role> roles = new HashSet<Role>();
  
  public String getUsername() {
	  return this.username;
  }
  
  public String getPassword() {
	  return this.password;
  }
  
  public boolean isEnabled() {
	  return this.enabled;
  }
  
  public void setUsername(String username) {
	  this.username = username;
  }
  
  public void setPassword(String password) {
	  this.password = password;
  }
  
  public void setEnabled(boolean enabled) {
	  this.enabled = enabled;
  } 
  
  public Set<Role> roles () {
	  return this.roles;
  }

  public void addRole(Role role) {
	  if (this.roles == null) {
		  this.roles = new HashSet<Role>();
	  }
	  
	  this.roles.add(role);
  }
  
  public void setRoles(Set<Role> roles) {
	  this.roles = roles;
  }
  
  public Set<Role> getRoles() {
	  return this.roles;
  }
  
}