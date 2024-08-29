package isika.cda27.projet1.group4.annuaire;

import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;

public class HelloAsposePdf {
    public static void main(String[] args) {
        // Create a new PDF document
        Document pdfDocument = new Document();

        // Add a page to the document
        Page page = pdfDocument.getPages().add();

        // Add text to the page
        TextFragment textFragment = new TextFragment("Hello Aspose.PDF");
        page.getParagraphs().add(textFragment);

        // Save the document
        pdfDocument.save("HelloAsposePdf.pdf");
    }
}

