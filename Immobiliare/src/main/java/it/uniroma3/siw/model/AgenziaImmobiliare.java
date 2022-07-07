package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
public class AgenziaImmobiliare {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	private String descrizione;
	
	@Column(nullable = false)
	private String indirizzo;
	@Column(nullable = false)
	@Min(1)
	private int civico;
	
	@NotNull
	@Min(10)
	@Max(15)
	private String numeroTelefonico;
	
	
	
	
	
	@OneToMany(mappedBy="agenzia",cascade = {CascadeType.REMOVE})
	private List<Agente> agenti;
	
	@OneToMany(mappedBy="agenzia",cascade = {CascadeType.REMOVE})
	private List<Immobile> immobili;
	
	
	
	
	
	
	
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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumeroTelefonico() {
		return numeroTelefonico;
	}

	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}

	public List<Agente> getAgenti() {
		return agenti;
	}

	public void setAgenti(List<Agente> agenti) {
		this.agenti = agenti;
	}

	public List<Immobile> getImmobili() {
		return immobili;
	}

	public void setImmobili(List<Immobile> immobili) {
		this.immobili = immobili;
	}
	
	

}
