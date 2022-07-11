package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import it.uniroma3.siw.service.ImmobileService;
import it.uniroma3.siw.service.TicketService;
import it.uniroma3.siw.validator.TicketValidator;





@Controller
public class TicketController {

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
	
	
//	
//	@GetMapping("/Conferma")
//    public String paginaConferma(@PathVariable("id") Long id,Model model, Immobile immobile) {
////        logger.debug("addBiglietto");
//		this.immobileCorrente=this.ts.getImmobileService().immobilePerId(id);
//
//        model.addAttribute("immobile",immobile.getId());
//        model.addAttribute("agente",immobile.getAgente()); 
//        model.addAttribute("ticket", new Ticket());
//
//        return "paginaConferma.html";
//    }
//

// @RequestMapping(value="/conferma", method = RequestMethod.POST)
// public String newBiglietto( Model model,@ModelAttribute("biglietto") Ticket biglietto, BindingResult bindingResult,
//		 HttpSession httpSession){
//	 
//     this.tv.validate(biglietto, bindingResult);
//     this.immobileCorrente=is.getImmobileCorrente();
//     
////
////     if (!bindingResult.hasErrors()) {
//    	 biglietto.setImmobile(immobileCorrente);
//    	 //biglietto.setDataPrenotazione(null)
//
//    	Utente utente=(Utente)httpSession.getAttribute("utente");
//            biglietto.setUtente(utente); 
//            ts.saveTicket(biglietto);
//            return "tickets.html";
//             }
////     	
////       model.addAttribute("ticket", new Ticket());
////    // model.addAttribute("ticket", biglietto);
////		model.addAttribute("immobile",immobileCorrente);
////		model.addAttribute("agente",this.immobileCorrente.getAgente());
////
////        return "prenotaForm.html";
////    }

	@RequestMapping(value="/conferma", method = RequestMethod.POST)
	public String newBiglietto( Model model,@ModelAttribute("biglietto") Ticket biglietto, BindingResult bindingResult,
			HttpSession httpSession) {
		this.tv.validate(biglietto, bindingResult);
		this.immobileCorrente=is.getImmobileCorrente();


		if (!bindingResult.hasErrors()) {
		biglietto.setImmobile(immobileCorrente);


		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = is.getCredentialsService().getCredentials(userDetails.getUsername());
		model.addAttribute("credentials", credentials);
		model.addAttribute("utente", credentials.getUser());


		Utente utente = credentials.getUser();


		model.addAttribute("ticket", biglietto);
		model.addAttribute("immobile",immobileCorrente);
		biglietto.setUtente(utente);
		
		biglietto.setProgressivo((ts.FindByUtente(utente).size()));


		biglietto.setDataPrenotazione((biglietto.getDataPrenotazione()));

		ts.saveTicket(biglietto);
		return "conferma.html";
	}

		       model.addAttribute("ticket", new Ticket());
		    // model.addAttribute("ticket", biglietto);
		        model.addAttribute("immobile",immobileCorrente);
		        model.addAttribute("agente",this.immobileCorrente.getAgente());
	
		        return "prenotaForm.html";
		    }
	
	@PostMapping("/remove/{id}")
    public String removePiatto(Model model, @PathVariable("id")Long idTicket) {

            Ticket t= ts.FindById(idTicket);

//            List<Ingredienti> ingredientiP = new ArrayList<>( p.getIngredienti());
//
//            for(Ingredienti i: ingredientiP) {
//
//                p.removeIngrediente(i);
//                i.removePiatto(p);
//            }
            Utente u = t.getUtente();
            //ho l'utente devo vedere se così facendo sul DB resta in memoria il ticket
            
            //devo capire se così facendo tolgo anche dall'utente il ticket
            ts.rimuovi(t);
            model.addAttribute("utente", u);


            
            return "utente.html";
    }
	


//	@PostMapping("/ticket")
//
//	//bilding result gestische i casi di errore
//	//model Attriubute associa cio che c edentro al modello con l oggetto persona
//	public String addTicket(@Valid @ModelAttribute("tickets")Ticket ticket,BindingResult br,Model model) {
//		
//		//qui dovrei fare il login prima di fare un ticket
////		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////        Credentials credentials = this.as.getCredentialsService().getCredentials(userDetails.getUsername());
////        if(credentials.getRole(.equals()))
//        
//		
//		tv.validate(ticket, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
//		                              errori contro anche che non ci siano duplicati*/
//		if(!br.hasErrors())	{
//			ts.saveTicket(ticket);
//			//model.addAttribute("chef", model);
//			model.addAttribute("tickets", this.ts.FindAll());
//
//
//			return "tickets.html";  // se il problema non ha trovato errori torna alla pagina iniziale
//		}
//		model.addAttribute("ticket", new Ticket());
//
//		return "ticketForm.html";
//	}
//	
//	@GetMapping("/addTicket")
//	public String addTicket(Model model, Immobile immobile) {
//		//logger.debug("addCuratore");
//		model.addAttribute("ticket", new Ticket());
//		model.addAttribute("immobile",immobile);
//		model.addAttribute("agenti",as.FindAll());
//
//
//		//model.addAttribute("login",AuthenticationController.loggato);
//		return "ticketForm.html";
//	}
//	
//	@GetMapping("/tidcketForm")
//	public String TicketForm(Model model) {
//		model.addAttribute("ticket", new Ticket());
//		return "ticketForm.html";
//
//	}
	
//
//	@GetMapping("/ticket")
//	public String getTickets(Model model) {
//		
//		return "tickets.html";
//
//	}


	@GetMapping("/ticket/{id}")
	public String getTicket(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ticket", this.ts.FindById(id));
		return "ticket.html";

	}


}
