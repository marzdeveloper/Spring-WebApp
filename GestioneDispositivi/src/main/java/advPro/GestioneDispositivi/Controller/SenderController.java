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

import advPro.GestioneDispositivi.Model.Entities.Sender;
import advPro.GestioneDispositivi.Services.DeviceService;
import advPro.GestioneDispositivi.Services.SenderService;


@Controller
@RequestMapping("/sender")
public class SenderController {
	private SenderService senderService;
	private DeviceService deviceService;
	
	@GetMapping()
	public String teams(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg) {
		model.addAttribute("title", "Senders");
		
		List<Sender> list = senderService.findAll();
		model.addAttribute("senders", list);
		model.addAttribute("alertMsg", msg);
		
		return "sender/list";
	}
	
	@GetMapping(value = "/getDetails/{id}")
	public String getDetails(Locale locale, Model model, @PathVariable("id") int id) {
		Sender sender = senderService.findById(id);
		model.addAttribute("sender", sender);
		model.addAttribute("devices", deviceService.findAllBySenderId(id));
		
		return "sender/modal";
	}
	
	@RequestMapping(value = "/showNew", method = RequestMethod.GET)
	public String showNew(Locale locale, Model model) {
		model.addAttribute("sender", new Sender());
		model.addAttribute("devices", deviceService.findAll());
		
		return "sender/modal_new";
	}
	
	@RequestMapping(value = "/showEdit/{id}", method = RequestMethod.GET)
	public String showEdit(Locale locale, Model model, @PathVariable("id") int id) {
		Sender sender = senderService.findById(id);
		model.addAttribute("sender", sender);
		
		return "sender/modal_edit";
	}
	
	@RequestMapping(value = "/saveData", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String saveData(Locale locale, Model uModel,
			@RequestParam("name") String name, @RequestParam(value = "id", required = false) String id) {
		boolean resp = false;
		String msg = "Operation failed: name can't be empty";
		try {
			if(name != null && !name.trim().isEmpty()) {
				Sender sender = null;
				if (id != null && !id.trim().isEmpty()) {
					sender = senderService.findById(Integer.parseInt(id));
					sender.setName(name);
					msg = "Changes saved successfully";
				} else {
					sender = senderService.create(name);
					msg = "Sender created successfully";
				}
				this.senderService.update(sender);
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
			senderService.delete(id);
			msg = "Sender deleted successfully";
			resp = true;
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@Autowired
	public void setServices(SenderService senderService, DeviceService deviceService) {
		this.senderService = senderService;
		this.deviceService = deviceService;
	}
}