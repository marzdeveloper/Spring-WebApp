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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Team;
import advPro.GestioneDispositivi.Services.DeviceService;
import advPro.GestioneDispositivi.Services.EmployeeService;
import advPro.GestioneDispositivi.Services.JobService;
import advPro.GestioneDispositivi.Services.TeamService;
import advPro.GestioneDispositivi.Utils.Utils;


@Controller
@RequestMapping("/job")
public class JobController {
	private JobService jobService;
	private TeamService teamService;
	private EmployeeService employeeService;
	private DeviceService deviceService;
	
	@GetMapping()
	public String teams(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg) {
		model.addAttribute("title", "Jobs");
		
		List<Job> list = jobService.findAll();
		model.addAttribute("jobs", list);
		model.addAttribute("alertMsg", msg);
		
		return "job/list";
	}
	
	@GetMapping(value = "/getDetails/{id}")
	public String getDetails(Locale locale, Model model, @PathVariable("id") int id) {
		Job job = jobService.findById(id);
		model.addAttribute("job", job);
		
		return "job/modal";
	}
	
	@RequestMapping(value = "/showNew", method = RequestMethod.GET)
	public String showNew(Locale locale, Model model) {
		model.addAttribute("job", new Team());
		model.addAttribute("devices", deviceService.findAll());
		
		return "job/modal_new";
	}
	
	@RequestMapping(value = "/showEdit/{id}", method = RequestMethod.GET)
	public String showEdit(Locale locale, Model model, @PathVariable("id") int id) {
		Team team = teamService.findById(id);
		model.addAttribute("team", team);
		
		return "job/modal_edit";
	}
	
	@RequestMapping(value = "/showLink/{id}", method = RequestMethod.GET)
	public String showLink(Locale locale, Model model, @PathVariable("id") int id) {
		Job job = jobService.findById(id);
		model.addAttribute("job", job);
		model.addAttribute("teams", teamService.findAll());
		model.addAttribute("employees", employeeService.findAll());
		
		return "job/modal_link";
	}
	
	@RequestMapping(value = "/saveData", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String saveData(Locale locale, Model uModel,
			@RequestParam("description") String des, @RequestParam("device") String dev, @RequestParam(value = "id", required = false) String id) {
		boolean resp = false;
		String msg = "Operation failed: one or more empty fields";
		System.out.println("Dev: " + dev);
		try {
			if(des != null && !des.trim().isEmpty() && dev != null && !dev.isEmpty()) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				Job job = null;
				if (id != null && !id.trim().isEmpty()) {
					job = jobService.findById(Integer.parseInt(id));
					job.setDescription(des);
					job.setStart(Utils.date(dateFormat.format(date)));
					msg = "Changes saved successfully";
				} else {
					Device device = deviceService.findById(Integer.parseInt(dev));
					job = jobService.create(des, Utils.date(dateFormat.format(date)), device);
					msg = "Job created successfully";
				}
				this.jobService.update(job);
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
			@RequestParam(value = "team", required = false) String team, @RequestParam(value = "employee", required = false) String emp, 
			@RequestParam(value = "link") int link, @RequestParam(value = "id") int id) {
		boolean resp = false;
		String msg = "";
		try {
			Job j = jobService.findById(id);
			
			if (j != null) {
				if (link == 1) {
					Team t = teamService.findById(Integer.parseInt(team));
					j.setTeam(t);
					j.setEmployee(null);
				} else if (link == 2) {
					Employee e = employeeService.findById(Integer.parseInt(emp));
					j.setEmployee(e);
					j.setTeam(null);
				}
				this.jobService.update(j);
				resp = true;
				msg = "Changes saved successfully";
			}
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "Operation failed: a team or an employee should be selected";
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
			Job j = this.jobService.findById(id);
			j.setEnd(Utils.date(dateFormat.format(date)));
			
			this.jobService.update(j);
			msg = "Job ended successfully";
			resp = true;
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@Autowired
	public void setServices(TeamService teamService, JobService jobService, EmployeeService employeeService, DeviceService deviceService) {
		this.teamService = teamService;
		this.jobService = jobService;
		this.employeeService = employeeService;
		this.deviceService = deviceService;
	}

}