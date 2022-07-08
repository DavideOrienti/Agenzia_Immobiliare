package it.uniroma3.siw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.AgenziaImmobiliareService;
import it.uniroma3.siw.validator.AgenziaImmobiliareValidator;

public class AgenziaImmobiliareController {
	
	@Autowired
	private AgenziaImmobiliareService aIs;
	@Autowired
	private AgenziaImmobiliareValidator av;
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	//quando non mi arriva nulla oppure caso base vado in index pagina iniziale
	@GetMapping("/")
    public String defaultMapping(Model model)
    {
		if(AuthenticationController.loggato) {
			if(AuthenticationController.admin) {
				model.addAttribute("credentials",AuthenticationController.admin);
			}}
        return "index.html";
    }
	
	
}
