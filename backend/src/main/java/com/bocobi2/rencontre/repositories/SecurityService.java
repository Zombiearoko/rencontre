package com.bocobi2.rencontre.repositories;

public interface SecurityService {

	 public String findLoggedInUsername();
	 public void autologin(String pseudonym, String password);
}
