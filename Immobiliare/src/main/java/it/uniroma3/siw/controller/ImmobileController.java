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
import it.uniroma3.siw.model.Immobile;
import it.uniroma3.siw.model.Ticket;
import it.uniroma3.siw.service.ImmobileService;
import it.uniroma3.siw.validator.ImmobileValidator;



@Controller
public class ImmobileController {
	
	@Autowired
	private ImmobileService is;
	@Autowired
	private ImmobileValidator iv;
	
	//quando non mi arriva nulla oppure caso base vado in index pagina iniziale
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	Immobile immobileCorrente;
	


	

	@PostMapping("/admin/immobile")

	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
	public String addImmobile(@Valid @ModelAttribute("immobili")Immobile immobile,BindingResult br,Model model) {
		iv.validate(immobile, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		if(!br.hasErrors())	{
			is.saveImmobile(immobile);
			//model.addAttribute("chef", model);
			model.addAttribute("agenti", this.is.FindAll());


			return "agenti.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}
		model.addAttribute("agente", new Agente());

		return "agenteForm.html";
	}
	

	@GetMapping("/admin/immobileForm")
	public String immobileForm(Model model) {
		model.addAttribute("immobile", new Immobile());
		return "immobileForm.html";
	}


	@GetMapping("/immobile")
	public String getImmobile(Model model) {
		model.addAttribute("immobili", this.is.FindAll());
		
		return "immobile.html";

	}


	@GetMapping("/immobile/{id}")
	public String getImmobile(@PathVariable("id") Long id, Model model) {
		this.immobileCorrente = this.is.FindById(id);
		model.addAttribute("immobile",immobileCorrente);
		//per il tasto modifica per dare la visibilità 
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.is.getCredentialsService().getCredentials(userDetails.getUsername());
        model.addAttribute("credentials", credentials);
		return "immobile.html";

	}
	
	@GetMapping("/prenotaForm")
	public String prenota(Model model) {
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("immobile",this.immobileCorrente);
		model.addAttribute("agente",this.immobileCorrente.getAgente());
		
		return "conferma.html";
	}
	
	
//	
//	@GetMapping("/modifica/{id}")
//    public String modificaChef(Model model,@PathVariable("id") Long id) {
//        Chef c= cs.FindById(id);
//        model.addAttribute("chef", c);
//        if(AuthenticationController.loggato) {
//     		if(AuthenticationController.admin) {	
//     			model.addAttribute("credentials",AuthenticationController.admin);
//     			
//     		}}
//
//           
//        return "ModificaChef.html";
//        }
//
//	@PostMapping("/chef/{id}")
//    public String modificaChef(@ModelAttribute("chef") Chef chef, Model model,BindingResult bindingResult,
//            @PathVariable("id") Long Id) {
//	
//	//Chef c = cs.FindById(Id);
//     chef.setId(Id);
//     cs.salvaChef(chef);
//     //cs.cancellaChef(c);
//   
//     //chef.setId(Id);
//     chef=cs.FindById(Id);
//     model.addAttribute("chef", chef);
//     if(AuthenticationController.loggato) {
// 		if(AuthenticationController.admin) {	
// 			model.addAttribute("credentials",AuthenticationController.admin);
// 			
// 		}}
//
//        return "chef.html";
//         }

}
