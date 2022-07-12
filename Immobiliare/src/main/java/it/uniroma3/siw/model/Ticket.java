package it.uniroma3.siw.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	
	
	private int Progressivo;
	
	
	private String dataPrenotazione;
	
	
//	
//	@ManyToOne(cascade = CascadeType.PERSIST)
//	private Agente agente;
	
	@ManyToOne//(cascade = CascadeType.PERSIST)
	private Immobile immobile;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Utente utente;
	
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public int getProgressivo() {
		return Progressivo;
	}

	public void setProgressivo(int progressivo) {
		Progressivo = progressivo;
	}

	public String getDataPrenotazione() {
		return dataPrenotazione;
	}

	public void setDataPrenotazione(String dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}

//	public Agente getAgente() {
//		return agente;
//	}
//
//	public void setAgente(Agente agente) {
//		this.agente = agente;
//	}

	public Immobile getImmobile() {
		return immobile;
	}

	public void setImmobile(Immobile immobile) {
		this.immobile = immobile;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	

}
