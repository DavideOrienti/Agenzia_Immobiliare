package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Immobile;

public interface ImmobileRepository extends CrudRepository<Immobile,Long> {
	
	public boolean existsByNome(String nome);
	//public List<Piatto> findByPiatti(Buffet buffet);
	


}
