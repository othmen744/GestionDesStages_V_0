package GestionDesStages_V_0;


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
@Table (name="ADMINISTRATEUR")
@ToString()
@NoArgsConstructor
@AllArgsConstructor
public class Administrateur {
	

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
	

	
	public boolean ajouterDoc() {
		return true;
	}
	
	public boolean supprimerDoc() {
		return true;
	}
	
	public boolean modifierDoc() {
		return true;
	}

}
