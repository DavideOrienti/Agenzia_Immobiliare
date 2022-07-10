package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Agente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	
	@NotBlank
//	@Column(nullable = false)
	private String nome;
	
	@NotBlank
//	@Column(nullable = false)
	private String cognome;

	@NotNull
	//@Min(18)
	//@Max(100)
	private int eta;
	
     @NotNull
	//@Min(10)
	//@Max(15)
	private String numeroTelefono;
     
    
     @OneToMany(mappedBy="agente")
     private List<Immobile> immobili;
	

	@ManyToOne(cascade = CascadeType.PERSIST)
	private AgenziaImmobiliare agenzia;
	
//	@OneToMany(mappedBy = "agente")
//	private List<Ticket> prenotazioni;
	
	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public int getEta() {
		return eta;
	}

	public void setEta(int eta) {
		this.eta = eta;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefonico) {
		this.numeroTelefono = numeroTelefonico;
	}

	public AgenziaImmobiliare getAgenzia() {
		return agenzia;
	}

	public void setAgenzia(AgenziaImmobiliare agenzia) {
		this.agenzia = agenzia;
	}

//	public List<Ticket> getPrenotazioni() {
//		return prenotazioni;
//	}
//
//	public void setPrenotazioni(List<Ticket> prenotazioni) {
//		this.prenotazioni = prenotazioni;
//	}
	public List<Immobile> getImmobili() {
		return immobili;
	}

	public void setImmobili(List<Immobile> immobili) {
		this.immobili = immobili;
	}



}
