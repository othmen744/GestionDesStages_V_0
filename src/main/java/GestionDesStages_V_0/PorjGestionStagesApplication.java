package GestionDesStages_V_0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PorjGestionStagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PorjGestionStagesApplication.class, args);
		
		Document doc = new Document(1,"stage","12-15-2023","aaa",1,2);
		System.out.println(doc.toString());
		System.out.println(doc.getId());
		
	}

}
