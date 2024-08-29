package isika.cda27.projet1.group4.annuaire.back;

public class User {

	private String username;
	private String password;
	private Role role;

	public User(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User() {
		
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Role getRole() {
		return role;
	}
}
