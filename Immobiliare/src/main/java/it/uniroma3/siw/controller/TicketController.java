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

import it.uniroma3.siw.model.Ticket;
import it.uniroma3.siw.service.TicketService;
import it.uniroma3.siw.validator.TicketValidator;





@Controller
public class TicketController {

	@Autowired
	private TicketService ts;
	@Autowired
	private TicketValidator tv;

	//quando non mi arriva nulla oppure caso base vado in index pagina iniziale

	private final Logger logger = LoggerFactory.getLogger(this.getClass());



	@PostMapping("/ticket")

	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
	public String addTicket(@Valid @ModelAttribute("ticket")Ticket ticket,BindingResult br,Model model) {
		tv.validate(ticket, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		model.addAttribute("login",AuthenticationController.loggato);
		if(!br.hasErrors())	{
			ts.saveTicket(ticket);
			//model.addAttribute("chef", model);immobile
			model.addAttribute("ticket", this.ts.FindAll());

			if(AuthenticationController.loggato) {
				if(AuthenticationController.admin) {
					model.addAttribute("credentials",AuthenticationController.admin);
				}}
			return "ticket.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}

		return "ticketForm.html";

	}
	
	@GetMapping("/addTicket")
	public String addTicket(Model model) {
		//logger.debug("addCuratore");
		model.addAttribute("ticket", new Ticket());


		//model.addAttribute("login",AuthenticationController.loggato);
		return "ticketForm.html";
	}

	//richiede tute le persone perche non specifico id
	//	@GetMapping("/chef")
	//	public String getChef(Model model) {
	//		List<Chef> chef = cs.FindAll();
	//		model.addAttribute("chef",chef);
	//		return "chefs.html";	
	//	}

	@GetMapping("/ticket")
	public String getTickets(Model model) {
		model.addAttribute("login",AuthenticationController.loggato);
		model.addAttribute("tickets", this.ts.FindAll());
		if(AuthenticationController.loggato) {
			if(AuthenticationController.admin) {	
				model.addAttribute("credentials",AuthenticationController.admin);

			}}
		return "tickets.html";

	}


	@GetMapping("/ticket/{id}")
	public String getTicket(@PathVariable("id") Long id, Model model) {
		model.addAttribute("login",AuthenticationController.loggato);
		model.addAttribute("ticket", this.ts.FindById(id));
		if(AuthenticationController.loggato) {
			if(AuthenticationController.admin) {	
				model.addAttribute("credentials",AuthenticationController.admin);

			}}


		return "ticket.html";

	}
	@GetMapping("/tidcketForm")
	public String TicketForm(Model model) {
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("login",AuthenticationController.loggato);
		return "ticketForm.html";

	}
	
	//da fare anche il get prenotazioni considerando l'immobile 
	//il get immobile 
	// il get agente per poter vedere quale agente andrà a quell'appuntamento

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
