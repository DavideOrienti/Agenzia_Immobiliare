package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket,Long> {
	
	public boolean existsByNome(String nome);
	//public List<Piatto> findByPiatti(Buffet buffet);
	


}
