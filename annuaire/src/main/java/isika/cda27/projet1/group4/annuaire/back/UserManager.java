package isika.cda27.projet1.group4.annuaire.back;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
	
	private List<User> users;

    public UserManager() {
        users = new ArrayList<>();
        // Ajouter quelques utilisateurs par défaut
        users.add(new User("Harry", "001", Role.STUDENT));
        users.add(new User("Rogue", "002", Role.TEACHER));
        users.add(new User("Dumbledore", "003", Role.ADMIN));
    }

    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(String username, String password, Role role) {
        users.add(new User(username, password, role));
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users); 
    }

}
