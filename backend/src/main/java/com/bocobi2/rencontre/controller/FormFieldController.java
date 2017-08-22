package com.bocobi2.rencontre.controller;

public class FormFieldController {
	/***
	 * Validation of the different paremeter
	 */
		
	public void validateGender(String gender ) throws Exception{
			if (gender.isEmpty() ) {
			throw new Exception( "Please choose your sex" );
			}
			}
		
	public  void validatePseudonym(String pseudonym ) throws Exception{
			if ( pseudonym != null && pseudonym.trim().length() < 3 ) {
			throw new Exception( "Le pseudonym d'utilisateur doit contenir au moins 3 caractères." );
			}
			}
		
	public void validateEmailAdress( String emailAdress) throws Exception{
			if ( emailAdress != null && emailAdress.trim().length() != 0 ) {
			if ( !emailAdress.matches(
			"([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
			throw new Exception( "Merci de saisir une adresse mail valide." );
			}
			} else {
			throw new Exception( "Merci de saisir une adresse mail." );
			}
	}
	
	public void validatePassword( String password, String confirmPassword ) throws Exception{
			if (password != null && password.trim().length() != 0 &&
					confirmPassword != null && confirmPassword.trim().length() != 0) {
			if (!password.equals(confirmPassword)) {
			throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
			} else if (password.trim().length() < 8) {
			throw new Exception("Les mots de passe doivent contenir au moins 8 caractères.");
			}else if(!password.contains("[^0-9]*")){
				throw new Exception("Les mots de passe doivent contenir au moins un chiffre.");
			}
			
			} else {
			throw new Exception("Merci de saisir et confirmer votre mot de passe.");
			}
			}
	public void validatePhoneNumber( String phoneNumber ) throws Exception{
		if ( phoneNumber != null && phoneNumber.trim().length() < 9 ) {
		throw new Exception( "Le phoneNumber d'utilisateur doit contenir au moins 3 caractères." );
		}
		}
	
	public void validatePicture( String picture ) throws Exception{
		if ( picture.isEmpty() ) {
		throw new Exception( "Please enter your picture" );
		}
		}
	
	//end Validation

}
