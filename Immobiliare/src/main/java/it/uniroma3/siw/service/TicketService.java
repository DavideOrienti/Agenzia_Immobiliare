package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Immobile;
import it.uniroma3.siw.model.Ticket;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.TicketRepository;

@Service
public class TicketService {
	
	@Autowired  // autocarichi
	private TicketRepository tr;


	
	@Transactional // ci pensa Springboot ad apreire e chiude la transazione
	
	public void saveTicket (Ticket ticket) {
	ticket.setDataPrenotazione(ticket.getDataPrenotazione().replace('T',' '));	
		tr.save(ticket);		
		
		
	}
	
	@Transactional
	public List<Ticket> FindByUtente(Utente u) {
		return tr.findByUtente(u);
	}
	
	@Transactional
	public List<Ticket> FindByImmobile(Immobile i) {
		return tr.findByImmobile(i);
	}
	
	
	@Transactional
	public List<Ticket> FindByUtenteAndImmobile(Utente u,Immobile i) {
		return tr.findByUtenteAndImmobile(u,i);
	}
	
	@Transactional
	public Ticket FindById(Long id) {
	
	  return tr.findById(id).get();  // senza get non mi ritornava una persona ma un messaggio java optional
	}
	
	@Transactional
	public List<Ticket> FindAll(){
		/* attenzine il metodo pr.findAll() non ritorna un alista ma un iteratore quindi
		 * devo far un modo di copiare ogni valore in un lista che poi faccio ritornare
		 */

		List<Ticket> ticket = new ArrayList<Ticket>();
		
		for(Ticket t: tr.findAll()) {
			ticket.add(t);
		}
		return ticket;
	}
	
	
	
	@Transactional
    public boolean alreadyExist(Ticket biglietto) {
		return tr.existsByUtenteAndImmobile(biglietto.getUtente(),biglietto.getImmobile());
	}

	
	public boolean dataOccupata(Ticket ticket) {
		List<Ticket> t = this.tr.findByImmobileAndDataPrenotazioneAndUtente(ticket.getImmobile(),ticket.getDataPrenotazione(),ticket.getUtente() );
		boolean disponibile=true;
		for(int i=0;i<t.size();i++) {
			
			if(t.get(i).getDataPrenotazione().equals(ticket.getDataPrenotazione())) {
				disponibile=false;
			}
			
		}
		return disponibile;
	}
	
	@Transactional
	public void rimuovi(Ticket ticket) {
		tr.delete(ticket);
	}
	

	
	
	
	
}
