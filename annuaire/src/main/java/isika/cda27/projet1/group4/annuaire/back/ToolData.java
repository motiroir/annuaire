package isika.cda27.projet1.group4.annuaire.back;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ToolData {
	
	
	
	
		public static void main(String[] args) {
			try {
			
				
	            //BufferedReader permet de lire une ligne à la fois
	            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/STAGIAIRES.DON"));
	            String ligne;
	            while ((ligne = reader.readLine())!= null) {
	           System.out.println(ligne);
	            }
	            reader.close();
	        } catch (FileNotFoundException e) {
			} catch (IOException e) {
				System.out.println("Le fichier n'a pas été trouvé.");
	            e.printStackTrace();
			}
		}
}












