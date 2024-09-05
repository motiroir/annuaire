package isika.cda27.projet1.group4.annuaire.back;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import isika.cda27.projet1.group4.annuaire.back.exceptions.*;

/**
 * La classe UserManager gère les opérations liées aux utilisateurs,
 * telles que la création, la suppression, la connexion, la déconnexion, 
 * et la gestion des fichiers d'utilisateurs.
 * Elle utilise un fichier sérialisé pour sauvegarder et charger les utilisateurs.
 */
public class UserManager {
    private List<User> users;
    private File userFile;
    private User loggedInUser;

    /**
     * Constructeur de la classe UserManager.
     * Initialise le gestionnaire d'utilisateurs, charge les utilisateurs depuis un fichier.
     */
    public UserManager() {
        this.userFile = new File("src/main/resources/users/users.txt");
        this.users = new ArrayList<>();
        loadUsers();
    }

    /**
     * Récupère la liste des utilisateurs.
     * Cette méthode recharge les utilisateurs depuis le fichier pour s'assurer qu'elle est à jour.
     *
     * @return La liste des utilisateurs.
     */
    public List<User> getUsers() {
        this.loadUsers();
        return users;
    }

    /**
     * Crée un nouvel utilisateur avec un nom d'utilisateur, un mot de passe et un rôle.
     *
     * @param username Le nom d'utilisateur.
     * @param password Le mot de passe.
     * @param role     Le rôle de l'utilisateur.
     * @return true si l'utilisateur a été créé avec succès.
     * @throws UserAlreadyExistsException si un utilisateur avec le même nom existe déjà.
     */
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

    /**
     * Récupère un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur à rechercher.
     * @return L'utilisateur correspondant, ou null s'il n'est pas trouvé.
     */
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Supprime un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur à supprimer.
     * @return true si l'utilisateur a été supprimé avec succès.
     * @throws UserNotFoundException si l'utilisateur n'est pas trouvé.
     */
    public boolean deleteUser(String username) throws UserNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("Utilisateur non trouvé : " + username);
        }
        users.remove(user);
        saveUsers();
        return true;
    }

    /**
     * Authentifie un utilisateur en vérifiant son nom d'utilisateur et son mot de passe.
     *
     * @param username Le nom d'utilisateur.
     * @param password Le mot de passe.
     * @return L'utilisateur authentifié.
     * @throws AuthenticationFailedException si l'authentification échoue.
     */
    public User login(String username, String password) throws AuthenticationFailedException {
        User user = getUserByUsername(username);
        if (user != null && user.getPasswordHash().equals(hashPassword(password))) {
            loggedInUser = user;
            return loggedInUser;
        }
        throw new AuthenticationFailedException("Échec de l'authentification pour l'utilisateur : " + username);
    }

    /**
     * Déconnecte l'utilisateur actuellement connecté.
     */
    public void logout() {
        loggedInUser = null;
    }

    /**
     * Vérifie si un utilisateur est actuellement connecté.
     *
     * @return true si un utilisateur est connecté, sinon false.
     */
    public boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    /**
     * Sauvegarde la liste des utilisateurs dans un fichier.
     * Utilise la sérialisation pour écrire la liste des utilisateurs dans un fichier.
     */
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge la liste des utilisateurs depuis un fichier.
     * Utilise la désérialisation pour lire la liste des utilisateurs à partir d'un fichier.
     */
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

    /**
     * Hache un mot de passe en utilisant l'algorithme SHA-256.
     *
     * @param password Le mot de passe en clair.
     * @return Le hash du mot de passe sous forme de chaîne hexadécimale.
     */
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
