package com.bocobi2.rencontre.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.bocobi2.rencontre.model.Customer;
import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.MemberBuffer;
import com.bocobi2.rencontre.model.Testimony;
import com.bocobi2.rencontre.repositories.MemberBufferRepository;
import com.bocobi2.rencontre.repositories.MemberRepository;
import com.bocobi2.rencontre.repositories.TestimonyRepository;

@RestController
@RequestMapping("/Member")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class MemberController {

	private static final String SAVE_DIR_TESTIMONY = "/home/saphir/test1/workspaceGit/rencontre/backend/src/main/resources/UploadFile/UploadTestimony";
	private static final String SAVE_DIR_PICTURE = "/home/saphir/test1/workspaceGit/rencontre/backend/src/main/resources/UploadFile/UploadPicture";

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberBufferRepository memberBufferRepository;

	@Autowired
	TestimonyRepository testimonyRepository;

	@Autowired
	private MailSender sender;

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

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
	@RequestMapping(value = "/choiseRencontre", method = RequestMethod.POST)
	public ResponseEntity<?> choiseRencontrePost(HttpServletRequest request, UriComponentsBuilder ucBuilder) {

		String bithDate = request.getParameter("bithDate");
		String gender = request.getParameter("gender");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		if (bithDate.regionMatches(year, "1980", year, 50)) {

			return new ResponseEntity(HttpStatus.OK);
		}

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	/**
	 * VERSION GET
	 */
	@RequestMapping(value = "/choiseRencontre", method = RequestMethod.GET)
	public ResponseEntity<?> choiseRencontreGet(HttpServletRequest request, UriComponentsBuilder ucBuilder) {

		String bithDate = request.getParameter("bithDate");
		String gender = request.getParameter("gender");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		if (bithDate.regionMatches(year, "1980", year, 50)) {

			return new ResponseEntity(HttpStatus.OK);
		}

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	/****
	 * registration member in the data base methode qui gere l'enregistrement
	 * d'un membre dans la bd
	 * 
	 */
	/*
	 * Version POST
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> registrationPost(HttpServletRequest request, UriComponentsBuilder ucBuilder) {

		/*
		 * recuperation des donnees du formulaire
		 */

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
		System.out.println(confirmPassword);
		System.out.println("-------------------------------");
		System.out.println(phoneNumber);
		System.out.println("-------------------------------");

		File fileWay = new File(SAVE_DIR_PICTURE);
		String nom = "picture" + pseudonym + "png";
		Part part = null;
		if (!fileWay.exists())
			fileWay.mkdir();
		try {
			part = request.getPart("picture");
			String fileName = SAVE_DIR_PICTURE + File.separator + nom;
			part.write(SAVE_DIR_PICTURE + File.separator + nom);

			member.setPseudonym(pseudonym);
			member.setEmailAdress(emailAdress);
			member.setPhoneNumber(phoneNumber);
			member.setPassword(password);
			member.setPicture(fileName);
			/***
			 * Enregistrement du membre dans une zone tampon de la base de
			 * donnees Notification de l'utilisateur par mail pour confirmer
			 * l'email et la creaction du compte
			 * 
			 * {ca c'est ce qui reste a faire ici!!!!!!!!}
			 */

			// enregistrement dans la zone tampon

			// resultRegistration.put("RegistrationStatus", "OK");

			memberBufferRepository.insert(member);

			SimpleMailMessage message = new SimpleMailMessage();
			String content = "Thanks to create yours count in our website<br/>" + "Now click here"
					+ "http://localhost:8091/Member/ConfirmRegistration?user=" + member.getPseudonym()
					+ "to validate your E-mail adress";
			String subject = "confirm your E-mail adress";
			String from = "saphirmfogo@gmail.com";
			message.setFrom(from);
			message.setTo(emailAdress);
			message.setSubject(subject);
			message.setText(content);
			sender.send(message);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(
					ucBuilder.path("/Member/registration/{pseudonym}").buildAndExpand(member.getPseudonym()).toUri());
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);

		} catch (Exception ex) {

			logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
			return new ResponseEntity(logger, HttpStatus.CONFLICT);
		}

	}

	/*
	 * Version GET
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ResponseEntity<?> registrationGet(HttpServletRequest request, UriComponentsBuilder ucBuilder) {

		/*
		 * recuperation des donnees du formulaire
		 */

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
		System.out.println(confirmPassword);
		System.out.println("-------------------------------");
		System.out.println(phoneNumber);
		System.out.println("-------------------------------");

		File fileWay = new File(SAVE_DIR_PICTURE);
		String nom = "picture" + pseudonym + "png";
		Part part = null;
		if (!fileWay.exists())
			fileWay.mkdir();
		try {
			part = request.getPart("picture");
			String fileName = SAVE_DIR_PICTURE + File.separator + nom;
			part.write(SAVE_DIR_PICTURE + File.separator + nom);

			member.setPseudonym(pseudonym);
			member.setEmailAdress(emailAdress);
			member.setPhoneNumber(phoneNumber);
			member.setPassword(password);
			member.setPicture(fileName);
			/***
			 * Enregistrement du membre dans une zone tampon de la base de
			 * donnees Notification de l'utilisateur par mail pour confirmer
			 * l'email et la creaction du compte
			 * 
			 * {ca c'est ce qui reste a faire ici!!!!!!!!}
			 */

			// enregistrement dans la zone tampon

			// resultRegistration.put("RegistrationStatus", "OK");

			memberBufferRepository.insert(member);

			SimpleMailMessage message = new SimpleMailMessage();
			String content = "Thanks to create yours count in our website<br/>" + "Now click here"
					+ "http://localhost:8091/Member/ConfirmRegistration?user=" + member.getPseudonym()
					+ "to validate your E-mail adress";
			String subject = "confirm your E-mail adress";
			String from = "saphirmfogo@gmail.com";
			message.setFrom(from);
			message.setTo(emailAdress);
			message.setSubject(subject);
			message.setText(content);
			sender.send(message);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(
					ucBuilder.path("/Member/registration/{pseudonym}").buildAndExpand(member.getPseudonym()).toUri());
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);

		} catch (Exception ex) {

			logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
			return new ResponseEntity(logger, HttpStatus.CONFLICT);
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ConfirmRegistration", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> confirmRegistrationPost(HttpServletRequest request) {

		String pseudonym = request.getParameter("user");

		MemberBuffer memberBuffer = memberBufferRepository.findByPseudonym(pseudonym);
		Member member = new Member();
		try {

			member.setPseudonym(memberBuffer.getPseudonym());
			member.setBirthDate(memberBuffer.getBirthDate());
			member.setEmailAdress(memberBuffer.getEmailAdress());
			member.setGender(memberBuffer.getGender());
			member.setPassword(memberBuffer.getPassword());
			member.setPicture(memberBuffer.getPicture());
			memberRepository.insert(member);
			memberBufferRepository.delete(memberBuffer);
			return new ResponseEntity<Member>(member, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
			return new ResponseEntity(logger, HttpStatus.CONFLICT);
		}

	}

	/*
	 * Version Get
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ConfirmRegistration", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> confirmRegistrationGet(HttpServletRequest request) {

		String pseudonym = request.getParameter("user");

		MemberBuffer memberBuffer = memberBufferRepository.findByPseudonym(pseudonym);
		Member member = new Member();
		try {

			member.setPseudonym(memberBuffer.getPseudonym());
			member.setBirthDate(memberBuffer.getBirthDate());
			member.setEmailAdress(memberBuffer.getEmailAdress());
			member.setGender(memberBuffer.getGender());
			member.setPassword(memberBuffer.getPassword());
			member.setPicture(memberBuffer.getPicture());
			memberRepository.insert(member);
			memberBufferRepository.delete(memberBuffer);
			return new ResponseEntity<Member>(member, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
			return new ResponseEntity(logger, HttpStatus.CONFLICT);
		}
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
	@RequestMapping(value = "/Connexion", method = RequestMethod.POST)
	public ResponseEntity<?> connexionMemberPost(HttpServletRequest requestConnexion) {

		HttpSession session = requestConnexion.getSession();
		String failedValue;
		// String connexionResult;
		// recuperation des champs de connexion
		String pseudonym = requestConnexion.getParameter("pseudonym");
		String password = requestConnexion.getParameter("password");
		System.out.println("-------------------------------");
		System.out.println(pseudonym);
		System.out.println("-------------------------------");

		System.out.println("-------------------------------");
		System.out.println(password);
		// recherche du membre dans la base de donnees
		try {
			Member member = new Member();
			member = memberRepository.findByPseudonym(pseudonym);
			if (member != null) {
				if (member.getPassword().equals(password)) {
					session.setAttribute("Member", member);
					return new ResponseEntity<Member>(member, HttpStatus.OK);
				} else {
					session.setAttribute("Member", null);
					logger.error("Member with password {} not found.", password);
					return new ResponseEntity(logger, HttpStatus.NOT_FOUND);
				}
			} else {
				logger.error("Member with password {} not found.", pseudonym);
				return new ResponseEntity(logger, HttpStatus.NOT_FOUND);

			}
		} catch (Exception ex) {
			logger.error("Member with pseudonym {} not found.", pseudonym);
			return new ResponseEntity(logger, HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * version Get
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Connexion", method = RequestMethod.GET)
	public ResponseEntity<?> connexionMemberGet(HttpServletRequest requestConnexion) {

		HttpSession session = requestConnexion.getSession();
		String failedValue;
		// String connexionResult;
		// recuperation des champs de connexion
		String pseudonym = requestConnexion.getParameter("pseudonym");
		String password = requestConnexion.getParameter("password");
		System.out.println("-------------------------------");
		System.out.println(pseudonym);
		System.out.println("-------------------------------");

		System.out.println("-------------------------------");
		System.out.println(password);
		// recherche du membre dans la base de donnees
		try {
			Member member = new Member();
			member = memberRepository.findByPseudonym(pseudonym);
			if (member != null) {
				if (member.getPassword().equals(password)) {
					session.setAttribute("Member", member);
					return new ResponseEntity<Member>(member, HttpStatus.OK);
				} else {
					session.setAttribute("Member", null);
					logger.error("Member with password {} not found.", password);
					return new ResponseEntity(logger, HttpStatus.NOT_FOUND);
				}
			} else {
				logger.error("Member with password {} not found.", pseudonym);
				return new ResponseEntity(logger, HttpStatus.NOT_FOUND);

			}
		} catch (Exception ex) {
			logger.error("Member with pseudonym {} not found.", pseudonym);
			return new ResponseEntity(logger, HttpStatus.NOT_FOUND);
		}

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
	@RequestMapping(value = "/addTestimony", method = RequestMethod.POST)
	public ResponseEntity<?> addTestimonyPost(HttpServletRequest requestTestimony, UriComponentsBuilder ucBuilder)
			throws IOException, ServletException {
		HttpSession session = requestTestimony.getSession();
		Member member = (Member) session.getAttribute("Member");

		Testimony testimony = new Testimony();
		// String resultTestimony;

		String testimonyType = requestTestimony.getParameter("testimonyType");
		// String author=requestTestimony.getParameter("author");

		if (testimonyType.equalsIgnoreCase("videos")) {

			File fileWay = new File(SAVE_DIR_TESTIMONY);
			String name = "testimony";
			Part part = null;
			if (!fileWay.exists())
				fileWay.mkdir();

			part = requestTestimony.getPart("testimony");

			try {

				String fileName = SAVE_DIR_TESTIMONY + File.separator + name;
				part.write(SAVE_DIR_TESTIMONY + File.separator + name);

				testimony.setTestimonyType(testimonyType);
				testimony.setTestimonyContent(fileName);
				testimony.setAuthor(member.getPseudonym());
				// member=memberRepository.findByPseudonym(author);
				// testimony.setAuthor(member.getPseudonym());
				testimonyRepository.insert(testimony);
				// List<Testimony> testimonydb=
				// testimonyRepository.findByTestimonyType("videos");
				// List<Testimony> tes = new ArrayList<Testimony>();
				// tes.add(testimonydb);
				// member.setTestimonies(testimonydb);
				member.getTestimonies().add(testimony);
				memberRepository.save(member);

				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(
						ucBuilder.path("/Member/addTestimony/{id}").buildAndExpand(testimony.getId()).toUri());

				return new ResponseEntity<String>(headers, HttpStatus.CREATED);

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Unable to create. A User with name {} already exist", testimony.getTestimonyContent());
				return new ResponseEntity(logger, HttpStatus.CONFLICT);
			}
		} else {
			String testimonyContent = requestTestimony.getParameter("testimonyContent");
			try {

				testimony.setTestimonyType(testimonyType);
				testimony.setTestimonyContent(testimonyContent);
				testimony.setAuthor(member.getPseudonym());
				testimonyRepository.insert(testimony);
				member.getTestimonies().add(testimony);

				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(
						ucBuilder.path("/Member/addTestimony/{id}").buildAndExpand(testimony.getId()).toUri());

				return new ResponseEntity<String>(headers, HttpStatus.CREATED);

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Unable to create. A User with name {} already exist", testimony.getTestimonyContent());
				return new ResponseEntity(logger, HttpStatus.CONFLICT);

			}
		}

	}

	/*
	 * Version Get
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTestimony", method = RequestMethod.GET)
	public ResponseEntity<?> addTestimonyGet(HttpServletRequest requestTestimony, UriComponentsBuilder ucBuilder)
			throws IOException, ServletException {

		HttpSession session = requestTestimony.getSession();
		Member member = (Member) session.getAttribute("Member");

		Testimony testimony = new Testimony();
		// String resultTestimony;

		String testimonyType = requestTestimony.getParameter("testimonyType");
		String author = requestTestimony.getParameter("author");

		if (testimonyType.equalsIgnoreCase("videos")) {

			File fileWay = new File(SAVE_DIR_TESTIMONY);
			String name = "testimony";
			Part part = null;
			if (!fileWay.exists())
				fileWay.mkdir();

			part = requestTestimony.getPart("testimony");

			try {

				String fileName = SAVE_DIR_TESTIMONY + File.separator + name;
				part.write(SAVE_DIR_TESTIMONY + File.separator + name);

				testimony.setTestimonyType(testimonyType);
				testimony.setTestimonyContent(fileName);
				testimony.setAuthor(member.getPseudonym());
				// member=memberRepository.findByPseudonym(author);
				// testimony.setAuthor(member.getPseudonym());
				testimonyRepository.insert(testimony);
				// List<Testimony> testimonydb=
				// testimonyRepository.findByTestimonyType("videos");
				// List<Testimony> tes = new ArrayList<Testimony>();
				// tes.add(testimonydb);
				// member.setTestimonies(testimonydb);
				member.getTestimonies().add(testimony);
				memberRepository.save(member);

				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(
						ucBuilder.path("/Member/addTestimony/{id}").buildAndExpand(testimony.getId()).toUri());

				return new ResponseEntity<String>(headers, HttpStatus.CREATED);

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Unable to create. A User with name {} already exist", testimony.getTestimonyContent());
				return new ResponseEntity(logger, HttpStatus.CONFLICT);
			}
		} else {
			String testimonyContent = requestTestimony.getParameter("testimonyContent");
			try {

				testimony.setTestimonyType(testimonyType);
				testimony.setTestimonyContent(testimonyContent);
				testimony.setAuthor(member.getPseudonym());
				testimonyRepository.insert(testimony);
				member.getTestimonies().add(testimony);

				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(
						ucBuilder.path("/Member/addTestimony/{id}").buildAndExpand(testimony.getId()).toUri());

				return new ResponseEntity<String>(headers, HttpStatus.CREATED);

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Unable to create. A User with name {} already exist", testimony.getTestimonyContent());
				return new ResponseEntity(logger, HttpStatus.CONFLICT);

			}
		}

	}
	/*
	 * end add testimony
	 */

	/**
	 * Start paiement frais d'abonnement
	 */

}
