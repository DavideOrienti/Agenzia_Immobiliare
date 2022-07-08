package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Immobile;
import it.uniroma3.siw.model.Ticket;
import it.uniroma3.siw.model.Utente;

public interface TicketRepository extends CrudRepository<Ticket,Long> {
	
	public boolean existsByNome(String nome);
	//public List<Piatto> findByPiatti(Buffet buffet);
	
	//prima immobile era di tipo object , vedere se da qualche problema
	public List<Ticket> findByUtenteAndImmobile(Utente utente, Immobile immobile);



}
