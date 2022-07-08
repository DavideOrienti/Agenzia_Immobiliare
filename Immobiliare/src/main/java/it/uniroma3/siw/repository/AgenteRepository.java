package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Agente;

public interface AgenteRepository extends CrudRepository<Agente,Long> {
	
	public boolean existsByNome(String nome);
	
	


}
