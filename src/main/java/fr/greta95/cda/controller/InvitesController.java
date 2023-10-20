package fr.greta95.cda.controller;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import fr.greta95.cda.dao.InviteRepository;
import fr.greta95.cda.model.Invite;

@RestController
public class InvitesController {
	@Autowired // Injection du repository
	private InviteRepository inviteRepository;

	@GetMapping("/defaut")
	public Invite getDefaut() {
		return new Invite("Palmer", "Jack", "sav.spring@gmail.com");
	}

	@GetMapping("/all")
	ArrayList<Invite> getAll() {
		return (ArrayList<Invite>) inviteRepository.findAll();
	}

	@GetMapping("/{invite}") // Map requêtes GET
	public ResponseEntity<Invite> rechercheInviteParGetInvite(@PathVariable("invite") String nom) {
		ArrayList<Invite> liste = inviteRepository.findByNom(nom);
		if (liste.size() == 0)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(liste.get(0));
	}

	@GetMapping("/") // Map requêtes GET
	public ResponseEntity<Invite> rechercheInviteParGet(@RequestParam String nom, @RequestParam String prenom) {
		ArrayList<Invite> liste = inviteRepository.findByNomAndPrenom(nom, prenom);
		if (liste.size() == 0)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(liste.get(0));
	}

	@PostMapping(path = "/ajoute") // Map requêtes POST
	public ResponseEntity<Void> ajouteInvite(@RequestParam String nom, @RequestParam String prenom,
			@RequestParam String email) {
		Invite r = inviteRepository.findByNomAndPrenomAndEmail(nom, prenom, email);
		if (r != null)
			return ResponseEntity.noContent().build();
		Invite i = new Invite(nom, prenom, email);
		i = inviteRepository.save(i);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(i.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/") // Map requêtes DELETE
	public ResponseEntity<Void> supprimerInvite(@RequestParam String nom, @RequestParam String prenom) {
		ArrayList<Invite> liste = inviteRepository.findByNomAndPrenom(nom, prenom);
		if (liste.size() == 0)
			return ResponseEntity.notFound().build();
		else {
			inviteRepository.delete(liste.get(0));
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	

	@PutMapping(path = "/modifie") // Map requêtes PUT
	public ResponseEntity<Void> modifieEmailInvite(@RequestParam String nom, @RequestParam String prenom,
			@RequestParam String email) {
		ArrayList<Invite> liste = inviteRepository.findByNomAndPrenom(nom, prenom);
		if (liste.size() != 0) {
			Invite i = liste.get(0);
			i.setEmail(email);
			inviteRepository.save(i);
			return ResponseEntity.ok().build();
		}
		Invite i = new Invite(nom, prenom, email);
		i = inviteRepository.save(i);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(i.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	// ajoute un invite, l'objet est reçu au format json
	@PostMapping(value = "/ajoutejson")
	public ResponseEntity<Void> ajouterInvite(@RequestBody Invite invite) {
		Invite r = inviteRepository.findByNomAndPrenomAndEmail(invite.getNom(), invite.getPrenom(), invite.getEmail());
		if (r != null)
			return ResponseEntity.noContent().build();
		invite.setDate(Date.from(Instant.now()));
		Invite inviteAjoute = inviteRepository.save(invite);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(inviteAjoute.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/deljson") // Map requêtes DELETE
	public ResponseEntity<Void> supprimerInvite(@RequestBody Invite invite) {
		ArrayList<Invite> liste = inviteRepository.findByNomAndPrenom(invite.getNom(), invite.getPrenom());
		if (liste.size() == 0)
			return ResponseEntity.notFound().build();
		else {
			inviteRepository.delete(liste.get(0));
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
}
