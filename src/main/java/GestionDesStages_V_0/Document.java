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
@AllArgsConstructor()
@NoArgsConstructor()
@ToString()
@Table (name="DOCUMENT")
public class Document {
	@Id
	@Column
	@Getter @Setter private int id;
	@Column
	@Getter @Setter private String libelle;
	@Column
	@Getter @Setter private Date date;
	@Column
	@Getter @Setter private String description;
	@Column
	@Getter @Setter private int ID_source;
	@Column
	@Getter @Setter private int ID_destination;
	
	
public Document(int id, String libelle, String date, String description,int id1, int id2) {
		
		this.id=id;
		this.libelle=libelle;
		this.description=description;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
		try {
			this.date = simpleDateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		this.ID_source =id1;
		this.ID_destination=id2;
		
		
	}
	
	
	public void afficherDoc() {
		
		System.out.println("ID:         \t"+id);
		System.out.println("Libelle:    \t"+libelle);
		System.out.println("Date:       \t"+date);
		System.out.println("Description:\t"+description);


	}

}
