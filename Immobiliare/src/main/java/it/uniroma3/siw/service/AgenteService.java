package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Agente;
import it.uniroma3.siw.repository.AgenteRepository;

@Service
public class AgenteService {
	
	@Autowired  // autocarichi
	private AgenteRepository ar; 
	
	@Autowired
    private CredentialsService credentialsService;
	
//	@Autowired
//	private ChefService cs;
	
//	@Autowired
//	private PiattoService ps;
//	
	@Transactional // ci pensa Springboot ad apreire e chiude la transazione
	public void saveAgente (Agente agente) {
		ar.save(agente);		
		
		
	}
	
	@Transactional
	public Agente FindById(Long id) {
	
	  return ar.findById(id).get();  // senza get non mi ritornava una persona ma un messaggio java optional
	}
	
	@Transactional
	public List<Agente> FindAll(){
		/* attenzine il metodo pr.findAll() non ritorna un alista ma un iteratore quindi
		 * devo far un modo di copiare ogni valore in un lista che poi faccio ritornare
		 */

		List<Agente> agente = new ArrayList<Agente>();
		
		for(Agente a : ar.findAll()) {
			agente.add(a);
		}
		return agente;
	}
	
	
	
	@Transactional
	//Creato per verificare l esistenza di un duplicato
	public boolean alreadyExist(Agente agente) {
		return ar.existsByNome(agente.getNome());
	}
	
//	public ChefService getChefService() {
//		return cs;
//	}
//	
	
	@Transactional
	public void rimuovi(Agente agente) {
		ar.delete(agente);
	}
	
	public CredentialsService getCredentialsService() {
		return credentialsService;
    }
	
	
}
