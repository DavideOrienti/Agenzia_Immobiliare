package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.AgenziaImmobiliare;

public interface AgenziaImmobiliareRepository extends CrudRepository<AgenziaImmobiliare,Long> {
	
	public boolean existsByNome(String nome);
	


}
