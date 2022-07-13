package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Immobile;
import it.uniroma3.siw.model.Ticket;
import it.uniroma3.siw.model.Utente;

public interface TicketRepository extends CrudRepository<Ticket,Long> {
	

	
	//prima immobile era di tipo object , vedere se da qualche problema
	public List<Ticket> findByUtenteAndImmobile(Utente utente, Immobile immobile);
	public List<Ticket> findByUtente(Utente utente);
	public List<Ticket> findByImmobile(Immobile immobile);
	public List<Ticket> findByImmobileAndDataPrenotazioneAndUtente(Immobile immobile,String data,Utente utente);
	public boolean existsByUtenteAndImmobile(Utente utente,Immobile immobile);



}
