package fr.greta95.cda.dao;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.greta95.cda.model.Invite;

public interface InviteRepository extends CrudRepository<Invite, Long> {
	ArrayList<Invite> findByNomAndPrenom(String nom, String prenom);

	ArrayList<Invite> findByNom(String nom);

	Invite findByNomAndPrenomAndEmail(String nom, String prenom, String email);
	@Query("select i from Invite i group by i.nom,i.prenom,i.email having count(*)>1") 
	Collection<Invite> rechercherDoublons() ;

}