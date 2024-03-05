package GestionDesStages_V_0;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@Table(name="ETUDIANT")
@AllArgsConstructor()
@NoArgsConstructor()
@ToString()	
	public class Etudiant  {
		

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
		@Getter @Setter private int niveau;
		@Column
		@Getter @Setter private String section;
		@Column
		@Getter @Setter private int ID_encadrant;
		@Column
		@Getter @Setter private Date dateStage;
		
		
		

		public Etudiant(int cin, String nom, String prenom, String email, String mdp, int tel, int niveau,String section, String date) {
			
			 this.cin = cin ; 
			 this.nom = nom ;
			 this.prenom = prenom ; 
			 this.email=email;
			 this.mdp=mdp;
			this.tel=tel ;
			this.niveau=niveau;
			this.section=section;
			//this.encadrant=prof;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
			try {
				this.dateStage = simpleDateFormat.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
		
		public void afficherEtudiant() {
			
			System.out.println("CIN:       \t"+cin);
			System.out.println("Nom:       \t"+nom);
			System.out.println("Prenom:    \t"+prenom);
			System.out.println("Email:     \t"+email);
			System.out.println("Tel:       \t"+tel);		
			System.out.println("Niv:       \t"+niveau);
			System.out.println("Section:   \t"+section);
			//System.out.println("Encadrant: \t"+encadrant.getNom().toUpperCase()+" "+encadrant.getPrenom());
			System.out.println("Date Stage:\t"+dateStage);
			
			
		}


		/*public boolean envoyerDoc(Document doc) {
			
			return true;
		}
		
	    public boolean envoyerDemandeEncadrant(Document doc, int cin) {
			
			return true;
		}
	    
	  //  public Professeur chercherEncadrant(int cin) {
			
		//	return prof;
		//}
	    
	   public void afficherHistoStages() {
			
			
		}
	*/
	}



