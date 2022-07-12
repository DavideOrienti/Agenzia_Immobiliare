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

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Immobile;
import it.uniroma3.siw.model.Ticket;
import it.uniroma3.siw.service.AgenteService;
import it.uniroma3.siw.service.ImmobileService;
import it.uniroma3.siw.service.TicketService;
import it.uniroma3.siw.validator.ImmobileValidator;



@Controller
public class ImmobileController {
	
	@Autowired
	private ImmobileService is;
	@Autowired
	private ImmobileValidator iv;
	
	@Autowired
	private  AgenteService as;
//	
	@Autowired
	private TicketService ts;
//	@Autowired
//	private TicketValidator tv;
	
	//quando non mi arriva nulla oppure caso base vado in index pagina iniziale
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	Immobile immobileCorrente;
	

	


	

	@PostMapping("/admin/immobile")

	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
	public String addImmobile(@Valid @ModelAttribute("immobile")Immobile immobile,BindingResult br,Model model) {
		iv.validate(immobile, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		if(!br.hasErrors())	{
			is.saveImmobile(immobile);
			//model.addAttribute("chef", model);
			model.addAttribute("immobili", this.is.FindAll());


			return "index";  // se il problema non ha trovato errori torna alla pagina iniziale
		}
		else {
			//model.addAttribute("immobile", immobile);
		    model.addAttribute("agenti",as.FindAll());

		return "immobileForm.html";}
	}
	

	@GetMapping("/admin/immobileForm")
	public String immobileForm(Model model) {
		model.addAttribute("immobile", new Immobile());
		model.addAttribute("agenti",as.FindAll());
		return "immobileForm.html";
	}


	@GetMapping("/immobile")
	public String getImmobile(Model model) {
		model.addAttribute("immobili", this.is.FindAll());
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		Credentials credentials = is.getCredentialsService().getCredentials(userDetails.getUsername());
		model.addAttribute("credentials", credentials);
		
		
		return "immobili.html";

	}


	@GetMapping("/immobile/{id}")
	public String getImmobile(@PathVariable("id") Long id, Model model) {
		this.immobileCorrente = this.is.FindById(id);
		int postiRimasti = this.immobileCorrente.getNumeroPostiDisponibili()-is.getTicketervice().FindByImmobile(immobileCorrente).size();
		if(postiRimasti==0) {immobileCorrente.setStato(true);}
		model.addAttribute("stato",this.immobileCorrente.isStato());
	
		model.addAttribute("postiRimasti",postiRimasti);
		model.addAttribute("immobile",immobileCorrente);
		//per il tasto modifica per dare la visibilità 
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = is.getCredentialsService().getCredentials(userDetails.getUsername());
        model.addAttribute("credentials", credentials);
		return "immobile.html";

	}
	
	
	
	@GetMapping("/prenota")
	public String prenota(Model model) {
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("immobile",this.immobileCorrente);
		model.addAttribute("agente",this.immobileCorrente.getAgente());
		is.setImmobileCorrente(this.immobileCorrente);
		return "prenotaForm.html";
	}
	
	
	
	
	
	@GetMapping("/modificaImmobile/{id}")
    public String modificaImmobile(Model model,@PathVariable("id") Long id) {
		Immobile immobile = is.FindById(id);
		
        model.addAttribute("immobile", immobile);
		model.addAttribute("agenti",as.FindAll());
        
//        if(AuthenticationController.loggato) {
//     		if(AuthenticationController.admin) {	
//     			model.addAttribute("credentials",AuthenticationController.admin);
//     			
//     		}}

           
        return "modificaImmobile.html";
        }

	@PostMapping("/immobile/{id}")
    public String modificaImmobile(@ModelAttribute("immobile") Immobile immobile, Model model,BindingResult bindingResult,
            @PathVariable("id") Long Id) {
	
     immobile.setId(Id);
     is.saveImmobile(immobile);
 	model.addAttribute("agenti",as.FindAll());

     immobile=is.FindById(Id);
     model.addAttribute("immobile", immobile);
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.as.getCredentialsService().getCredentials(userDetails.getUsername());
        model.addAttribute("credentials", credentials);
        
        int postiRimasti = this.immobileCorrente.getNumeroPostiDisponibili()-is.getTicketervice().FindByImmobile(immobileCorrente).size();
		if(postiRimasti==0) {immobileCorrente.setStato(true);}
		model.addAttribute("stato",this.immobileCorrente.isStato());
		
        return "immobile.html";
         }

}
