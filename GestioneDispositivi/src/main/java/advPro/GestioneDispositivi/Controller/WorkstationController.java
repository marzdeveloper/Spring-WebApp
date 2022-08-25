package advPro.GestioneDispositivi.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Workstation;
import advPro.GestioneDispositivi.Services.EmployeeService;
import advPro.GestioneDispositivi.Services.LocationService;
import advPro.GestioneDispositivi.Services.WorkstationService;
import advPro.GestioneDispositivi.Utils.Utils;


@Controller
@RequestMapping("/workstation")
public class WorkstationController {
	private WorkstationService workstationService;
	private EmployeeService employeeService;
	private LocationService locationService;
	
	@GetMapping()
	public String workstations(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg) {
		model.addAttribute("title", "Workstations");
		
		List<Workstation> list = workstationService.findAll();
		model.addAttribute("workstations", list);
		model.addAttribute("alertMsg", msg);
		
		return "workstation/list";
	}
	
	@RequestMapping(value = "/showNew", method = RequestMethod.GET)
	public String showNew(Locale locale, Model model) {
		model.addAttribute("workstation", new Workstation());
		model.addAttribute("employees", employeeService.findAll());
		model.addAttribute("locations", locationService.findAll());
		return "workstation/modal_new";
	}
	
	@RequestMapping(value = "/saveData", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String saveData(Locale locale, Model uModel,
			@RequestParam("employee") String emp, @RequestParam("location") String loc) {
		boolean resp = false;
		String msg = "Operation failed: one or more empty fields";
		try {
			if (emp != null && !emp.isEmpty() && loc != null && !loc.isEmpty()) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				Workstation work = new Workstation();
				Employee e = employeeService.findById(Integer.parseInt(emp));
				Location l = locationService.findById(Integer.parseInt(loc));
				
				work.setEmployee(e);
				work.setLocation(l);
				work.setStart(Utils.date(dateFormat.format(date)));
				
				this.workstationService.update(work);
				msg = "Workstation created successfully";
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
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			Workstation w = this.workstationService.findById(id);
			w.setEnd(Utils.date(dateFormat.format(date)));
			
			this.workstationService.update(w);
			msg = "Workstation ended successfully";
			resp = true;
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@Autowired
	public void setServices(WorkstationService workstationService, EmployeeService employeeService, LocationService locationService) {
		this.workstationService = workstationService;
		this.employeeService = employeeService;
		this.locationService = locationService;
	}

}