package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.AgenziaImmobiliare;
import it.uniroma3.siw.repository.AgenziaImmobiliareRepository;

@Service
public class AgenziaImmobiliareService {
	
	@Autowired  // autocarichi
	private AgenziaImmobiliareRepository aIr;
	

	@Transactional // ci pensa Springboot ad apreire e chiude la transazione
	public void saveAgenzia (AgenziaImmobiliare agenzia) {
		aIr.save(agenzia);		
		
		
	}
	
	@Transactional
	public AgenziaImmobiliare FindById(Long id) {
	
	  return aIr.findById(id).get();  // senza get non mi ritornava una persona ma un messaggio java optional
	}
	
	@Transactional
	public List<AgenziaImmobiliare> FindAll(){
		/* attenzine il metodo pr.findAll() non ritorna un alista ma un iteratore quindi
		 * devo far un modo di copiare ogni valore in un lista che poi faccio ritornare
		 */

		List<AgenziaImmobiliare> agenzia = new ArrayList<AgenziaImmobiliare>();
		
		for(AgenziaImmobiliare a: aIr.findAll()) {
			agenzia.add(a);
		}
		return agenzia;
	}
	
	
	
	@Transactional
	//Creato per verificare l esistenza di un duplicato
	public boolean alreadyExist(AgenziaImmobiliare agenzia) {
		return aIr.existsByNome(agenzia.getNome());
	}
	

	
	@Transactional
	public void rimuovi(AgenziaImmobiliare agenzia) {
		aIr.delete(agenzia);
	}
	
	
}
