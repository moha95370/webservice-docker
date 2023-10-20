package fr.greta95.cda.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "invite")
public class Invite implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date date;
	private String nom;
	private String prenom;
	private String email;

	public Invite() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Invite(long id, String nom, String prenom, String email) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		LocalDate localdate = LocalDate.now();
		date = new Date(1000 * 24 * 3600 * localdate.toEpochDay());
		System.out.println("date de " + nom + " = " + date);
	}

	public Invite(String nom, String prenom, String email) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		LocalDate localdate = LocalDate.now();
		date = new Date(1000 * 24 * 3600 * localdate.toEpochDay());
	}

	public String toString() {
		String ladate = LocalDate.ofEpochDay((long) (Math.ceil((double) date.getTime() / (double) (1000 * 3600 * 24))))
				.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		return nom + " " + " " + prenom + " " + ladate;
	}
}
