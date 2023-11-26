
public class User {
	public String id;
	public String name;
	public String password;
	public String email;
	//For login purpose
	public User(String e,String p) {
		this.email = e;
		this.password = p;
	}
	
	//	For register purpose
	public User(String n,String e,String p) {
		this.email = e;
		this.name = n;
		this.password = p;
	}
	
	// For General purpose
	public User(String id,String n,String e,String p) {
		this.id = id;
		this.name = n;
		this.email = e;
		this.password = p;
	}
	
	public boolean SignUp() {
		return UserAuthentication.SignUp(this);
	}
	
	public boolean LogIn() {
		return UserAuthentication.SignIn(this);
	}
}
