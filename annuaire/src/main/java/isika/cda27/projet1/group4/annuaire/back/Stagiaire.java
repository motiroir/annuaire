package isika.cda27.projet1.group4.annuaire.back;

public class Stagiaire {

	private String name;
	private String firstName; 
	private String postalCode;
	private String promo;
	private int year;
	
	
	
	public Stagiaire(String name, String firstName, String postalCode, String promo, int year) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.postalCode = postalCode;
		this.promo = promo;
		this.year = year;
	}



	@Override
	public String toString() {
		return "Stagiaire [name=" + name + ", firstName=" + firstName + ", postalCode=" + postalCode + ", promo="
				+ promo + ", year=" + year + "]";
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getPostalCode() {
		return postalCode;
	}



	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}



	public String getPromo() {
		return promo;
	}



	public void setPromo(String promo) {
		this.promo = promo;
	}



	public int getYear() {
		return year;
	}



	public void setYear(int year) {
		this.year = year;
	}
	
	
	
	
	
}
