package com.projet.BackendPfe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.BackendPfe.model.AutoDetection;
import com.projet.BackendPfe.model.AvisExpert;
import com.projet.BackendPfe.model.Consultation;

public interface AvisExpertRepository extends JpaRepository<AvisExpert, Long> {
	
}
