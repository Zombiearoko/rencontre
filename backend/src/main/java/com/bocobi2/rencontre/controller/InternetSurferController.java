
package com.bocobi2.rencontre.controller;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.MemberBuffer;
import com.bocobi2.rencontre.model.MemberErrorType;
import com.bocobi2.rencontre.model.Status;
import com.bocobi2.rencontre.model.Testimony;
import com.bocobi2.rencontre.repositories.MemberBufferRepository;
import com.bocobi2.rencontre.repositories.MemberRepository;
import com.bocobi2.rencontre.repositories.StatusRepository;
import com.bocobi2.rencontre.repositories.TestimonyRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/rencontre/InternetSurfer")
public class InternetSurferController {
	
	public static final Logger logger = LoggerFactory.getLogger(InternetSurferController.class);
	
	
	 private static final String SAVE_DIR_PICTURE = "/home/saphir/test1/workspaceGit/rencontre/backend/src/main/resources/UploadFile/UploadPicture";


	
	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberBufferRepository memberBufferRepository;
	
	@Autowired
	TestimonyRepository testimonyRepository;
	
	@Autowired
	StatusRepository statusRepository;
	
	

	
	/**
	 * choix du type de rencontre
	 */
	/**
	 * VERSION POST
	 * 
	 * @param request
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value = "/chooseRencontre", method = RequestMethod.POST)
	public ResponseEntity<?> choiseRencontrePost(HttpServletRequest request, UriComponentsBuilder ucBuilder) {

		String birthDate = request.getParameter("bithDate");
		String gender = request.getParameter("gender");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int birthYear = 0;
		try {
			birthYear = new Integer(birthDate);
			int age = year - birthYear;
			if (age < 18) {
				String typeMeeting = "Amicale";
			} else {
				String typeMeeting = request.getParameter("typeMeeting");
				
			}
		} catch (NumberFormatException numberEx) {

		}

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	

	/****
	 * registration member in the data base methode qui gere l'enregistrement
	 * d'un membre dans la bd
	 * 
	 */
	@SuppressWarnings("unchecked")
	/*
	 * Version POST
	 */
	@ResponseBody
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> registrationPost(HttpServletRequest request, UriComponentsBuilder ucBuilder) {

		/*
		 * recuperation des donnees du formulaire
		 */
		String birthDate = request.getParameter("bithDate");
		String gender = request.getParameter("gender");
		String pseudonym = request.getParameter("pseudonym");
		String emailAdress = request.getParameter("emailAdress");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String phoneNumber = request.getParameter("phoneNumber");
		// String picture= request.getParameter("picture");
		MemberBuffer member = new MemberBuffer();
		/***
		 * Reste les champs de la classe profil a recuperer!!!!!!!!!!!!! ceci je
		 * le ferrai apres avoir pris la liste des champs a Mr Foko ou a Mr
		 * Sofeu!!!!!!!
		 */
		System.out.println("-------------------------------");
		System.out.println(pseudonym);
		System.out.println("-------------------------------");
		System.out.println(emailAdress);
		System.out.println("-------------------------------");
		System.out.println(password);
		System.out.println("-------------------------------");
		System.out.println("-------------------------------");
		System.out.println(phoneNumber);
		System.out.println("-------------------------------");

		//File fileWay = new File(SAVE_DIR_PICTURE);
		//String nom = "picture" + pseudonym + ".png";
		//Part part = null;
		//if (!fileWay.exists())
			//fileWay.mkdir();
		try {
			//part = request.getPart("picture");
			//String fileName = SAVE_DIR_PICTURE + File.separator + nom;
			//part.write(SAVE_DIR_PICTURE + File.separator + nom);

			member.setGender(gender);
			member.setBirthDate(birthDate);
			member.setPseudonym(pseudonym);
			member.setEmailAdress(emailAdress);
			member.setPhoneNumber(phoneNumber);
			member.setPassword(password);
			//member.setPicture(fileName);

			/***
			 * Enregistrement du membre dans une zone tampon de la base de
			 * donnees Notification de l'utilisateur par mail pour confirmer
			 * l'email et la creaction du compte
			 * 
			 * {ca c'est ce qui reste a faire ici!!!!!!!!}
			 */

			// enregistrement dans la zone tampon

			Properties properties = new Properties();
			properties.put("mail.smtp.host", "smtp.gmail.com");
			// properties.put("mail.smtp.host", "smtp-relay.gmail.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.starttls.required", "false");
			properties.put("mail.smtp.connectiontimeout", "5000");
			properties.put("mail.smtp.timeout", "5000");
			properties.put("mail.smtp.writetimeout", "5000");
			Session session = Session.getInstance(properties, null);

			String content1 = "Thanks to create your count in our website \n"
					+ " Now,lick on this link to activate E-mail adress: http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user=" + member.getPseudonym()
					;
			String subject1 = "confirm your E-mail adress";
			// String form="saphirmfogo@gmail.com";
			MimeMessage msg = new MimeMessage(session);
			/// msg.setFrom(new InternetAddress(form));
			msg.setRecipients(MimeMessage.RecipientType.TO, emailAdress);
			msg.setSubject(subject1);
			msg.setText(content1);
			msg.setSentDate(new Date());

			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			
			String mess = "ok";
			if (mess.equals("ok")) {
				memberBufferRepository.deleteAll();
				memberBufferRepository.insert(member);
				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(ucBuilder.path("/rencontre/InternetSurfer/registration/{pseudonym}")
						.buildAndExpand(member.getPseudonym()).toUri());

				return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
			} else {
				logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
				return new ResponseEntity(new MemberErrorType("the email is not validate"), HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());

			logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
			return new ResponseEntity(
					new MemberErrorType(
							"Unable to create. " + "A Member with name " + member.getPseudonym() + " already exist"),
					HttpStatus.CONFLICT);
		}

	}

	/*
	 * Version GET
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ResponseEntity<?> registrationGet(HttpServletRequest request, UriComponentsBuilder ucBuilder) {

		/*
		 * recuperation des donnees du formulaire
		 */
		String birthDate = request.getParameter("bithDate");
		String gender = request.getParameter("gender");
		String pseudonym = request.getParameter("pseudonym");
		String emailAdress = request.getParameter("emailAdress");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String phoneNumber = request.getParameter("phoneNumber");
		// String picture= request.getParameter("picture");
		MemberBuffer member = new MemberBuffer();
		/***
		 * Reste les champs de la classe profil a recuperer!!!!!!!!!!!!! ceci je
		 * le ferrai apres avoir pris la liste des champs a Mr Foko ou a Mr
		 * Sofeu!!!!!!!
		 */
		System.out.println("-------------------------------");
		System.out.println(pseudonym);
		System.out.println("-------------------------------");
		System.out.println(emailAdress);
		System.out.println("-------------------------------");
		System.out.println(password);
		System.out.println("-------------------------------");
		System.out.println("-------------------------------");
		System.out.println(phoneNumber);
		System.out.println("-------------------------------");

		//File fileWay = new File(SAVE_DIR_PICTURE);
		///String nom = "picture" + pseudonym + ".png";
		//Part part = null;
		//if (!fileWay.exists())
			//fileWay.mkdir();
		try {
			//part = request.getPart("picture");
			// fileName = SAVE_DIR_PICTURE + File.separator + nom;
			//part.write(SAVE_DIR_PICTURE + File.separator + nom);

			member.setGender(gender);
			member.setBirthDate(birthDate);
			member.setPseudonym(pseudonym);
			member.setEmailAdress(emailAdress);
			member.setPhoneNumber(phoneNumber);
			member.setPassword(password);
			//member.setPicture(fileName);

			/***
			 * Enregistrement du membre dans une zone tampon de la base de
			 * donnees Notification de l'utilisateur par mail pour confirmer
			 * l'email et la creaction du compte
			 * 
			 * {ca c'est ce qui reste a faire ici!!!!!!!!}
			 */

			// enregistrement dans la zone tampon

			Properties properties = new Properties();
			properties.put("mail.smtp.host", "smtp.gmail.com");
			// properties.put("mail.smtp.host", "smtp-relay.gmail.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.starttls.required", "false");
			properties.put("mail.smtp.connectiontimeout", "5000");
			properties.put("mail.smtp.timeout", "5000");
			properties.put("mail.smtp.writetimeout", "5000");
			Session session = Session.getInstance(properties, null);

			String content1 = "Thanks to create your count in our website" + " Now  click here "
					+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user=" + member.getPseudonym()
					+ " to validate your E-mail adress";
			String subject1 = "confirm your E-mail adress";
			// String form="saphirmfogo@gmail.com";
			MimeMessage msg = new MimeMessage(session);
			/// msg.setFrom(new InternetAddress(form));
			msg.setRecipients(MimeMessage.RecipientType.TO, emailAdress);
			msg.setSubject(subject1);
			msg.setText(content1);
			msg.setSentDate(new Date());

			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			
			String mess = "ok";
			if (mess.equals("ok")) {
				memberBufferRepository.insert(member);
				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(ucBuilder.path("/rencontre/InternetSurfer/registration/{pseudonym}")
						.buildAndExpand(member.getPseudonym()).toUri());

				return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
			} else {
				logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
				return new ResponseEntity(new MemberErrorType("the email is not validate"), HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());

			logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
			return new ResponseEntity(
					new MemberErrorType(
							"Unable to create. " + "A Member with name " + member.getPseudonym() + " already exist"),
					HttpStatus.CONFLICT);
		}
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/confirmRegistration", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> confirmRegistrationPost(HttpServletRequest request) {

		String pseudonym = request.getParameter("user");
		System.out.println(pseudonym);
		MemberBuffer memberBuffer = memberBufferRepository.findByPseudonym(pseudonym);
		Status statusMember=statusRepository.findByStatusName("connected");
		Member member = new Member();
		try {

			member.setPseudonym(memberBuffer.getPseudonym());
			member.setBirthDate(memberBuffer.getBirthDate());
			member.setEmailAdress(memberBuffer.getEmailAdress());
			member.setGender(memberBuffer.getGender());
			member.setPhoneNumber(memberBuffer.getPhoneNumber());
			member.setPassword(memberBuffer.getPassword());
			member.setPicture(memberBuffer.getPicture());
			member.setStatus(statusMember);
			memberRepository.deleteAll();
			memberRepository.insert(member);
			memberBufferRepository.delete(memberBuffer);
			return new ResponseEntity<Member>(member, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("Unable to create. A Member with name {} already exist", pseudonym);
			return new ResponseEntity(
					new MemberErrorType("Unable to create. " + "A Member with name "
							+ "" + pseudonym + " already exist"),
					HttpStatus.CONFLICT);
		}

	}

	/*
	 * Version Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/confirmRegistration", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> confirmRegistrationGet(HttpServletRequest request) {

		String pseudonym = request.getParameter("user");

		MemberBuffer memberBuffer = memberBufferRepository.findByPseudonym(pseudonym);
		Status statusMember=statusRepository.findByStatusName("connected");
		Member member = new Member();
		try {

			member.setPseudonym(memberBuffer.getPseudonym());
			member.setBirthDate(memberBuffer.getBirthDate());
			member.setEmailAdress(memberBuffer.getEmailAdress());
			member.setPhoneNumber(memberBuffer.getPhoneNumber());
			member.setGender(memberBuffer.getGender());
			member.setPassword(memberBuffer.getPassword());
			member.setPicture(memberBuffer.getPicture());
			member.setStatus(statusMember);
			memberRepository.insert(member);
			memberBufferRepository.delete(memberBuffer);
			return new ResponseEntity<Member>(member, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("Unable to create. A Member with name {} already exist", pseudonym);
			return new ResponseEntity(
					new MemberErrorType("Unable to create. " + "A Member with name "
							+ "" + pseudonym + " already exist"),
					HttpStatus.CONFLICT);
		}
	}
	
	
	/**
	 * Start visualize testimony
	 */
	/*
	 * Version Post
	 */
	@RequestMapping(value = "/visualizeVideoTestimony", method = RequestMethod.POST)
	public ResponseEntity<List<Testimony>> visualizeVideoTestimonyPost(HttpServletRequest request) {
		// JSONObject listOfTestimony = new JSONObject();
		List<Testimony> listOfTestimony = testimonyRepository.findByTestimonyType("videos");

		if (listOfTestimony.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Testimony>>(listOfTestimony, HttpStatus.OK);
	}

	/*
	 * Version Get
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/visualizeVideoTestimony", method = RequestMethod.GET)
	public ResponseEntity<List<Testimony>> visualizeVideoTestimonyGet(HttpServletRequest request) {
		// JSONObject listOfTestimony = new JSONObject();
		List<Testimony> listOfTestimony = testimonyRepository.findByTestimonyType("videos");

		if (listOfTestimony.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Testimony>>(listOfTestimony, HttpStatus.OK);
	}

	/*
	 * End visualize testimony videos
	 */
	/**
	 * start visualition testimony write
	 * 
	 * @param request
	 * @return
	 */
	/*
	 * Version POST
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/visualizeWriteTestimony", method = RequestMethod.POST)
	public ResponseEntity<List<Testimony>> visualizeWriteTestimonyPost(HttpServletRequest request) {

		List<Testimony> listOfTestimony = testimonyRepository.findByTestimonyType("write");

		if (listOfTestimony.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Testimony>>(listOfTestimony, HttpStatus.OK);
	}

	/*
	 * Version GET
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/visualizeWriteTestimony", method = RequestMethod.GET)
	public ResponseEntity<List<Testimony>> visualizeWriteTestimonyGet(HttpServletRequest request) {

		List<Testimony> listOfTestimony = testimonyRepository.findByTestimonyType("write");

		if (listOfTestimony.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Testimony>>(listOfTestimony, HttpStatus.OK);
	}
}
/*
 * end visualize testimony write
 */
