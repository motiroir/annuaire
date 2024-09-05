package isika.cda27.projet1.group4.annuaire.back;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.FileNotFoundException;

/**
 * La classe PdfPrint fournit une méthode pour exporter les données d'un TableView JavaFX vers un fichier PDF.
 * Elle utilise la bibliothèque iText pour créer et gérer les documents PDF.
 */
public class PdfPrint {

    /**
     * Exporte les données d'un TableView JavaFX dans un fichier PDF spécifié par le chemin donné.
     *
     * @param <T>      Le type des éléments contenus dans le TableView.
     * @param tableView Le TableView dont les données doivent être exportées.
     * @param pdfPath  Le chemin du fichier PDF à créer.
     * @throws FileNotFoundException Si le chemin du fichier PDF spécifié est invalide ou inaccessible.
     */
    public static <T> void printTableToPdf(TableView<T> tableView, String pdfPath) throws FileNotFoundException {
        // Créer un PdfWriter avec le chemin spécifié
        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // Créer une table avec le même nombre de colonnes que le TableView
        Table pdfTable = new Table(tableView.getColumns().size());

        // Ajouter les en-têtes de colonne
        for (TableColumn<T, ?> column : tableView.getColumns()) {
            pdfTable.addHeaderCell(new Cell().add(new Paragraph(column.getText())));
        }

        // Ajouter les données du tableau
        for (T item : tableView.getItems()) {
            for (TableColumn<T, ?> column : tableView.getColumns()) {
                Object cellValue = column.getCellData(item);
                pdfTable.addCell(new Cell().add(new Paragraph(cellValue != null ? cellValue.toString() : "")));
            }
        }

        // Ajouter la table au document
        document.add(pdfTable);

        // Fermer le document
        document.close();
    }
}
