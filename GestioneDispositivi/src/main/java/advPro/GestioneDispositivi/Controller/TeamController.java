package advPro.GestioneDispositivi.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Team;
import advPro.GestioneDispositivi.Services.EmployeeService;
import advPro.GestioneDispositivi.Services.JobService;
import advPro.GestioneDispositivi.Services.TeamService;


@Controller
@RequestMapping("/team")
public class TeamController {
	private TeamService teamService;
	private JobService jobService;
	private EmployeeService employeeService;
	
	@GetMapping()
	public String teams(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg) {
		model.addAttribute("title", "Teams");
		
		List<Team> list = teamService.findAll();
		model.addAttribute("teams", list);
		model.addAttribute("alertMsg", msg);
		
		return "team/list";
	}
	
	@GetMapping(value = "/getDetails/{id}")
	public String getDetails(Locale locale, Model model, @PathVariable("id") int id) {
		Team team = teamService.findById(id);
		
		List<Job> list_jobs = jobService.findAllByTeamId(id);
		List<Employee> list_employees = employeeService.findAllByTeamId(id);
		
		model.addAttribute("team", team);
		model.addAttribute("jobs", list_jobs);
		model.addAttribute("employees", list_employees);

		return "team/modal";
	}
	
	@RequestMapping(value = "/showNew", method = RequestMethod.GET)
	public String showNew(Locale locale, Model model) {
		model.addAttribute("employee", new Team());
		return "team/modal_new";
	}
	
	@RequestMapping(value = "/showEdit/{id}", method = RequestMethod.GET)
	public String showEdit(Locale locale, Model model, @PathVariable("id") int id) {
		Team team = teamService.findById(id);
		model.addAttribute("team", team);
		
		return "team/modal_edit";
	}
	
	@RequestMapping(value = "/showLink/{id}", method = RequestMethod.GET)
	public String showLink(Locale locale, Model model, @PathVariable("id") int id) {
		Team team = teamService.findById(id);
		model.addAttribute("team", team);
		
		List<Employee> lista_emp_team = employeeService.findAllByTeamId(id);
		List<Employee> lista_emp_all = employeeService.findAll();
		List<String[]> lista_emps = new ArrayList<String[]>();
		for (int i = 0; i < lista_emp_all.size(); i++) {
			String[] s = new String[3];
			s[0] = lista_emp_all.get(i).getId() + "";
			s[1] = lista_emp_all.get(i).getName() + " " + lista_emp_all.get(i).getSurname();
			s[2] = "0";
			for (Employee e : lista_emp_team) {
				if (lista_emp_all.get(i).getId() == e.getId()) {
					s[2] = "1";
					break;
				}	
			}
			
			lista_emps.add(s);
		}
		model.addAttribute("employees", lista_emps);
		return "team/modal_link";
	}
	
	@RequestMapping(value = "/saveData", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String saveData(Locale locale, Model uModel,
			@RequestParam("name") String name, @RequestParam("type") String type, @RequestParam("description") String des,
			@RequestParam(value = "id", required = false) String id) {
		boolean resp = false;
		String msg = "Operation failed: one or more empty fields";
		try {
			if(name != null && !name.trim().isEmpty() && type != null && !type.trim().isEmpty() && des != null && !des.trim().isEmpty()) {
				Team team = null;
				if (id != null && !id.trim().isEmpty()) {
					team = teamService.findById(Integer.parseInt(id));
					team.setName(name);
					team.setType(type);
					team.setDescription(des);
					msg = "Changes saved succesfully";
				} else {
					team = teamService.create(name, type, des);
					msg = "Team created successfully";
				}
				
				this.teamService.update(team);
				resp = true;
			}
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@RequestMapping(value = "/saveLink", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String saveLink(Locale locale, Model uModel,
			@RequestParam(value = "employees[]", required = false) String[] employees, @RequestParam(value = "id") int id) {
		boolean resp = false;
		String msg = "";
		try {
			Team t = teamService.findById(id);
			
			if (employees != null) {
				t.setEmployees(new HashSet<Employee>());
				for (int i = 0; i < employees.length; i++) {
					Employee e = employeeService.findById(Integer.parseInt(employees[i]));
					t.addEmployee(e);
				}
			} else {
				t.setEmployees(new HashSet<Employee>());
			}
			this.teamService.update(t);
			msg = "Changes saved successfully";
			resp = true;
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String deleteData(Locale locale, Model model, @RequestParam("id") int id) {
		boolean resp = false;
		String msg = "";
		try {
			this.teamService.delete(id);
			msg = "Team deleted successfully";
			resp = true;
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@Autowired
	public void setServices(TeamService teamService, JobService jobService, EmployeeService employeeService) {
		this.teamService = teamService;
		this.jobService = jobService;
		this.employeeService = employeeService;
	}

}