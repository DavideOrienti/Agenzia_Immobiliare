package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Immobile;
import it.uniroma3.siw.repository.ImmobileRepository;
@Service
public class ImmobileService {
	
	@Autowired  // autocarichi
	private ImmobileRepository ir;
	
	@Autowired
    CredentialsService credentialsService;
	
//	@Autowired
//	private ChefService cs;
	
//	@Autowired
//	private PiattoService ps;
//	
	@Transactional // ci pensa Springboot ad apreire e chiude la transazione
	public void saveImmobile (Immobile immobile) {
		ir.save(immobile);		
	}
	
	@Transactional
	public Immobile FindById(Long id) {
	
	  return ir.findById(id).get();  // senza get non mi ritornava una persona ma un messaggio java optional
	}
	
	@Transactional
	public List<Immobile> FindAll(){
		/* attenzine il metodo pr.findAll() non ritorna un alista ma un iteratore quindi
		 * devo far un modo di copiare ogni valore in un lista che poi faccio ritornare
		 */

		List<Immobile> immobile = new ArrayList<Immobile>();
		
		for(Immobile i: ir.findAll()) {
			immobile.add(i);
		}
		return immobile;
	}
	
	
	
	@Transactional
	//Creato per verificare l esistenza di un duplicato
	public boolean alreadyExist(Immobile immobile) {
		return ir.existsByIndirizzoAndCivicoAndInterno(immobile.getIndirizzo(),immobile.getCivico(),immobile.getInterno());
	}
	
//	public ChefService getChefService() {
//		return cs;
//	}
//	
	
	@Transactional
	public void rimuovi(Immobile immobile) {
		ir.delete(immobile);
	}
	
	public CredentialsService getCredentialsService() {
		return credentialsService;
    }
	
	
}
