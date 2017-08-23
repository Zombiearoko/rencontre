	package com.bocobi2.rencontre.controller;
	
	import java.io.File;
import java.io.IOException;
import java.util.HashMap;
	import java.util.Map;
	
	import javax.mail.internet.MimeMessage;
	import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpSession;
	import javax.servlet.http.Part;
	
	import org.json.simple.JSONObject;
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
	import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.bind.annotation.ResponseBody;
	import org.springframework.web.bind.annotation.RestController;
	import org.springframework.web.servlet.ModelAndView;
	
	import com.bocobi2.rencontre.model.Customer;
	import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.Testimony;
import com.bocobi2.rencontre.repositories.MemberBufferRepository;
import com.bocobi2.rencontre.repositories.MemberRepository;
import com.bocobi2.rencontre.repositories.TestimonyRepository;
	
	
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
		/*
		 * Version POST
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/registration", method = RequestMethod.POST)
		
		public JSONObject  registrationPost(HttpServletRequest request ){
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
			String gender=  request.getParameter("gender");
			String pseudonym=  request.getParameter("pseudonym");
			String emailAdress=  request.getParameter("emailAdress");
			String password=  request.getParameter("password");
			String confirmPassword=  request.getParameter("confirmPassword");
			String phoneNumber=  request.getParameter("phoneNumber");
			//String picture= request.getParameter("picture");
			/***
			 * Reste les champs de la classe profil a recuperer!!!!!!!!!!!!!
			 * ceci je le ferrai apres avoir pris la liste des champs a Mr Foko ou a Mr Sofeu!!!!!!!
			 */
			System.out.println("-------------------------------");
			System.out.println(pseudonym);
			System.out.println("-------------------------------");
			System.out.println(emailAdress);
			System.out.println("-------------------------------");
			System.out.println(password);
			System.out.println("-------------------------------");
			System.out.println(confirmPassword);
			System.out.println("-------------------------------");
			System.out.println(phoneNumber);
			System.out.println("-------------------------------");
		
			
					File fileWay= new File(SAVE_DIR_PICTURE);
					String nom="picture"+pseudonym;
					Part part=null;
					if(!fileWay.exists()) fileWay.mkdir();
					try {
						part=request.getPart("picture");
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
					
						//enregistrement dans la zone tampon
						
						//resultRegistration.put("RegistrationStatus", "OK");
							
							memberBufferRepository.insert(member);
						        
								MimeMessage message = null;
								 MimeMessageHelper helper = new MimeMessageHelper(message,true);
							     helper.setSubject("Hi");
							      //helper.setFrom("customerserivces@yourshop.com");
							       helper.setTo(pseudonym);
							        String content  = ""+"http://localhost:8091/Member/ConfirmRegistration?user="+member.getPseudonym();
							            // Add an inline resource.
							            // use the true flag to indicate you need a multipart message
							         helper.setText("<html><body><p>" + content + "</p><img src='cid:company-logo'></body></html>", true);
							         sender.send(message);
						        
						        resultSendMail.put("SendStatus", "OK"); 
							
							    }catch(Exception ex) {
							        	
							        	resultSendMail.put("SendStatus", ex.getMessage());	 
							
							        }
						
						
				
			return resultSendMail;
			
		}
		/*
		 * Version GET
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/registration", method = RequestMethod.GET)
		
		public JSONObject  registrationGet(HttpServletRequest request ){
			
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
			String gender=  request.getParameter("gender");
			String pseudonym=  request.getParameter("pseudonym");
			String emailAdress=  request.getParameter("emailAdress");
			String password=  request.getParameter("password");
			String confirmPassword=  request.getParameter("confirmPassword");
			String phoneNumber=  request.getParameter("phoneNumber");
			//String picture= request.getParameter("picture");
			/***
			 * Reste les champs de la classe profil a recuperer!!!!!!!!!!!!!
			 * ceci je le ferrai apres avoir pris la liste des champs a Mr Foko ou a Mr Sofeu!!!!!!!
			 */
			System.out.println("-------------------------------");
			System.out.println(pseudonym);
			System.out.println("-------------------------------");
			System.out.println(emailAdress);
			System.out.println("-------------------------------");
			System.out.println(password);
			System.out.println("-------------------------------");
			System.out.println(confirmPassword);
			System.out.println("-------------------------------");
			System.out.println(phoneNumber);
			System.out.println("-------------------------------");
		
			
					File fileWay= new File(SAVE_DIR_PICTURE);
					String nom="picture"+pseudonym;
					Part part=null;
					if(!fileWay.exists()) fileWay.mkdir();
					try {
						part=request.getPart("picture");
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
					
						//enregistrement dans la zone tampon
						
						//resultRegistration.put("RegistrationStatus", "OK");
							
							memberBufferRepository.insert(member);
						        
								MimeMessage message = null;
								 MimeMessageHelper helper = new MimeMessageHelper(message,true);
							     helper.setSubject("Hi");
							      //helper.setFrom("customerserivces@yourshop.com");
							       helper.setTo(pseudonym);
							        String content  = ""+"http://localhost:8091/Member/ConfirmRegistration?user="+member.getPseudonym();
							            // Add an inline resource.
							            // use the true flag to indicate you need a multipart message
							         helper.setText("<html><body><p>" + content + "</p><img src='cid:company-logo'></body></html>", true);
							         sender.send(message);
						        
						        resultSendMail.put("SendStatus", "OK"); 
							
							    }catch(Exception ex) {
							        	
							        	resultSendMail.put("SendStatus", ex.getMessage());	 
							
							        }
						
						
				
			return resultSendMail;
			
		}
		/**
		 * end registration
		 */
		/**
		 * start confirm registration
		 */
		/*
		 * Version POST
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/ConfirmRegistration", method =RequestMethod.POST )
		@ResponseBody
		public JSONObject  confirmRegistrationPost(HttpServletRequest request ){
			
			JSONObject resultConfirmRegistration=new JSONObject();
			
			String pseudonym= request.getParameter("user");
			
			Member memberBuffer= memberBufferRepository.findByPseudonym(pseudonym);
			try{
			memberRepository.insert(memberBuffer);
			memberBufferRepository.delete(memberBuffer);
			resultConfirmRegistration.put("StatusConfirm", "OK");
			}catch(Exception ex){
				resultConfirmRegistration.put("StatusConfirm", "Failed");
			}
			return resultConfirmRegistration;
		}
		/*
		 * Version Get
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/ConfirmRegistration", method =RequestMethod.GET )
		@ResponseBody
		public JSONObject  confirmRegistrationGet(HttpServletRequest request ){
			JSONObject resultConfirmRegistration=new JSONObject();
			
			String pseudonym= request.getParameter("user");
			
			Member memberBuffer= memberBufferRepository.findByPseudonym(pseudonym);
			try{
				memberRepository.insert(memberBuffer);
				memberBufferRepository.delete(memberBuffer);
				resultConfirmRegistration.put("StatusConfirm", "OK");
			}catch(Exception ex){
				resultConfirmRegistration.put("StatusConfirm", "Failed");
			}
			return resultConfirmRegistration;
		}
		/**
		 * connexion of the member
		 * 
		 * cette methode permet de connecter un membre dans une session
		 */
		/*
		 * Version POST
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/Connexion", method= RequestMethod.POST)
		public JSONObject connexionMemberPost(HttpServletRequest requestConnexion){
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
		 * version Get
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/Connexion", method=RequestMethod.GET)
		public JSONObject connexionMemberGet(HttpServletRequest requestConnexion){
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
		/*
		 * Version POST
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/addTestimony", method= RequestMethod.POST)
		public JSONObject addTestimonyPost(HttpServletRequest requestTestimony) throws IOException, ServletException {
			JSONObject resultTestimony = new JSONObject();
			
			//String resultTestimony;
			
			String testimonyType=requestTestimony.getParameter("testimonyType");
			
			
						//int i=0;
			if(testimonyType.equals("videos")){
				
				//HttpSession memberSession=requestTestimony.getSession();
				File fileWay= new File(SAVE_DIR_TESTIMONY);
				String nom="testimony";
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
							resultTestimony.put("StatusAddTestimony", "OK");
							
						}catch (Exception ex){
								ex.printStackTrace();
								resultTestimony.put("StatusAddTestimony", "Failed adding");
				}
			}else{
				String testimonyContent= requestTestimony.getParameter("testimonyContent");
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
		 * Version Get
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/addTestimony", method=RequestMethod.GET)
		public JSONObject addTestimonyGet(HttpServletRequest requestTestimony) {
			JSONObject resultTestimony = new JSONObject();
			
			//String resultTestimony;
			
			String testimonyType=requestTestimony.getParameter("testimonyType");
			
			
						//int i=0;
			if(testimonyType.equals("videos")){
				HttpSession memberSession=requestTestimony.getSession();
						File fileWay= new File(SAVE_DIR_TESTIMONY);
						String nom="testimony"+((Member)memberSession.getAttribute("Member")).getPseudonym()+".avis";
						Part part=null;
						if(!fileWay.exists()) fileWay.mkdir();
						
						try{
							part=requestTestimony.getPart("testimony");
							String fileName=SAVE_DIR_TESTIMONY + File.separator + nom;
							part.write(SAVE_DIR_TESTIMONY + File.separator + nom);
							Testimony testimony=new Testimony();
							testimony.setTestimonyType(testimonyType);
							testimony.setTestimonyContent(fileName);
							testimonyRepository.insert(testimony);
							resultTestimony.put("StatusAddTestimony", "OK");
							
						}catch (Exception ex){
								ex.printStackTrace();
								resultTestimony.put("StatusAddTestimony", "Failed adding");
				}
			}else{
				String testimonyContent= requestTestimony.getParameter("testimonyContent");
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
