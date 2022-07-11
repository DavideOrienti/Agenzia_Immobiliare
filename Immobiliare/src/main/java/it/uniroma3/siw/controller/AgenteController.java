package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Agente;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.AgenteService;
import it.uniroma3.siw.validator.AgenteValidator;




@Controller
public class AgenteController {

	@Autowired
	private AgenteService as;
	@Autowired
	private AgenteValidator av;

	//quando non mi arriva nulla oppure caso base vado in index pagina iniziale

	private final Logger logger = LoggerFactory.getLogger(this.getClass());



	@PostMapping("/admin/agente")

	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
	public String addAgente(@Valid @ModelAttribute("agenti")Agente agente,BindingResult br,Model model) {
		av.validate(agente, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		if(!br.hasErrors())	{
			as.saveAgente(agente);
			//model.addAttribute("chef", model);
			model.addAttribute("agenti", this.as.FindAll());
			model.addAttribute("agente",agente);

			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = as.getCredentialsService().getCredentials(userDetails.getUsername());
			model.addAttribute("credentials", credentials);
			

			return "agenti.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}
		model.addAttribute("agente", new Agente());

		return "agenteForm.html";
	}

	@GetMapping("/admin/agenteForm")
	public String agenteForm(Model model) {
		model.addAttribute("agente", new Agente());
		return "agenteForm.html";
	}


	@GetMapping("/agente")
	public String getAgenti(Model model) {
		model.addAttribute("agenti", this.as.FindAll());
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Credentials credentials = as.getCredentialsService().getCredentials(userDetails.getUsername());
		model.addAttribute("credentials", credentials);
		return "agenti.html";

	}


	@GetMapping("/agente/{id}")
	public String getAgente(@PathVariable("id") Long id, Model model) {
		model.addAttribute("agente", this.as.FindById(id));
		//per il tasto modifica per dare la visibilità 
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.as.getCredentialsService().getCredentials(userDetails.getUsername());
        model.addAttribute("credentials", credentials);
		return "agente.html";

	}
	
	


//
//	@GetMapping("/modifica/{id}")
//	public String modificaChef(Model model,@PathVariable("id") Long id) {
//		Chef c= cs.FindById(id);
//		model.addAttribute("chef", c);
//		if(AuthenticationController.loggato) {
//			if(AuthenticationController.admin) {	
//				model.addAttribute("credentials",AuthenticationController.admin);
//
//			}}
//
//
//		return "ModificaChef.html";
//	}
//
//	@PostMapping("/chef/{id}")
//	public String modificaChef(@ModelAttribute("chef") Chef chef, Model model,BindingResult bindingResult,
//			@PathVariable("id") Long Id) {
//
//		//Chef c = cs.FindById(Id);
//		chef.setId(Id);
//		cs.salvaChef(chef);
//		//cs.cancellaChef(c);
//
//		//chef.setId(Id);
//		chef=cs.FindById(Id);
//		model.addAttribute("chef", chef);
//		if(AuthenticationController.loggato) {
//			if(AuthenticationController.admin) {	
//				model.addAttribute("credentials",AuthenticationController.admin);
//
//			}}
//
//		return "chef.html";
//	}

}
