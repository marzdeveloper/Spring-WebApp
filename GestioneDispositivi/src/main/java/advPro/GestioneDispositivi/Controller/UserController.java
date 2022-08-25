package advPro.GestioneDispositivi.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import advPro.GestioneDispositivi.Model.Entities.Role;
import advPro.GestioneDispositivi.Model.Entities.User;
import advPro.GestioneDispositivi.Services.RoleService;
import advPro.GestioneDispositivi.Services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	private UserService userService;
	private RoleService roleService;
	
	@GetMapping()
	public String teams(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg) {
		model.addAttribute("title", "Users");
		
		List<User> list = userService.findAll();
		model.addAttribute("users", list);
		model.addAttribute("alertMsg", msg);
		
		return "user/list";
	}
	
	@GetMapping(value = "/getDetails/{username}")
	public String getDetails(Locale locale, Model model, @PathVariable("username") String username) {
		try {
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
			model.addAttribute("roles", user.getRoles());
			model.addAttribute("error", false);
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			model.addAttribute("error", true);
		}
		
		return "user/modal";
	}
	
	@RequestMapping(value = "/showNew", method = RequestMethod.GET)
	public String showNew(Locale locale, Model model) {
		model.addAttribute("roles", roleService.findAll());
		return "user/modal_new";
	}
	
	@RequestMapping(value = "/showEdit/{username}", method = RequestMethod.GET)
	public String showEdit(Locale locale, Model model, @PathVariable("username") String username) {
		try {
			User user = userService.findByUsername(username);
			user.setPassword(null);
			
			List<Role> role_all = roleService.findAll();
			Set<Role> role_user = user.getRoles();
			ArrayList<String[]> lista = new ArrayList<String[]>();
			
			for (int i = 0; i < role_all.size(); i++) {
				String[] x = new String[3];
				x[2] = "0";
				for (Role r : role_user) {
					x[0] = role_all.get(i).getId() + "";
					x[1] = role_all.get(i).getName();
					if (role_all.get(i).getId() == r.getId()) {
						x[2] = "1";
						break;
					}
				}
				lista.add(x);
			}
			
			model.addAttribute("user", user);
			model.addAttribute("roles", lista);
			model.addAttribute("error", false);
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			model.addAttribute("error", true);
		}
		return "user/modal_edit";
	}
	
	@RequestMapping(value = "/saveData", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String saveData(Locale locale, Model uModel,
			@RequestParam("username") String username, @RequestParam("password") String password, 
			@RequestParam(value = "enabled", required = false) boolean enabled, @RequestParam("role") long role) {
		boolean resp = false;
		String msg = "Operation failed: username can't be empty";
		try {
			if(username != null && !username.trim().isEmpty()) {
				User user = userService.findByUsername(username);
				if (user != null) {
					user.setUsername(username);
					user.setEnabled(enabled);
					if (password != null && !password.trim().isEmpty()) {
						user.setPassword(userService.encryptPassword(password));
					}
					if (role > 0) {
						Set<Role> listR = new HashSet<Role>();
						user.setRoles(listR);
						user.addRole(roleService.findRoleById(role));
					}
					msg = "Changes saved successfully";
				} else {
					if (role > 0) {
						user = userService.create(username, password, roleService.findRoleById(role));
						msg = "User created successfully";
					}
				}
				this.userService.update(user);
				resp = true;
			}
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String deleteData(Locale locale, Model model, @RequestParam("username") String username) {
		boolean resp = false;
		String msg = "";
		try {
			resp = userService.delete(username);
			if (resp) {
				msg = "User deleted successfully";
			} else {
				msg = "Admins can't be deleted";
			}
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@Autowired
	public void setServices(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

}