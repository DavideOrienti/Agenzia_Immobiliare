package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class UtenteService {
	
	@Autowired  // autocarichi
	private UtenteRepository ur;
	
	@Autowired
	private CredentialsService cs;

	@Transactional // ci pensa Springboot ad apreire e chiude la transazione
	public void saveUtente (Utente utente) {
		ur.save(utente);		
		
		
	}
	
	@Transactional
	public Utente FindById(Long id) {
	
	  return ur.findById(id).get();  // senza get non mi ritornava una persona ma un messaggio java optional
	}
	
	@Transactional
	public List<Utente> FindAll(){
		/* attenzine il metodo pr.findAll() non ritorna un alista ma un iteratore quindi
		 * devo far un modo di copiare ogni valore in un lista che poi faccio ritornare
		 */

		List<Utente> utente = new ArrayList<Utente>();
		
		for(Utente u: ur.findAll()) {
			utente.add(u);
		}
		return utente;
	}
	
	
	
	@Transactional
	//Creato per verificare l esistenza di un duplicato
	public boolean alreadyExist(Utente utente) {
		return ur.existsByNomeAndCognome(utente.getNome(),utente.getCognome());
	}
	

	@Transactional
	public void rimuovi(Utente utente) {
		ur.delete(utente);
	}
	
	public CredentialsService getCredentialsService() {
		return this.cs;
    }
	
	
}
