package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Agente;
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



	@PostMapping("/agente")

	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
	public String addAgente(@Valid @ModelAttribute("agente")Agente agente,BindingResult br,Model model) {
		av.validate(agente, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		model.addAttribute("login",AuthenticationController.loggato);
		if(!br.hasErrors())	{
			as.saveAgente(agente);
			//model.addAttribute("chef", model);
			model.addAttribute("agente", this.as.FindAll());

			if(AuthenticationController.loggato) {
				if(AuthenticationController.admin) {
					model.addAttribute("credentials",AuthenticationController.admin);
				}}
			return "agente.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}

		return "agenteForm.html";

	}

	@GetMapping("/addAgente")
	public String addAgente(Model model) {
		//logger.debug("addCuratore");
		model.addAttribute("agente", new Agente());


		//model.addAttribute("login",AuthenticationController.loggato);
		return "agenteForm.html";
	}

	//richiede tute le persone perche non specifico id
	//	@GetMapping("/chef")
	//	public String getChef(Model model) {
	//		List<Chef> chef = cs.FindAll();
	//		model.addAttribute("chef",chef);
	//		return "chefs.html";	
	//	}
	@GetMapping("/agente")
	public String getAgenti(Model model) {
		model.addAttribute("login",AuthenticationController.loggato);
		model.addAttribute("agenti", this.as.FindAll());
		if(AuthenticationController.loggato) {
			if(AuthenticationController.admin) {	
				model.addAttribute("credentials",AuthenticationController.admin);

			}}
		return "agenti.html";

	}


	@GetMapping("/agente/{id}")
	public String getAgente(@PathVariable("id") Long id, Model model) {
		model.addAttribute("login",AuthenticationController.loggato);
		model.addAttribute("agente", this.as.FindById(id));
		if(AuthenticationController.loggato) {
			if(AuthenticationController.admin) {	
				model.addAttribute("credentials",AuthenticationController.admin);

			}}


		return "agente.html";

	}
	
	
	@GetMapping("/agenteForm")
	public String agenteForm(Model model) {
		model.addAttribute("agente", new Agente());
		model.addAttribute("login",AuthenticationController.loggato);
		return "agenteForm.html";

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
