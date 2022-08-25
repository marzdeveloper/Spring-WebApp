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

import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Services.LocationService;


@Controller
@RequestMapping("/location")
public class LocationController {
	private LocationService locationService;
	
	@GetMapping()
	public String locations(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg) {
		model.addAttribute("title", "Locations");
		
		List<Location> list = locationService.findAll();
		model.addAttribute("locations", list);
		model.addAttribute("alertMsg", msg);
		
		return "location/list";
	}
	
	@RequestMapping(value = "/showNew", method = RequestMethod.GET)
	public String showNew(Locale locale, Model model) {
		model.addAttribute("location", new Location());
		return "location/modal_new";
	}
	
	@RequestMapping(value = "/showEdit/{id}", method = RequestMethod.GET)
	public String showEdit(Locale locale, Model model, @PathVariable("id") int id) {
		Location location = locationService.findById(id);
		model.addAttribute("location", location);
		
		return "location/modal_edit";
	}
	
	@RequestMapping(value = "/saveData", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String saveData(Locale locale, Model uModel,
			@RequestParam("name") String name, @RequestParam("description") String des, @RequestParam(value = "id", required = false) String id) {
		boolean resp = false;
		String msg = "Operation failed: one or more empty fields";
		try {
			if(name != null && !name.trim().isEmpty() && des != null && !des.trim().isEmpty()) {
				Location location = null;
				if (id != null && !id.trim().isEmpty()) {
					location = locationService.findById(Integer.parseInt(id));
					location.setName(name);
					location.setDescription(des);
					msg = "Changes saved successfully";
				} else {
					location = locationService.create(name, des);
					msg = "Location created successfully";
				}
				
				this.locationService.update(location);
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
			this.locationService.delete(id);
			msg = "Location deleted successfully";
			resp = true;
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@Autowired
	public void setServices(LocationService locationService) {
		this.locationService = locationService;
	}

}