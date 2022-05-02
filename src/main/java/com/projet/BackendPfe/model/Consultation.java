package com.projet.BackendPfe.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;



@Entity
public class Consultation {


 public Consultation(LocalDate dateConsult, Generaliste generaliste, Patient patient, byte[] image1_Droite,
			
			byte[] image1_Gauche) {
		super();
		this.dateConsult = dateConsult;
		this.generaliste = generaliste;
		this.patient = patient;
		this.image1_Droite = image1_Droite;
		
		this.image1_Gauche = image1_Gauche;
		
	}
private int demandeAvisD;
 private int demandeAvisG;
	public Consultation( int demandeAvisD,int demandeAvisG,LocalDate dateConsult, Generaliste generaliste, Patient patient,
			AutoDetection autoDetection, byte[] image1_Droite, byte[] image1_Gauche) {
		super();
		this.dateConsult = dateConsult;
		this.generaliste = generaliste;
		this.patient = patient;
		this.autoDetection = autoDetection;
		this.image1_Droite = image1_Droite;
		this.demandeAvisD=demandeAvisD;
		this.demandeAvisG=demandeAvisG;
	}

	public int getDemandeAvisD() {
		return demandeAvisD;
	}

	public void setDemandeAvisD(int demandeAvisD) {
		this.demandeAvisD = demandeAvisD;
	}

	public int getDemandeAvisG() {
		return demandeAvisG;
	}

	public void setDemandeAvisG(int demandeAvisG) {
		this.demandeAvisG = demandeAvisG;
	}

	public Generaliste getGeneraliste() {
		return generaliste;
	}

public Consultation(Generaliste generaliste, Patient patient) {
		super();
		this.generaliste = generaliste;
		this.patient = patient;
	}
private LocalDate dateConsult;
	//private  SimpleDateFormat dateFormat;
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	public Consultation(Generaliste generaliste, AutoDetection autoDetection) {
		super();
		this.generaliste = generaliste;
		this.autoDetection = autoDetection;
	}
	@ManyToOne()
	protected Generaliste generaliste;
	public AutoDetection getAutoDetection() {
		return autoDetection;
	}
	public void setAutoDetection(AutoDetection autoDetection) {
		this.autoDetection = autoDetection;
	}

	@ManyToOne()
	
	protected Patient patient;
	@OneToOne
	
	protected AutoDetection autoDetection;
	
	@Column(name = "image1Droite", length = 1000000)
	  protected byte[] image1_Droite;
	 
	@Column(name = "image1Gauche", length = 1000000)
	  protected byte[] image1_Gauche;
	 
	public byte[] getImage1_Droite() {
		return image1_Droite;
	}
	public void setImage1_Droite(byte[] image1_Droite) {
		this.image1_Droite = image1_Droite;
	}
	
	public byte[] getImage1_Gauche() {
		return image1_Gauche;
	}
	public void setImage1_Gauche(byte[] image1_Gauche) {
		this.image1_Gauche = image1_Gauche;
	}
	

	
public Consultation() {}


public Consultation(Generaliste generaliste,Patient patient,LocalDate dateConsult,
		byte[] image1_Gauche ,byte[] image1_Droite) {
	super();
	this.generaliste = generaliste;
	this.patient = patient;

	this.image1_Gauche=image1_Gauche ; 
	
	this.image1_Droite=image1_Droite ; 
	
	this. dateConsult=dateConsult;
}




	public LocalDate getDateConsult() {
	return dateConsult;
}

public void setDateConsult(LocalDate dateConsult) {
	this.dateConsult = dateConsult;
}

	public void setGeneraliste(Generaliste generaliste) {
		this.generaliste = generaliste;
	}

	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	
	
}
