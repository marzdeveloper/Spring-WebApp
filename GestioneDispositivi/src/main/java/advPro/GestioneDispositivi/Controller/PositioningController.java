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

import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Services.DeviceService;
import advPro.GestioneDispositivi.Services.EmployeeService;
import advPro.GestioneDispositivi.Services.LocationService;
import advPro.GestioneDispositivi.Services.PositioningService;
import advPro.GestioneDispositivi.Utils.Utils;


@Controller
@RequestMapping("/positioning")
public class PositioningController {
	private PositioningService positioningService;
	private DeviceService deviceService;
	private LocationService locationService;
	private EmployeeService employeeService;
	
	@GetMapping()
	public String teams(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg) {
		model.addAttribute("title", "Positionings");
		
		List<Positioning> list = positioningService.findAll();
		model.addAttribute("positions", list);
		model.addAttribute("alertMsg", msg);
		
		return "positioning/list";
	}
	
	@RequestMapping(value = "/showNew", method = RequestMethod.GET)
	public String showNew(Locale locale, Model model) {
		model.addAttribute("devices", deviceService.findAll());
		model.addAttribute("employees", employeeService.findAll());
		model.addAttribute("locations", locationService.findAll());
		
		return "positioning/modal_new";
	}
	
	@RequestMapping(value = "/showLink/{id}", method = RequestMethod.GET)
	public String showLink(Locale locale, Model model, @PathVariable("id") int id) {
		Positioning pos = positioningService.findById(id);
		model.addAttribute("positioning", pos);
		model.addAttribute("devices", deviceService.findAll());
		model.addAttribute("employees", employeeService.findAll());
		model.addAttribute("locations", locationService.findAll());

		int x = pos.getDevice() == null ? 0 : pos.getDevice().getId();
		int y = pos.getEmployee() == null ? 0 : pos.getEmployee().getId();
		int z = pos.getLocation() == null ? 0 : pos.getLocation().getId();
		
		model.addAttribute("pos_device", x);
		model.addAttribute("pos_employee", y);
		model.addAttribute("pos_location", z);
		
		return "positioning/modal_link";
	}
	
	@RequestMapping(value = "/saveData", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String saveData(Locale locale, Model uModel,
			@RequestParam("device") String dev) {
		boolean resp = false;
		String msg = "Operation failed: no devices available";
		try {
			if(dev != null && !dev.isEmpty()) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				Positioning pos = new Positioning();
				pos.setDevice(deviceService.findById(Integer.parseInt(dev)));
				pos.setDatePositioning(Utils.date(dateFormat.format(date)));
				this.positioningService.update(pos);
				resp = true;
				msg = "Positioning created successfully";
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
			@RequestParam("employee") String emp, @RequestParam("location") String loc,
			@RequestParam("id") int id) {
		boolean resp = false;
		String msg = "";
		try {
			Positioning pos = positioningService.findById(id);
			if (emp != null && !emp.isEmpty()) {
				pos.setEmployee(employeeService.findById(Integer.parseInt(emp)));					
			} else {
				pos.setEmployee(null);
			}
			if (loc !=null && !loc.isEmpty()) {
				pos.setLocation(locationService.findById(Integer.parseInt(loc)));					
			} else {
				pos.setLocation(null);
			}
			
			this.positioningService.update(pos);
			resp = true;
			msg = "Changes saved successfully";
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
			this.positioningService.delete(id);
			msg = "Positioning deleted successfully";
			resp = true;
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@Autowired
	public void setServices(PositioningService positioningService, DeviceService deviceService, EmployeeService employeeService, LocationService locationService) {
		this.positioningService = positioningService;
		this.deviceService = deviceService;
		this.employeeService = employeeService;
		this.locationService = locationService;
	}

}