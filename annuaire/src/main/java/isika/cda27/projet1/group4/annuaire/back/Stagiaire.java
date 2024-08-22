package isika.cda27.projet1.group4.annuaire.back;

public class Stagiaire {

	public static final int NAME_SIZE = 20;
	public static final int FIRSTNAME_SIZE = 20;
	public static final int POSTALCODE_SIZE = 2;
	public static final int PROMO_SIZE = 10;
	public static final int YEAR_SIZE = 1;
	public static final int STAGIAIRE_SIZE_OCTET = NAME_SIZE*2 + FIRSTNAME_SIZE*2 + POSTALCODE_SIZE*2 + PROMO_SIZE*2 + YEAR_SIZE*4;

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

	public String getNameLong() {
		String nameLong = this.name;
		if (nameLong.length() < NAME_SIZE) {
			for (int i = this.name.length(); i < NAME_SIZE; i++) {
				nameLong += " ";
			}
		} else {
			nameLong = nameLong.substring(0, NAME_SIZE);
		}
		return nameLong;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFirstNameLong() {
		String firstNameLong = this.firstName;
		if (firstNameLong.length() < FIRSTNAME_SIZE) {
			for (int i = this.firstName.length(); i < FIRSTNAME_SIZE; i++) {
				firstNameLong += " ";
			}
		} else {
			firstNameLong = firstNameLong.substring(0, FIRSTNAME_SIZE);
		}
		return firstNameLong;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getPostalCodeLong() {
		String postalCodeLong = this.postalCode;
		if (postalCodeLong.length() < POSTALCODE_SIZE) {
			for (int i = this.postalCode.length(); i < POSTALCODE_SIZE; i++) {
				postalCodeLong += " ";
			}
		} else {
			postalCodeLong = postalCodeLong.substring(0, POSTALCODE_SIZE);
		}
		return postalCodeLong;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPromo() {
		return promo;
	}

	public String getPromoLong() {
		String promoLong = this.promo;
		if (promoLong.length() < PROMO_SIZE) {
			for (int i = this.promo.length(); i < PROMO_SIZE; i++) {
				promoLong += " ";
			}
		} else {
			promoLong = promoLong.substring(0, PROMO_SIZE);
		}
		return promoLong;
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
