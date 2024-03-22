package GestionDesStages_V_0;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="PROFESSEUR")
@AllArgsConstructor()
@NoArgsConstructor()
@ToString()
public class Professeur {

	@Id
	@Getter @Setter private int cin;
	@Column
	@Getter @Setter private String nom;
	@Column
	@Getter @Setter private String prenom;
	@Column
	@Getter @Setter private String email;
	@Column
	@Getter @Setter private String mdp;
	@Column
	@Getter @Setter private int tel;

	@Column
	@Getter @Setter private int capacite ;
	@Column
	@Getter @Setter private String specialite ;
	@Column
	@Getter @Setter private String information ;
//	@Column
	//@Getter @Setter private ArrayList <Etudiant> etudiants ;



	public void afficherProf() {

		System.out.println("CIN:       \t"+cin);
		System.out.println("Nom:       \t"+nom);
		System.out.println("Prenom:    \t"+prenom);
		System.out.println("Email:     \t"+email);
		System.out.println("Tel:       \t"+tel);
		System.out.println("Capacité:      \t"+capacite);
		System.out.println("Specialité:    \t"+specialite);
		System.out.println("Information:   \t"+information);
		System.out.println("\n ***** AFFICHAGE DES ETUDIANTS ENCADRES ***** \n");





	}

	public boolean accepterEtud() {
		return true ;
	}
	public boolean refuserEtud() {
		return true ;
	}
	/*public Etudiant chercherEtud() {
		return  ;
	} */
	public boolean testerCapacite() {
		return true ;
	}
	public void afficherEtudiant() {}





}