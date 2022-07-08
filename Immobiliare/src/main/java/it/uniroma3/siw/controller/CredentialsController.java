package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Credentials;

@Controller
public class CredentialsController {
	
	   @GetMapping("/switch-case")
	    public String switchExample(Model model) {
		   
	        Credentials user = new Credentials("ADMIN");
	        model.addAttribute("credentials", user);
	        return "switch-case";
	    }

}
