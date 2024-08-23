package isika.cda27.projet1.group4.annuaire.back;

import java.io.IOException;
import java.io.RandomAccessFile;

public class BinaryFileManager {

	public void objectBinaryFileWriter(Node noeud) {
		try {
			RandomAccessFile raf = new RandomAccessFile("src/main/resources/save/stagiairesDataBase.bin", "rw");
			//pos 0
			raf.writeChars(noeud.getKey().getNameLong());
			raf.writeChars(noeud.getKey().getFirstNameLong());
			raf.writeChars(noeud.getKey().getPostalCodeLong());
			raf.writeChars(noeud.getKey().getPromoLong());
			raf.writeInt(noeud.getKey().getYear());
			raf.writeInt(-1);
			raf.writeInt(-1);
			raf.writeInt(-1);
			//pos 120
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	//méthode ecriture fichier binaire sur l'objet lol
//	public void binaryFileWriter(Annuaire annuaire) {
//		try {
//			RandomAccessFile raf = new RandomAccessFile("src/main/resources/save/stagiairesDataBase.bin", "rw");
//
//			for (int i = 0; i < annuaire.getStagiaires().size(); i++) {
//				raf.writeChars(annuaire.getStagiaires().get(i).getNameLong());
//				raf.writeChars(annuaire.getStagiaires().get(i).getFirstNameLong());
//				raf.writeChars(annuaire.getStagiaires().get(i).getPostalCodeLong());
//				raf.writeChars(annuaire.getStagiaires().get(i).getPromoLong());
//				raf.writeInt(annuaire.getStagiaires().get(i).getYear());
//			}
//			raf.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

//	public void binaryFileReader(String filePath) {
//		try {
//			RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
//
//			raf.seek(0);
//			
//			for (int i = 0; i < raf.length(); i++) {
//				
//				
//				parfum += raf.readChar();
//			}
//
//			raf.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
////			String parfum = "";
////		Tarte troisiemeTarte = new Tarte();
////		troisiemeTarte.setNbParts(raf.readInt());
////		troisiemeTarte.setParfum(parfum.trim());
//
////		System.out.println(troisiemeTarte);
//
//	}

}
