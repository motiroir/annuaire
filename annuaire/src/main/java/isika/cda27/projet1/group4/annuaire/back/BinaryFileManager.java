package isika.cda27.projet1.group4.annuaire.back;


public class BinaryFileManager {

	

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
