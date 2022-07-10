package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.UtenteService;

public class UtenteController {
	
	@Autowired
	private UtenteService us;
	
	
//	@PostMapping("/admin/agente")
//
//	//bilding result gestische i casi di errore
//	//model Attriubute associa cio che c edentro al modello con l oggetto persona
//	public String addAgente(@Valid @ModelAttribute("agenti")Agente agente,BindingResult br,Model model) {
//		av.validate(agente, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
//		                              errori contro anche che non ci siano duplicati*/
//		if(!br.hasErrors())	{
//			as.saveAgente(agente);
//			//model.addAttribute("chef", model);
//			model.addAttribute("agenti", this.as.FindAll());
//
//
//			return "agenti.html";  // se il problema non ha trovato errori torna alla pagina iniziale
//		}
//		model.addAttribute("agente", new Agente());
//
//		return "agenteForm.html";
//	}
//
//	@GetMapping("/admin/agenteForm")
//	public String agenteForm(Model model) {
//		model.addAttribute("agente", new Agente());
//		return "agenteForm.html";
//	}

//
//	@GetMapping("/agente")
//	public String getAgenti(Model model) {
//		model.addAttribute("agenti", this.as.FindAll());
//		
//		return "agenti.html";
//
//	}


	@GetMapping("/utente/{id}")
	public String getUtente(@PathVariable("id") Long id, Model model) {
		model.addAttribute("utente", this.us.FindById(id));
		//per il tasto modifica per dare la visibilità 
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.us.getCredentialsService().getCredentials(userDetails.getUsername());
        model.addAttribute("credentials", credentials);
		return "utente.html";

	}
	

}
