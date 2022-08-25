package advPro.GestioneDispositivi.Controller;
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
import advPro.GestioneDispositivi.Services.EmployeeService;
import advPro.GestioneDispositivi.Services.JobService;
import advPro.GestioneDispositivi.Services.PositioningService;
import advPro.GestioneDispositivi.Services.WorkstationService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	private EmployeeService employeeService;
	private WorkstationService workstationService;
	private JobService jobService;
	private PositioningService positioningService;
	
	@GetMapping()
	public String employees(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg) {
		model.addAttribute("title", "Employees");
		
		List<Employee> list = employeeService.findAll();
		model.addAttribute("employees", list);
		model.addAttribute("alertMsg", msg);
		
		return "employee/list";
	}
	
	@GetMapping(value = "/getDetails/{id}")
	public String getDetails(Locale locale, Model model, @PathVariable("id") int id) {
		Employee em = employeeService.findById(id);
		
		model.addAttribute("employee", em);
		model.addAttribute("work", workstationService.findAllByEmployeeId(id));
		model.addAttribute("teams", em.getTeams());
		model.addAttribute("jobs", jobService.findAllByEmployeeId(id));
		model.addAttribute("pos", positioningService.findLastByEmployeeId(id));

		return "employee/modal";
	}
	
	@RequestMapping(value = "/showNew", method = RequestMethod.GET)
	public String showNew(Locale locale, Model model) {
		model.addAttribute("employee", new Employee());
		return "employee/modal_new";
	}
	
	@RequestMapping(value = "/showEdit/{id}", method = RequestMethod.GET)
	public String showEdit(Locale locale, Model model, @PathVariable("id") int id) {
		Employee emp = employeeService.findById(id);
		model.addAttribute("employee", emp);
		
		return "employee/modal_edit";
	}
	
	@RequestMapping(value = "/saveData", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String saveData(Locale locale, Model uModel,
			@RequestParam("name") String name, @RequestParam("surname") String surname, @RequestParam(value = "id", required = false) String id) {
		boolean resp = false;
		String msg = "Operation failed: one or more empty fields";
		try {
			if(name != null && !name.trim().isEmpty() && surname != null && !surname.trim().isEmpty()) {
				Employee emp = null;
				if (id != null && !id.trim().isEmpty()) {
					emp = employeeService.findById(Integer.parseInt(id));
					emp.setName(name);
					emp.setSurname(surname);
					msg = "Changes saved successfully";
				} else {
					emp = employeeService.create(name, surname);
					msg = "Employee created successfully";
				}
				this.employeeService.update(emp);
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
	public @ResponseBody String deleteData(Locale locale, Model model, @RequestParam("id") int id) {
		boolean resp = false;
		String msg = "";
		try {
			resp = this.employeeService.delete(id);
			if (resp) {
				msg = "Employee deleted successfully";				
				resp = true;				
			} else {
				msg = "Employee not deleted";
			}
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@Autowired
	public void setServices(EmployeeService employeeService, WorkstationService workstationService, JobService jobService, PositioningService positioningService) {
		this.employeeService = employeeService;
		this.workstationService = workstationService;
		this.jobService = jobService;
		this.positioningService = positioningService;
	}

}
