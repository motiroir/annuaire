package isika.cda27.projet1.group4.annuaire.back;

/**
 * Représente un stagiaire avec des informations telles que le nom, le prénom,
 * le code postal, la promotion et l'année.
 * Cette classe contient également des méthodes pour gérer et formater les informations
 * des stagiaires.
 */
public class Stagiaire {

    /** Taille du champ nom en nombre de caractères. */
    public static final int NAME_SIZE = 20;
    /** Taille du champ prénom en nombre de caractères. */
    public static final int FIRSTNAME_SIZE = 20;
    /** Taille du champ code postal en nombre de caractères. */
    public static final int POSTALCODE_SIZE = 2;
    /** Taille du champ promotion en nombre de caractères. */
    public static final int PROMO_SIZE = 10;
    /** Taille du champ année en nombre d'octets. */
    public static final int YEAR_SIZE = 1;
    /** Taille totale en octets d'un enregistrement de stagiaire. */
    public static final int STAGIAIRE_SIZE_OCTET = NAME_SIZE * 2 + FIRSTNAME_SIZE * 2 + POSTALCODE_SIZE * 2
            + PROMO_SIZE * 2 + YEAR_SIZE * 4;

    /** Le nom du stagiaire. */
    public String name;
    /** Le prénom du stagiaire. */
    public String firstName;
    /** Le code postal du stagiaire. */
    public String postalCode;
    /** La promotion du stagiaire. */
    public String promo;
    /** L'année d'inscription du stagiaire. */
    public int year;

    /**
     * Crée une instance de Stagiaire avec les informations fournies.
     *
     * @param name       Le nom du stagiaire.
     * @param firstName  Le prénom du stagiaire.
     * @param postalCode Le code postal du stagiaire.
     * @param promo      La promotion du stagiaire.
     * @param year       L'année d'inscription du stagiaire.
     */
    public Stagiaire(String name, String firstName, String postalCode, String promo, int year) {
        super();
        this.name = name;
        this.firstName = firstName;
        this.postalCode = postalCode;
        this.promo = promo;
        this.year = year;
    }

    /**
     * Crée une instance de Stagiaire avec uniquement le nom fourni.
     * Les autres champs sont initialisés avec des valeurs par défaut.
     *
     * @param name Le nom du stagiaire.
     */
    public Stagiaire(String name) {
        super();
        this.name = name;
        this.firstName = "";
        this.postalCode = "";
        this.promo = "";
        this.year = 0;
    }

    /**
     * Retourne une chaîne de caractères représentant l'objet Stagiaire.
     *
     * @return Une chaîne de caractères contenant les informations du stagiaire.
     */
    @Override
    public String toString() {
        return "Stagiaire [name=" + name + ", firstName=" + firstName + ", postalCode=" + postalCode + ", promo="
                + promo + ", year=" + year + "]";
    }

    /**
     * Compare cet objet Stagiaire avec un autre pour déterminer s'ils sont égaux.
     * Deux objets Stagiaire sont considérés comme égaux si tous leurs attributs
     * sont égaux.
     *
     * @param obj L'objet à comparer avec cet objet Stagiaire.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        // Vérifier si les objets sont identiques
        if (this == obj) {
            return true;
        }
        // Vérifier si l'objet est null ou de classes différentes
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Convertir l'objet en Stagiaire pour comparer les attributs
        Stagiaire stagiaire = (Stagiaire) obj;
        // Comparer tous les attributs un par un
        return this.name.equals(stagiaire.name) && this.firstName.equals(stagiaire.firstName)
                && this.postalCode.equals(stagiaire.postalCode) && this.promo.equals(stagiaire.promo)
                && this.year == stagiaire.year;
    }

    /**
     * Retourne le nom du stagiaire.
     *
     * @return Le nom du stagiaire.
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne le nom du stagiaire, ajusté à la taille maximale définie (NAME_SIZE).
     * Si le nom est plus court que la taille maximale, il est complété par des espaces.
     * Si le nom est plus long, il est tronqué.
     *
     * @return Le nom ajusté du stagiaire.
     */
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

    /**
     * Modifie le nom du stagiaire.
     *
     * @param name Le nouveau nom du stagiaire.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le prénom du stagiaire.
     *
     * @return Le prénom du stagiaire.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retourne le prénom du stagiaire, ajusté à la taille maximale définie (FIRSTNAME_SIZE).
     * Si le prénom est plus court que la taille maximale, il est complété par des espaces.
     * Si le prénom est plus long, il est tronqué.
     *
     * @return Le prénom ajusté du stagiaire.
     */
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

    /**
     * Modifie le prénom du stagiaire.
     *
     * @param firstName Le nouveau prénom du stagiaire.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retourne le code postal du stagiaire.
     *
     * @return Le code postal du stagiaire.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Retourne le code postal du stagiaire, ajusté à la taille maximale définie (POSTALCODE_SIZE).
     * Si le code postal est plus court que la taille maximale, il est complété par des espaces.
     * Si le code postal est plus long, il est tronqué.
     *
     * @return Le code postal ajusté du stagiaire.
     */
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

    /**
     * Modifie le code postal du stagiaire.
     *
     * @param postalCode Le nouveau code postal du stagiaire.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Retourne la promotion du stagiaire.
     *
     * @return La promotion du stagiaire.
     */
    public String getPromo() {
        return promo;
    }

    /**
     * Retourne la promotion du stagiaire, ajustée à la taille maximale définie (PROMO_SIZE).
     * Si la promotion est plus courte que la taille maximale, elle est complétée par des espaces.
     * Si la promotion est plus longue, elle est tronquée.
     *
     * @return La promotion ajustée du stagiaire.
     */
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

    /**
     * Modifie la promotion du stagiaire.
     *
     * @param promo La nouvelle promotion du stagiaire.
     */
    public void setPromo(String promo) {
        this.promo = promo;
    }

    /**
     * Retourne l'année d'inscription du stagiaire.
     *
     * @return L'année d'inscription du stagiaire.
     */
    public int getYear() {
        return year;
    }

    /**
     * Modifie l'année d'inscription du stagiaire.
     *
     * @param year La nouvelle année d'inscription du stagiaire.
     */
    public void setYear(int year) {
        this.year = year;
    }

}
