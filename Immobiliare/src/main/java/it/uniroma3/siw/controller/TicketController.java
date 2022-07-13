package it.uniroma3.siw.controller;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Immobile;
import it.uniroma3.siw.model.Ticket;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.AgenteService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ImmobileService;
import it.uniroma3.siw.service.TicketService;
import it.uniroma3.siw.validator.TicketValidator;





@Controller
public class TicketController {
	
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private TicketService ts;
	@Autowired
	private TicketValidator tv;
	
	@Autowired
	private ImmobileService is;
	
	@Autowired
	private AgenteService as;
	
	

	//quando non mi arriva nulla oppure caso base vado in index pagina iniziale

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Immobile immobileCorrente;
	
	

	@RequestMapping(value="/conferma", method = RequestMethod.POST)
	public String newBiglietto(@Valid @ModelAttribute("biglietto") Ticket biglietto, Model model, BindingResult bindingResult,
			HttpSession httpSession) {
		this.immobileCorrente=is.getImmobileCorrente();
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = is.getCredentialsService().getCredentials(userDetails.getUsername());
		model.addAttribute("credentials", credentials);
		model.addAttribute("utente", credentials.getUser());
		model.addAttribute("immobile",immobileCorrente);

		

		Utente utente = credentials.getUser();
		
		biglietto.setImmobile(immobileCorrente);
		biglietto.setUtente(utente);

		
		this.tv.validate(biglietto, bindingResult);



		if (!bindingResult.hasErrors()) {
//		biglietto.setImmobile(immobileCorrente);


//		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Credentials credentials = is.getCredentialsService().getCredentials(userDetails.getUsername());
//		model.addAttribute("credentials", credentials);
//		model.addAttribute("utente", credentials.getUser());
//
//
//		Utente utente = credentials.getUser();


//		model.addAttribute("biglietto", biglietto);
//		model.addAttribute("immobile",immobileCorrente);
//		biglietto.setUtente(utente);
		
//		biglietto.setProgressivo((ts.FindByUtenteAndImmobile(utente,immobileCorrente).size()));
			biglietto.setProgressivo((ts.FindByImmobile(immobileCorrente).size()));

		biglietto.setDataPrenotazione((biglietto.getDataPrenotazione()));

		ts.saveTicket(biglietto);
		return "conferma.html";
	}

//		       model.addAttribute("ticket", new Ticket());
//		     model.addAttribute("ticket", biglietto);
		        model.addAttribute("immobile",immobileCorrente);
		        model.addAttribute("agente",this.immobileCorrente.getAgente());
	
		        return "prenotaForm.html";
		    }
	
	@PostMapping("/remove/{id}")
    public String removePiatto(Model model, @PathVariable("id")Long idTicket) {

            Ticket t= ts.FindById(idTicket);


            Utente u = t.getUtente();
            //ho l'utente devo vedere se così facendo sul DB resta in memoria il ticket
            Immobile immobile=ts.FindById(idTicket).getImmobile();
            immobile.setStato(false);
            //devo capire se così facendo tolgo anche dall'utente il ticket
            ts.rimuovi(t);
            
            
            model.addAttribute("utente", u);
            model.addAttribute("tickets",ts.FindByUtente(u));


            
            
            return "utente.html";
    }

	@GetMapping("/ticket/{id}")
	public String getTicket(@PathVariable("id") Long id, Model model) {
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		Utente u = credentials.getUser();
		
		Ticket ticketCorrente= ts.FindById(id);
		if(ticketCorrente.getUtente().equals(u)) {
		model.addAttribute("ticket", this.ts.FindById(id));
		return "ticket.html";
		}
		return "loginForm";

	}


}
