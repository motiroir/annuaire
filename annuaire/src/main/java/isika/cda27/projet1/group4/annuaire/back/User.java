package isika.cda27.projet1.group4.annuaire.back;

import java.io.Serializable;

/**
 * La classe User représente un utilisateur du système avec un nom d'utilisateur, un mot de passe haché, et un rôle.
 * Cette classe implémente l'interface Serializable pour permettre la sérialisation des objets User.
 */
public class User implements Serializable {
    private String username;
    private String passwordHash; // Stockage du hash du mot de passe
    private Role role;

    /**
     * Constructeur de la classe User avec des paramètres.
     *
     * @param username     Le nom d'utilisateur de l'utilisateur.
     * @param passwordHash Le hash du mot de passe de l'utilisateur.
     * @param role         Le rôle de l'utilisateur.
     */
    public User(String username, String passwordHash, Role role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    /**
     * Constructeur par défaut de la classe User.
     * Crée un utilisateur vide sans initialiser les attributs.
     */
    public User() {
        
    }

    /**
     * Obtient le nom d'utilisateur.
     *
     * @return Le nom d'utilisateur.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Obtient le hash du mot de passe.
     *
     * @return Le hash du mot de passe.
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Définit le hash du mot de passe.
     *
     * @param passwordHash Le nouveau hash du mot de passe.
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Obtient le rôle de l'utilisateur.
     *
     * @return Le rôle de l'utilisateur.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Définit le rôle de l'utilisateur.
     *
     * @param role Le nouveau rôle de l'utilisateur.
     */
    public void setRole(Role role) {
        this.role = role;
    }
}