package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Immobile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	
	@NotNull
	private String descrizione;
	
	@Column(nullable = false)
	private String indirizzo;
	@Column(nullable = false)
	@Min(1)
	private int civico;
	
	
	//servirà per vedere quando è ancora prenotabile
	private boolean stato;
	
	@NotNull
	@Min(0)
	private int numeroPostiDisponibili;
	
	
	
	@OneToMany
	private List<Ticket> prenotazioni;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private AgenziaImmobiliare agenzia;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getCivico() {
		return civico;
	}

	public void setCivico(int civico) {
		this.civico = civico;
	}

	public boolean isStato() {
		return stato;
	}

	public void setStato(boolean stato) {
		this.stato = stato;
	}

	public int getNumeroPostiDisponibili() {
		return numeroPostiDisponibili;
	}

	public void setNumeroPostiDisponibili(int numeroPostiDisponibili) {
		this.numeroPostiDisponibili = numeroPostiDisponibili;
	}

	public List<Ticket> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(List<Ticket> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	

}
