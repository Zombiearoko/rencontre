package com.bocobi2.rencontre.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.repositories.MemberRepository;


@RestController
//@RequestMapping("/ResgisterMember")
public class MemberController {
	
	@Autowired
	MemberRepository memberRepository;
	
	/*****(value="/registrationForm", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView  registrationFormGet(HttpServletRequest request){
		
		ModelAndView model =new ModelAndView("http://localalhost:8091/InscriptionMembre");
		
		return model;
		
	}****/
	
	@RequestMapping(value="/registration", method = {RequestMethod.POST, RequestMethod.GET}, params = { "pseudonym","emailAdress", "phoneNumber"  } )
	public String  registrationGet(HttpServletRequest request ){
		
		String result;
		String failedSaved= null;
		Map<String, String> errors = new HashMap<String, String>();
		
		String pseudonym= (String) request.getParameter("pseudonym");
		String emailAdress= (String) request.getParameter("emailAdress");
		String phoneNumber= (String) request.getParameter("phoneNumber");
		
		try {
			
			validatePhoneNumber( phoneNumber );
			} catch (Exception e) {
			/* Gérer les errors de validation ici. */
			}
		try {
			validatePseudonym( pseudonym );
			} catch ( Exception e ) {
			errors.put( "pseudonymErrorMessage", e.getMessage() );
			}
		try {
			validatePseudonym( emailAdress );
			} catch ( Exception e ) {
			errors.put( "emailAdressErrorMessage", e.getMessage() );
			}
		try {
			validatePseudonym( phoneNumber );
			} catch ( Exception e ) {
			errors.put( "phoneNumberErrorMessage", e.getMessage() );
			}
		
		if ( errors.isEmpty() ) {
				Member member= new Member();
				member.setPseudonym(pseudonym);
				member.setEmailAdress(emailAdress);
				member.setPhoneNumber(phoneNumber);
				try{
					memberRepository.save(member);
					result="OK";
				}catch(Exception ex){
				result="{\"failedSaved\": failed registration}";
			
				}
			
			} else {
				
					
			result = "{pseudonymErrorMessage:"+errors.get("pseudonymErrorMessage")+""
					+ ",emailAdressErrorMessage:"+errors.get("emailAdressErrorMessage")+ ""
							+ ",phoneNumberErrorMessage:"+errors.get("phoneNumberErrorMessage")+"}";
			}
		return result;
		
	}
	
	
	private void validatePhoneNumber( String phoneNumber ) throws Exception{}
	
	
	
	private void validatePseudonym( String pseudonym ) throws Exception{
			if ( pseudonym != null && pseudonym.trim().length() < 3 ) {
			throw new Exception( "Le pseudonym d'utilisateur doit contenir au moins 3 caractères." );
			}
			}
		
	private void validateEmailAdress( String emailAdress) throws Exception{
			if ( emailAdress != null && emailAdress.trim().length() != 0 ) {
			if ( !emailAdress.matches(
			"([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
			throw new Exception( "Merci de saisir une adresse mail valide." );
			}
			} else {
			throw new Exception( "Merci de saisir une adresse mail." );
			}
	}
}
