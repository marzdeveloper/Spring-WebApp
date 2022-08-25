package advPro.GestioneDispositivi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController 
{
	@Autowired
	String appName = "Gestione Dispositivi";
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@RequestParam(value = "error", required = false) String error, 
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessage = null;
        if(error != null) {
        	errorMessage = "Wrong username or password";
        }
        if(logout != null) {
        	//entriamo in questo caso se non specifichiamo una .logoutSuccessUrl in WebSecurityConf.configure
        	errorMessage = "System exit successful";
        }
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("appName", appName);
        model.addAttribute("title", "Login");
        
        model.addAttribute("app_css", "login.css");
        return "login";
    }
    
}

