package GestionDesStages_V_0;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.testng.annotations.Test;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentTest {

    @Test
    public void testAfficherDoc() {
        // Créer une instance de Document avec des données de test
        Document document = new Document(1, "Test Document", "01-01-22", "Description test", 123, 456);

        // Appeler la méthode afficherDoc
        document.afficherDoc();

        // Assertions spécifiques pour chaque attribut de Document
        assertEquals(1, document.getId());
        assertEquals("Test Document", document.getLibelle());
        // Ajouter des assertions pour d'autres attributs comme la date, la description, etc.
    }


    // Méthode utilitaire pour capturer la sortie console
    private String captureOutput(Runnable task) {
        try {
            // Redirection de la sortie console vers une chaîne de caractères
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream oldOut = System.out;
            System.setOut(ps);

            // Exécution de la tâche qui imprime dans la console
            task.run();

            // Récupération de la sortie console capturée
            System.out.flush();
            System.setOut(oldOut);
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
