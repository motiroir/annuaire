package isika.cda27.projet1.group4.annuaire.back;
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String passwordHash; // Stockage du hash du mot de passe
    private Role role; // Attribut pour le rôle de l'utilisateur

    public User(String username, String passwordHash, Role role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    
    public User() {
        
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
