package advPro.GestioneDispositivi.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Dao.UserDetailsDao;
import advPro.GestioneDispositivi.Model.Entities.Role;
import advPro.GestioneDispositivi.Model.Entities.User;

@Service("userDetailsService")
public class UserDetailsServiceDefault implements UserDetailsService {

  @Autowired
  private UserDetailsDao userDetailsDao;

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	  User user = userDetailsDao.findUserByUsername(username);
	  UserBuilder builder = null;
	  if (user != null) {
		  //qui "mappiamo" uno User della nostra app in uno User di spring
	      builder = org.springframework.security.core.userdetails.User.withUsername(username);
	      builder.disabled(!user.isEnabled());
	      builder.password(user.getPassword());
	            
	      String [] roles = new String[user.getRoles().size()];

	      int j = 0;
	      for (Role r : user.getRoles()) {
	    	  roles[j++] = r.getName();
	      }
	            
	      builder.roles(roles);
	  } else {
		  throw new UsernameNotFoundException("User not found.");
	  }
	  return builder.build();
  }
}
