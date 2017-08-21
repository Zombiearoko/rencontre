	package com.bocobi2.rencontre.controller;
	
	import java.io.File;
	import java.util.HashMap;
	import java.util.Map;
	
	import javax.mail.internet.MimeMessage;
	import javax.servlet.ServletContext;
	import javax.servlet.annotation.MultipartConfig;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpSession;
	import javax.servlet.http.Part;
	
	import org.json.simple.JSONObject;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.mail.javamail.JavaMailSender;
	import org.springframework.mail.javamail.MimeMessageHelper;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.bind.annotation.ResponseBody;
	import org.springframework.web.bind.annotation.RestController;
	import org.springframework.web.servlet.ModelAndView;
	
	import com.bocobi2.rencontre.model.Customer;
	import com.bocobi2.rencontre.model.Member;
	import com.bocobi2.rencontre.repositories.MemberRepository;
	
	
	@RestController
	@RequestMapping("/Member")
	@MultipartConfig(fileSizeThreshold=1024*1024*2,maxFileSize=1024*1024*10,maxRequestSize=1024*1024*50)
	public class MemberController {
		
		private static final String SAVE_DIR_TESTIMONY="/home/saphir/test1/workspaceGit/rencontre/backend/src/main/resources/UploadFile/UploadTestimony";
		private static final String SAVE_DIR_PICTURE="/home/saphir/test1/workspaceGit/rencontre/backend/src/main/resources/UploadFile/UploadPicture";
		
		@Autowired
		MemberRepository memberRepository;
		
		@Autowired
		MemberBufferRepository memberBufferRepository;
		
		@Autowired
		TestimonyRepository testimonyRepository;
		
		@Autowired
		 private JavaMailSender sender;
		
		/*****(value="/registrationForm", method = {RequestMethod.POST, RequestMethod.GET})
		public ModelAndView  registrationFormGet(HttpServletRequest request){
			
			ModelAndView model =new ModelAndView("http://localalhost:8091/InscriptionMembre");
			
			return model;
			
		}****/
		/**** registration member in the data base
		 * methode qui gere l'enregistrement d'un membre dans la bd 
		 * 
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/registration", method = {RequestMethod.POST, RequestMethod.GET} )
		
		public JSONObject  registration(HttpServletRequest request ){
			/*
			 * Variable de la notification par mail nous utiliserons le serveur de messagerie SMTP
			 */
			JSONObject resultRegistration = new JSONObject();
			JSONObject resultSendMail = new JSONObject();
		    FormFieldController formField= new FormFieldController ();
			/**
			 * Variables d'erreur et de succes
			 */
			//String resultRegistration;
			
			//String failedSaved= null;
			Map<String, String> errors = new HashMap<String, String>();
			/*
			 * recuperation des donnees du formulaire
			 */
			String gender= (String) request.getParameter("gender");
			String pseudonym= (String) request.getParameter("pseudonym");
			String emailAdress= (String) request.getParameter("emailAdress");
			String password= (String) request.getParameter("password");
			String confirmPassword= (String) request.getParameter("confirmPassword");
			String phoneNumber= (String) request.getParameter("phoneNumber");
			String picture= (String) request.getParameter("picture");
			/***
			 * Reste les champs de la classe profil a recuperer!!!!!!!!!!!!!
			 * ceci je le ferrai apres avoir pris la liste des champs a Mr Foko ou a Mr Sofeu!!!!!!!
			 */
			System.out.println("-------------------------------");
			System.out.println(pseudonym);
			System.out.println("-------------------------------");
			System.out.println(emailAdress);
			
			try {
				formField.validateGender( gender );
				} catch ( Exception e ) {
				errors.put( "sexErrorMessage", e.getMessage() );
				}
			try {
				formField.validatePseudonym( pseudonym );
				} catch ( Exception e ) {
				errors.put( "pseudonymErrorMessage", e.getMessage() );
				}
			try {
				formField.validateEmailAdress( emailAdress );
				} catch ( Exception e ) {
				errors.put( "emailAdressErrorMessage", e.getMessage() );
				}
			try {
				formField.validatePassword( password, confirmPassword );
				} catch ( Exception e ) {
				errors.put( "passwordErrorMessage", e.getMessage() );
				}
			try {
				formField.validatePhoneNumber( phoneNumber );
				} catch ( Exception e ) {
				errors.put( "phoneNumberErrorMessage", e.getMessage() );
				}
			try {
				formField.validatePicture( picture );
				} catch ( Exception e ) {
				errors.put( "pictureErrorMessage", e.getMessage() );
				}
			
			if ( errors.isEmpty() ) {
					File fileWay= new File(SAVE_DIR_TESTIMONY);
					String nom="picture"+pseudonym;
					Part part=null;
					if(!fileWay.exists()) fileWay.mkdir();
					String fileName=SAVE_DIR_PICTURE + File.separator + nom;
					part.write(SAVE_DIR_PICTURE + File.separator + nom);
					Member member= new Member();
					member.setPseudonym(pseudonym);
					member.setEmailAdress(emailAdress);
					member.setPhoneNumber(phoneNumber);
					member.setGender(gender);
					member.setPassword(password);
					member.setPicture(fileName);
					/***
					 * Enregistrement du membre dans une zone tampon de la base de donnees
					 * Notification de l'utilisateur par mail pour confirmer l'email et la creaction du compte
					 * 
					 * {ca c'est ce qui reste a faire ici!!!!!!!!}
					 */
					try{
						//enregistrement dans la zone tampon
						memberBufferRepository.insert(member);
						resultRegistration.put("RegistrationStatus", "OK");
						
						
						try {
								MimeMessage message = sender.createMimeMessage();
						        // Enable the multipart flag!
					
						        MimeMessageHelper helper = new MimeMessageHelper(message,true);
						        helper.setTo(emailAdress);
						        String content1 = ""+"http://localhost:8091/Member/ConfirmRegistration?user="+member.getPseudonym();
						        
						        helper.setText("How are you? my name is Saphir and I'm thee best of best"+content1);
					
						        helper.setSubject("Hi");
						        sender.send(message);
						        resultSendMail.put("SendStatus", "OK"); 
							
							    }catch(Exception ex) {
							        	resultSendMail.put("SendStatus", "Failed E-mail Adress");
							        	resultSendMail.put("SendStatus", ex.getMessage());	 
							
							        }
						
						//System.out.println(result);
					}catch(Exception ex){
						resultSendMail.put("SendStatus", "Failed E-mail Adress");
					//System.out.println(result);
					}
				} else {	
					resultRegistration.put("pseudonymErrorMessage", errors.get("pseudonymErrorMessage")) ;
					resultRegistration.put("emailAdressErrorMessage", errors.get("emailAdressErrorMessage")) ;
					resultRegistration.put("phoneNumberErrorMessage", errors.get("phoneNumberErrorMessage")) ;
					return resultRegistration;
				//System.out.println(result);
				}
			return resultSendMail;
			
		}
		/**
		 * end registration
		 */
		/**
		 * start confirm registration
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/ConfirmRegistration", method = {RequestMethod.POST, RequestMethod.GET} )
		@ResponseBody
		public JSONObject  confirmRegistration(HttpServletRequest request ){
			JSONObject resultConfirmRegistration;
			String pseudonym= request.getParameter("user");
			
			Member memberBuffer= memberBufferRepository.findByPseudonym(pseudonym);
			try{
			memberRepository.insert(memberBuffer);
			memberBufferRepository.delete(memberBuffer);
			resultConfirmRegistration.put("StatusConfirm", "OK");
			}catch(Exception ex){
				resultConfirmRegistration.put("StatusConfirm", "Failed");
			}
		}
		/**
		 * connexion of the member
		 * 
		 * cette methode permet de connectre un membre dans une session
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/Connexion", method={RequestMethod.GET, RequestMethod.POST})
		public JSONObject connexionMember(HttpServletRequest requestConnexion){
			JSONObject connexionResult = new JSONObject();
			HttpSession session = requestConnexion.getSession();
			String failedValue;
			//String connexionResult;
			//recuperation des champs de connexion
			String pseudonym= requestConnexion.getParameter("pseudonym");
			String password= requestConnexion.getParameter("password");
			//recherche du membre dans la base de donnees
			for (Member member : memberRepository.findAll()) {
				if(member.getPseudonym().equals(pseudonym)){
					if(member.getPassword().equals(password)){
						
						/**Le membre ayant le mot de passe "password" et pseudo "pseudonym" a ete trouve 
						 * on l'ajoute s une session
						 */
						session.setAttribute("Member", member);
						connexionResult.put("StatusConnexion", "OK");
						
					}else{
						session.setAttribute("Member", null);
						connexionResult.put("StatusConnexion", "Failed");
						connexionResult.put("Raison", "Failed password");
					}
				}else{
					connexionResult.put("StatusConnexion", "Failed");
					connexionResult.put("Raison", "Failed pseudo");
				}
				}
			
			return connexionResult;
			
		}
		/*
		 * end connexion
		 */
		
		/*
		 * add testimony
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/addTestimony", method={RequestMethod.GET, RequestMethod.POST})
		public JSONObject addTestimony(HttpServletRequest requestTestimony){
			JSONObject resultTestimony = new JSONObject();
			
			//String resultTestimony;
			
			String testimonyType=requestTestimony.getParameter("testimonyType");
			String testimonyContent= requestTestimony.getParameter("testimonyContent");
			
						//int i=0;
			if(testimonyType.equals("videos")){
				HttpSession memberSession=requestTestimony.getSession();
						File fileWay= new File(SAVE_DIR_TESTIMONY);
						String nom="testimony"+((Member)memberSession.getAttribute("Member")).getPseudonym()+".aviss";
						Part part=null;
						if(!fileWay.exists()) fileWay.mkdir();
						
						part=requestTestimony.getPart("file");
						try{
							String fileName=SAVE_DIR_TESTIMONY + File.separator + nom;
							part.write(SAVE_DIR_TESTIMONY + File.separator + nom);
							Testimony testimony=new Testimony();
							testimony.setTestimonyType(testimonyType);
							testimony.setTestimonyContent(fileName);
							testimonyRepository.insert(testimony);
							resultTestimony="{StatusAddTestimony: OK}";
				
						}catch (Exception ex){
								ex.printStackTrace();
								resultTestimony.put("StatusAddTestimony", "Failed adding");
				}
			}else{
				try{
					
					Testimony testimony=new Testimony();
					testimony.setTestimonyType(testimonyType);
					testimony.setTestimonyContent(testimonyContent);
					
					testimonyRepository.insert(testimony);
					resultTestimony.put("StatusAddTestimony", "OK");
					
		
				}catch (Exception ex){
						ex.printStackTrace();
						resultTestimony.put("StatusAddTestimony", "Failed adding");
						
		}
			}			
			
			return resultTestimony;
		}
		/*
		 * end add testimony
		 */
		
		/**
		 * Start  paiement frais d'abonnement
		 */
		
	
	}
