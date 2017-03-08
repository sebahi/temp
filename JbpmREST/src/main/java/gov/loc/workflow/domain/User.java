package gov.loc.workflow.domain;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class User {

	Map<String, String> map = new HashMap<>();

	private static String userName;
	private static String password;

	public User() {
	}

	public Map<String, String> getMap() {
		return map;
	}

	public User(String userName, String password) {
		User.userName = userName;
		User.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		User.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		User.password = password;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

}
