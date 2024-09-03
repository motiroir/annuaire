package isika.cda27.projet1.group4.annuaire.back;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import isika.cda27.projet1.group4.annuaire.back.exceptions.*;

public class UserManager {
    private List<User> users;
    private File userFile;
    private User loggedInUser;

    public UserManager() {
        this.userFile = new File("src/main/resources/users/users.txt");
        this.users = new ArrayList<>();
        loadUsers();
    }

    public List<User> getUsers() {
    	this.loadUsers();
		return users;
	}

	

	// Créer un nouvel utilisateur
    public boolean createUser(String username, String password, Role role) throws UserAlreadyExistsException {
        if (getUserByUsername(username) != null) {
            throw new UserAlreadyExistsException("L'utilisateur " + username + " existe déjà.");
        }
        String passwordHash = hashPassword(password);
        User newUser = new User(username, passwordHash, role);
        users.add(newUser);
        saveUsers();
        return true;
    }

    // Lire un utilisateur par nom d'utilisateur
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    // Supprimer un utilisateur
    public boolean deleteUser(String username) throws UserNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("Utilisateur non trouvé : " + username);
        }
        users.remove(user);
        saveUsers();
        return true;
    }

    // Connexion de l'utilisateur
    public User login(String username, String password) throws AuthenticationFailedException {
        User user = getUserByUsername(username);
        if (user != null && user.getPasswordHash().equals(hashPassword(password))) {
            loggedInUser = user;
            return loggedInUser;
        }
        throw new AuthenticationFailedException("Échec de l'authentification pour l'utilisateur : " + username);
    }

    // Déconnexion de l'utilisateur
    public void logout() {
        loggedInUser = null;
    }

    // Vérifier si un utilisateur est connecté
    public boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    // Sauvegarder les utilisateurs dans un fichier
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Charger les utilisateurs depuis un fichier
    @SuppressWarnings("unchecked")
    private void loadUsers() {
        if (userFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile))) {
                users = (List<User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Fonction pour hacher le mot de passe
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
