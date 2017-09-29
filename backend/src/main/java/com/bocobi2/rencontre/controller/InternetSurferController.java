
package com.bocobi2.rencontre.controller;

import java.io.File;
import java.util.ArrayList;
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

import com.bocobi2.rencontre.model.AcademicDatingInformation;
import com.bocobi2.rencontre.model.Borough;
import com.bocobi2.rencontre.model.ChooseMeeting;
import com.bocobi2.rencontre.model.ComeLocality;
import com.bocobi2.rencontre.model.Concession;
import com.bocobi2.rencontre.model.Country;
import com.bocobi2.rencontre.model.DatingInformation;
import com.bocobi2.rencontre.model.Department;
import com.bocobi2.rencontre.model.FriendlyDatingInformation;
import com.bocobi2.rencontre.model.Locality;
import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.MemberBuffer;
import com.bocobi2.rencontre.model.MemberErrorType;
import com.bocobi2.rencontre.model.ProfessionalMeetingInformation;
import com.bocobi2.rencontre.model.Region;
import com.bocobi2.rencontre.model.Status;
import com.bocobi2.rencontre.model.Testimony;
import com.bocobi2.rencontre.model.Town;
import com.bocobi2.rencontre.model.TypeMeeting;
import com.bocobi2.rencontre.repositories.BoroughRepository;
import com.bocobi2.rencontre.repositories.ChooseMeetingRepository;
import com.bocobi2.rencontre.repositories.ComeLocalityRepository;
import com.bocobi2.rencontre.repositories.ConcessionRepository;
import com.bocobi2.rencontre.repositories.CountryRepository;
import com.bocobi2.rencontre.repositories.DepartmentRepository;
import com.bocobi2.rencontre.repositories.LocalityRepository;
import com.bocobi2.rencontre.repositories.MemberBufferRepository;
import com.bocobi2.rencontre.repositories.MemberRepository;
import com.bocobi2.rencontre.repositories.RegionRepository;
import com.bocobi2.rencontre.repositories.StatusRepository;
import com.bocobi2.rencontre.repositories.TestimonyRepository;
import com.bocobi2.rencontre.repositories.TownRepository;
import com.bocobi2.rencontre.repositories.TypeMeetingRepository;

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

	@Autowired
	TypeMeetingRepository typeMeetingRepository;

	@Autowired
	ChooseMeetingRepository chooseMeetingRepository;
	
	@Autowired
	CountryRepository countryRepository;
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@Autowired
	BoroughRepository boroughRepository;

	@Autowired
	TownRepository townRepository;
	
	@Autowired
	ConcessionRepository concessionRepository;
	
	@Autowired
	LocalityRepository localityRepository;
	
	@Autowired
	ComeLocalityRepository comeLocalityRepository;

	/**
	 * choix du type de rencontre
	 */
	@SuppressWarnings("unchecked")
	/**
	 * VERSION POST
	 * 
	 * @param request
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value = "/chooseMeeting", method = RequestMethod.POST)
	public ResponseEntity<MemberBuffer> choiseRencontrePost(HttpServletRequest request,
			UriComponentsBuilder ucBuilder) {

		String birthDate = request.getParameter("bithDate");
		String gender = request.getParameter("gender");
		String meetingName = request.getParameter("meetingName");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int birthYear = 0;
		try {
			// birthYear = new Integer(birthDate);
			// int age = year - birthYear;
			// if (age < 18) {
			MemberBuffer memberBuffer = new MemberBuffer();
			TypeMeeting typeMeeting = typeMeetingRepository.findByMeetingName(meetingName);
			List<TypeMeeting> listTypeMeeting = new ArrayList<TypeMeeting>();
			listTypeMeeting.add(typeMeeting);
			memberBuffer.setBirthDate(birthDate);
			memberBuffer.setGender(gender);
			memberBuffer.setTypeMeeting(listTypeMeeting);

			return new ResponseEntity<MemberBuffer>(memberBuffer, HttpStatus.CONTINUE);

		} catch (Exception ex) {
			return new ResponseEntity(new MemberErrorType(ex.getMessage()), HttpStatus.BAD_REQUEST);

		}

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

		/*
		 * recuperation des donnees du formulaire
		 */
		String meetingName = request.getParameter("meetingName");
		String pseudonym = request.getParameter("pseudonym");
		String birthDate = request.getParameter("bithDate");
		String gender = request.getParameter("gender");
		String emailAdress = request.getParameter("emailAdress");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");
		

		// File fileWay = new File(SAVE_DIR_PICTURE);
		// String nom = "picture" + pseudonym + ".png";
		// Part part = null;
		// if (!fileWay.exists())
		// fileWay.mkdir();

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

		if (meetingName.equals("Amoureuse")) {

			String fatherName = request.getParameter("fatherName");
			String motherName = request.getParameter("motherName");
			String countryName= request.getParameter("countryName");
			String regionName = request.getParameter("regionName");
			String departmentName= request.getParameter("departmentName");
			String boroughName =request.getParameter("boroughName");
			String townName= request.getParameter("townName");
			String concessionName= request .getParameter("concessionName");
			
			
			
			Country countryDB = countryRepository.findByCountryName(countryName);
			Region regionDB =regionRepository.findByRegionName(regionName);
			Department departmentDB = departmentRepository.findByDepartmentName(departmentName);
			Borough boroughDB =boroughRepository.findByBoroughName(boroughName);
			//Town town = townRepository.findByTownName(townName);
			//Concession concession= concessionRepository.findByConcessionName(concessionName);
			
			
			Town town =new Town();
			Concession concession = new Concession();
			
			try {
				town.setTownName(townName);
				town.setBorough(boroughDB);
				
				townRepository.save(town);
				
			
			} catch (Exception ex) {
				logger.error("Unable to create. A Town with name {} already exist", townName);
				return new ResponseEntity(
						new MemberErrorType(
								"Unable to create. " + "A Town with name " + "" + townName + " already exist"),
						HttpStatus.CONFLICT);
			}
			
			Town townDB = townRepository.findByTownName(townName);
			
			try {
				concession.setConcessionName(concessionName);
				concession.setTown(townDB);
				
				concessionRepository.save(concession);
				
				
			} catch (Exception ex) {
				logger.error("Unable to create. A Concession with name {} already exist", concessionName);
				return new ResponseEntity(
						new MemberErrorType(
								"Unable to create. " + "A Concession with name " + "" + concessionName + " already exist"),
						HttpStatus.CONFLICT);
			}
			Concession concessionDB = concessionRepository.findByConcessionName(concessionName);
			
			String idLocality=countryName+regionName+departmentName+boroughName+townName+concessionName;
			
			Locality locality =new Locality();
			
			locality.setIdLocalite(idLocality);
			locality.setCountry(countryDB);
			locality.setRegion(regionDB);
			locality.setDepartment(departmentDB);
			locality.setBorough(boroughDB);
			locality.setTown(townDB);
			locality.setConcession(concessionDB);
			
			localityRepository.save(locality);
			
			Locality localityDB= localityRepository.findByIdLocalite(idLocality);
			
			String idLocalityDB= localityDB.getIdLocalite();
			
			
			
			ChooseMeeting chooseMeeting = new ChooseMeeting();
			MemberBuffer member = new MemberBuffer();
			DatingInformation datingInformation = new DatingInformation();

			TypeMeeting typeMeeting = typeMeetingRepository.findByMeetingName(meetingName);

			String idTypeMeeting = typeMeeting.getId();
			String idChoose = pseudonym + idTypeMeeting;

			try {
				// part = request.getPart("picture");
				// String fileName = SAVE_DIR_PICTURE + File.separator + nom;
				// part.write(SAVE_DIR_PICTURE + File.separator + nom);

				if (memberRepository.findByPseudonym(pseudonym) != null) {

					// ChooseMeeting chooseMeetingBd
					// =chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);
						chooseMeeting.setIdChooseMeeting(idChoose);
						chooseMeetingRepository.save(chooseMeeting);

						datingInformation.setFatherName(fatherName);
						datingInformation.setMotherName(motherName);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setEmailAdress(memberBD.getEmailAdress());
						member.setPhoneNumber(memberBD.getPhoneNumber());
						member.setPassword(memberBD.getPassword());
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(memberBD.getFriendlyDatingInformatio());
						member.setAcademicDatingInformation(memberBD.getAcademicDatingInformation());
						member.setProfessionalMeetingInformation(memberBD.getProfessionalMeetingInformation());

						member.setDatingInformation(datingInformation);

						try {
							

							String idComeLocality=pseudonym+idLocality;
							
							if(comeLocalityRepository.exists(idComeLocality)){
								
								return new ResponseEntity(
										new MemberErrorType("this locality is already exist"),
										HttpStatus.NOT_FOUND);
							}
							// enregistrement dans la zone tampon

							String content1 = "Thanks to create your count in our website \n"
									+ " Now,lick on this link to activate E-mail adress: "
									+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
									+ member.getPseudonym() + "&meetingName=" + meetingName;
							String subject1 = "confirm your E-mail adress";
							// String form="saphirmfogo@gmail.com";V
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

							// memberBufferRepository.deleteAll();
							memberBufferRepository.save(member);
							
							ComeLocality comeLocality = new ComeLocality();
							
							comeLocality.setId(idComeLocality);
							
							comeLocalityRepository.save(comeLocality);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("the email is not validate"),
									HttpStatus.NOT_FOUND);

						}
					}
				} else {

					// ChooseMeeting chooseMeetingBd
					// =chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

						return new ResponseEntity(
								new MemberErrorType("this pseudonym is already using, please choose an another own"),
								HttpStatus.NOT_FOUND);
					} else {
						// Member memberBD =
						// memberRepository.findByPseudonym(pseudonym);
						chooseMeeting.setIdChooseMeeting(idChoose);
						//chooseMeetingRepository.deleteAll();
						chooseMeetingRepository.save(chooseMeeting);

						datingInformation.setFatherName(fatherName);
						datingInformation.setMotherName(motherName);

						member.setPseudonym(pseudonym);
						member.setGender(gender);
						member.setBirthDate(birthDate);
						member.setEmailAdress(emailAdress);
						member.setPhoneNumber(phoneNumber);
						member.setPassword(password);
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(null);
						member.setAcademicDatingInformation(null);
						member.setProfessionalMeetingInformation(null);

						member.setDatingInformation(datingInformation);

						try {
String idComeLocality=pseudonym+idLocality;
							
							if(comeLocalityRepository.exists(idComeLocality)){
								
								return new ResponseEntity(
										new MemberErrorType("this locality is already exist"),
										HttpStatus.NOT_FOUND);
							}
							// enregistrement dans la zone tampon

							String content1 = "Thanks to create your count in our website \n"
									+ " Now,lick on this link to activate E-mail adress: "
									+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
									+ member.getPseudonym() + "&meetingName=" + meetingName;
							String subject1 = "confirm your E-mail adress";
							// String form="saphirmfogo@gmail.com";V
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

							// memberBufferRepository.deleteAll();
							memberBufferRepository.save(member);
							
							ComeLocality comeLocality = new ComeLocality();
							
							comeLocality.setId(idComeLocality);
							
							comeLocalityRepository.save(comeLocality);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("the email is not validate"),
									HttpStatus.NOT_FOUND);

						}
					}
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());

				logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
				return new ResponseEntity(new MemberErrorType("the email is not validate1"), HttpStatus.NOT_FOUND);

			}
			
			

		} else if (meetingName.equals("Professionnelle")) {

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String profession = request.getParameter("profession");
			String levelStudy = request.getParameter("levelStudy");

			ChooseMeeting chooseMeeting = new ChooseMeeting();
			MemberBuffer member = new MemberBuffer();
			ProfessionalMeetingInformation professionalMeeting = new ProfessionalMeetingInformation();

			TypeMeeting typeMeeting = typeMeetingRepository.findByMeetingName(meetingName);

			String idTypeMeeting = typeMeeting.getId();
			String idChoose = pseudonym + idTypeMeeting;

			try {
				// part = request.getPart("picture");
				// String fileName = SAVE_DIR_PICTURE + File.separator + nom;
				// part.write(SAVE_DIR_PICTURE + File.separator + nom);

				// Member memberBD =
				// memberRepository.findByPseudonym(pseudonym);

				if (memberRepository.findByPseudonym(pseudonym) != null) {

					ChooseMeeting chooseMeetingBd = chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingBd != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {

						Member memberBD = memberRepository.findByPseudonym(pseudonym);
						chooseMeeting.setIdChooseMeeting(idChoose);
						chooseMeetingRepository.save(chooseMeeting);

						professionalMeeting.setFirstName(firstName);
						professionalMeeting.setLastName(lastName);
						professionalMeeting.setLevelStudy(levelStudy);
						professionalMeeting.setProfession(profession);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setEmailAdress(memberBD.getEmailAdress());
						member.setPhoneNumber(memberBD.getPhoneNumber());
						member.setPassword(memberBD.getPassword());
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(memberBD.getFriendlyDatingInformatio());
						member.setAcademicDatingInformation(memberBD.getAcademicDatingInformation());
						member.setDatingInformation(memberBD.getDatingInformation());

						member.setProfessionalMeetingInformation(professionalMeeting);

						try {
							// enregistrement dans la zone tampon

							String content1 = "Thanks to create your count in our website \n"
									+ " Now,lick on this link to activate E-mail adress: "
									+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
									+ member.getPseudonym() + "&meetingName=" + meetingName;
							String subject1 = "confirm your E-mail adress";
							// String form="saphirmfogo@gmail.com";V
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

							// memberBufferRepository.deleteAll();
							memberBufferRepository.save(member);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("the email is not validate"),
									HttpStatus.NOT_FOUND);

						}
					}
				} else {

					try {

						// ChooseMeeting chooseMeetingBd
						// =chooseMeetingRepository.findByIdChooseMeeting(idChoose);
						if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

							return new ResponseEntity(
									new MemberErrorType(
											"this pseudonym is already using, please choose an another own"),
									HttpStatus.NOT_FOUND);
						} else {
							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);

							professionalMeeting.setFirstName(firstName);
							professionalMeeting.setLastName(lastName);
							professionalMeeting.setLevelStudy(levelStudy);
							professionalMeeting.setProfession(profession);

							member.setPseudonym(pseudonym);
							member.setGender(gender);
							member.setBirthDate(birthDate);
							member.setEmailAdress(emailAdress);
							member.setPhoneNumber(phoneNumber);
							member.setPassword(password);
							// member.setPicture(fileName);
							member.setFriendlyDatingInformatio(null);
							member.setAcademicDatingInformation(null);
							member.setDatingInformation(null);

							member.setProfessionalMeetingInformation(professionalMeeting);

							try {
								// enregistrement dans la zone tampon

								String content1 = "Thanks to create your count in our website \n"
										+ " Now,lick on this link to activate E-mail adress: "
										+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
										+ member.getPseudonym() + "&meetingName=" + meetingName;
								String subject1 = "confirm your E-mail adress";
								// String form="saphirmfogo@gmail.com";V
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

								// memberBufferRepository.deleteAll();
								memberBufferRepository.save(member);

								return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
							} catch (Exception ex) {
								System.out.println(ex.getMessage());

								logger.error("Unable to create. A Member with name {} already exist",
										member.getPseudonym());
								return new ResponseEntity(new MemberErrorType("the email is not validate"),
										HttpStatus.NOT_FOUND);

							}

						}
					} catch (Exception ex) {
						System.out.println(ex.getMessage());

						logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
						return new ResponseEntity(new MemberErrorType("the email is not validate"),
								HttpStatus.NOT_FOUND);

					}
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());

				logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
				return new ResponseEntity(new MemberErrorType("the email is not validate"), HttpStatus.NOT_FOUND);

			}

		} else if (meetingName.equals("Academique")) {

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String schoolName = request.getParameter("schoolName");
			String levelStudy = request.getParameter("levelStudy");

			ChooseMeeting chooseMeeting = new ChooseMeeting();
			MemberBuffer member = new MemberBuffer();
			AcademicDatingInformation academicDatingInformation = new AcademicDatingInformation();

			TypeMeeting typeMeeting = typeMeetingRepository.findByMeetingName(meetingName);

			String idTypeMeeting = typeMeeting.getId();
			String idChoose = pseudonym + idTypeMeeting;

			try {
				// part = request.getPart("picture");
				// String fileName = SAVE_DIR_PICTURE + File.separator + nom;
				// part.write(SAVE_DIR_PICTURE + File.separator + nom);

				// Member memberBD =
				// memberRepository.findByPseudonym(pseudonym);

				if (memberRepository.findByPseudonym(pseudonym) != null) {

					ChooseMeeting chooseMeetingBd = chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingBd != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);
						chooseMeeting.setIdChooseMeeting(idChoose);
						chooseMeetingRepository.save(chooseMeeting);

						academicDatingInformation.setFirstName(firstName);
						academicDatingInformation.setLastName(lastName);
						academicDatingInformation.setLevelStudy(levelStudy);
						academicDatingInformation.setSchoolName(schoolName);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setEmailAdress(memberBD.getEmailAdress());
						member.setPhoneNumber(memberBD.getPhoneNumber());
						member.setPassword(memberBD.getPassword());
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(memberBD.getFriendlyDatingInformatio());
						member.setProfessionalMeetingInformation(memberBD.getProfessionalMeetingInformation());
						member.setDatingInformation(memberBD.getDatingInformation());

						member.setAcademicDatingInformation(academicDatingInformation);

						try {
							// enregistrement dans la zone tampon

							String content1 = "Thanks to create your count in our website \n"
									+ " Now,lick on this link to activate E-mail adress: "
									+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
									+ member.getPseudonym() + "&meetingName=" + meetingName;
							String subject1 = "confirm your E-mail adress";
							// String form="saphirmfogo@gmail.com";V
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

							// memberBufferRepository.deleteAll();
							memberBufferRepository.save(member);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("the email is not validate"),
									HttpStatus.NOT_FOUND);

						}
					}
				} else {

					try {

						// ChooseMeeting chooseMeetingBd
						// =chooseMeetingRepository.findByIdChooseMeeting(idChoose);
						if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

							return new ResponseEntity(
									new MemberErrorType(
											"this pseudonym is already using, please choose an another own"),
									HttpStatus.NOT_FOUND);
						} else {
							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);

							academicDatingInformation.setFirstName(firstName);
							academicDatingInformation.setLastName(lastName);
							academicDatingInformation.setLevelStudy(levelStudy);
							academicDatingInformation.setSchoolName(schoolName);

							member.setPseudonym(pseudonym);
							member.setGender(gender);
							member.setBirthDate(birthDate);
							member.setEmailAdress(emailAdress);
							member.setPhoneNumber(phoneNumber);
							member.setPassword(password);
							// member.setPicture(fileName);
							member.setFriendlyDatingInformatio(null);
							member.setProfessionalMeetingInformation(null);
							member.setDatingInformation(null);

							member.setAcademicDatingInformation(academicDatingInformation);

							try {
								// enregistrement dans la zone tampon

								String content1 = "Thanks to create your count in our website \n"
										+ " Now,lick on this link to activate E-mail adress: "
										+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
										+ member.getPseudonym() + "&meetingName=" + meetingName;
								String subject1 = "confirm your E-mail adress";
								// String form="saphirmfogo@gmail.com";V
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

								// memberBufferRepository.deleteAll();
								memberBufferRepository.save(member);

								return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
							} catch (Exception ex) {
								System.out.println(ex.getMessage());

								logger.error("Unable to create. A Member with name {} already exist",
										member.getPseudonym());
								return new ResponseEntity(new MemberErrorType("the email is not validate"),
										HttpStatus.NOT_FOUND);

							}

						}
					} catch (Exception ex) {
						System.out.println(ex.getMessage());

						logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
						return new ResponseEntity(new MemberErrorType("the email is not validate"),
								HttpStatus.NOT_FOUND);

					}
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());

				logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
				return new ResponseEntity(new MemberErrorType("the email is not validate"), HttpStatus.NOT_FOUND);

			}

		} else if (meetingName.equals("Amicale")) {

			String name = request.getParameter("name");

			ChooseMeeting chooseMeeting = new ChooseMeeting();
			MemberBuffer member = new MemberBuffer();
			FriendlyDatingInformation friendlyDatingInformation = new FriendlyDatingInformation();

			TypeMeeting typeMeeting = typeMeetingRepository.findByMeetingName(meetingName);

			String idTypeMeeting = typeMeeting.getId();
			String idChoose = pseudonym + idTypeMeeting;

			try {
				// part = request.getPart("picture");
				// String fileName = SAVE_DIR_PICTURE + File.separator + nom;
				// part.write(SAVE_DIR_PICTURE + File.separator + nom);

				// Member memberBD =
				// memberRepository.findByPseudonym(pseudonym);

				if (memberRepository.findByPseudonym(pseudonym) != null) {

					ChooseMeeting chooseMeetingBd = chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingBd != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);
						chooseMeeting.setIdChooseMeeting(idChoose);
						chooseMeetingRepository.save(chooseMeeting);

						friendlyDatingInformation.setName(name);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setEmailAdress(memberBD.getEmailAdress());
						member.setPhoneNumber(memberBD.getPhoneNumber());
						member.setPassword(memberBD.getPassword());
						// member.setPicture(fileName);
						member.setAcademicDatingInformation(memberBD.getAcademicDatingInformation());
						member.setProfessionalMeetingInformation(memberBD.getProfessionalMeetingInformation());
						member.setDatingInformation(memberBD.getDatingInformation());

						member.setFriendlyDatingInformatio(friendlyDatingInformation);

						try {
							// enregistrement dans la zone tampon

							String content1 = "Thanks to create your count in our website \n"
									+ " Now,lick on this link to activate E-mail adress: "
									+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
									+ member.getPseudonym() + "&meetingName=" + meetingName;
							String subject1 = "confirm your E-mail adress";
							// String form="saphirmfogo@gmail.com";V
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

							// memberBufferRepository.deleteAll();
							memberBufferRepository.save(member);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("the email is not validate"),
									HttpStatus.NOT_FOUND);

						}
					}
				} else {

					try {

						// ChooseMeeting chooseMeetingBd
						// =chooseMeetingRepository.findByIdChooseMeeting(idChoose);
						if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

							return new ResponseEntity(
									new MemberErrorType(
											"this pseudonym is already using, please choose an another own"),
									HttpStatus.NOT_FOUND);
						} else {
							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);

							friendlyDatingInformation.setName(name);

							member.setPseudonym(pseudonym);
							member.setGender(gender);
							member.setBirthDate(birthDate);
							member.setEmailAdress(emailAdress);
							member.setPhoneNumber(phoneNumber);
							member.setPassword(password);
							// member.setPicture(fileName);
							member.setAcademicDatingInformation(null);
							member.setProfessionalMeetingInformation(null);
							member.setDatingInformation(null);

							member.setFriendlyDatingInformatio(friendlyDatingInformation);

							try {
								// enregistrement dans la zone tampon

								String content1 = "Thanks to create your count in our website \n"
										+ " Now,lick on this link to activate E-mail adress: "
										+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
										+ member.getPseudonym() + "&meetingName=" + meetingName;
								String subject1 = "confirm your E-mail adress";
								// String form="saphirmfogo@gmail.com";V
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

								// memberBufferRepository.deleteAll();
								memberBufferRepository.save(member);

								return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
							} catch (Exception ex) {
								System.out.println(ex.getMessage());

								logger.error("Unable to create. A Member with name {} already exist",
										member.getPseudonym());
								return new ResponseEntity(new MemberErrorType("the email is not validate"),
										HttpStatus.NOT_FOUND);

							}

						}
					} catch (Exception ex) {
						System.out.println(ex.getMessage());

						logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
						return new ResponseEntity(new MemberErrorType("the email is not validate"),
								HttpStatus.NOT_FOUND);

					}
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());

				logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
				return new ResponseEntity(new MemberErrorType("the email is not validate"), HttpStatus.NOT_FOUND);

			}

		}
		return new ResponseEntity(new MemberErrorType("the type of meeting is not available"), HttpStatus.NOT_FOUND);
	}

	@SuppressWarnings("unchecked")
	/*
	 * Version Get
	 */
	@ResponseBody
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ResponseEntity<?> registrationGet(HttpServletRequest request, UriComponentsBuilder ucBuilder) {

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

		/*
		 * recuperation des donnees du formulaire
		 */
		String meetingName = request.getParameter("meetingName");
		String pseudonym = request.getParameter("pseudonym");
		String birthDate = request.getParameter("bithDate");
		String gender = request.getParameter("gender");
		String emailAdress = request.getParameter("emailAdress");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");

		// File fileWay = new File(SAVE_DIR_PICTURE);
		// String nom = "picture" + pseudonym + ".png";
		// Part part = null;
		// if (!fileWay.exists())
		// fileWay.mkdir();

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

		if (meetingName.equals("Amoureuse")) {

			String fatherName = request.getParameter("fatherName");
			String motherName = request.getParameter("motherName");

			ChooseMeeting chooseMeeting = new ChooseMeeting();
			MemberBuffer member = new MemberBuffer();
			DatingInformation datingInformation = new DatingInformation();

			TypeMeeting typeMeeting = typeMeetingRepository.findByMeetingName(meetingName);

			String idTypeMeeting = typeMeeting.getId();
			String idChoose = pseudonym + idTypeMeeting;

			try {
				// part = request.getPart("picture");
				// String fileName = SAVE_DIR_PICTURE + File.separator + nom;
				// part.write(SAVE_DIR_PICTURE + File.separator + nom);

				if (memberRepository.findByPseudonym(pseudonym) != null) {

					// ChooseMeeting chooseMeetingBd
					// =chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);
						chooseMeeting.setIdChooseMeeting(idChoose);
						chooseMeetingRepository.save(chooseMeeting);

						datingInformation.setFatherName(fatherName);
						datingInformation.setMotherName(motherName);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setEmailAdress(memberBD.getEmailAdress());
						member.setPhoneNumber(memberBD.getPhoneNumber());
						member.setPassword(memberBD.getPassword());
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(memberBD.getFriendlyDatingInformatio());
						member.setAcademicDatingInformation(memberBD.getAcademicDatingInformation());
						member.setProfessionalMeetingInformation(memberBD.getProfessionalMeetingInformation());

						member.setDatingInformation(datingInformation);

						try {
							// enregistrement dans la zone tampon

							String content1 = "Thanks to create your count in our website \n"
									+ " Now,lick on this link to activate E-mail adress: "
									+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
									+ member.getPseudonym() + "&meetingName=" + meetingName;
							String subject1 = "confirm your E-mail adress";
							// String form="saphirmfogo@gmail.com";V
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

							// memberBufferRepository.deleteAll();
							memberBufferRepository.save(member);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("the email is not validate"),
									HttpStatus.NOT_FOUND);

						}
					}
				} else {

					// ChooseMeeting chooseMeetingBd
					// =chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

						return new ResponseEntity(
								new MemberErrorType("this pseudonym is already using, please choose an another own"),
								HttpStatus.NOT_FOUND);
					} else {
						// Member memberBD =
						// memberRepository.findByPseudonym(pseudonym);
						chooseMeeting.setIdChooseMeeting(idChoose);
						chooseMeetingRepository.deleteAll();
						chooseMeetingRepository.save(chooseMeeting);

						datingInformation.setFatherName(fatherName);
						datingInformation.setMotherName(motherName);

						member.setPseudonym(pseudonym);
						member.setGender(gender);
						member.setBirthDate(birthDate);
						member.setEmailAdress(emailAdress);
						member.setPhoneNumber(phoneNumber);
						member.setPassword(password);
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(null);
						member.setAcademicDatingInformation(null);
						member.setProfessionalMeetingInformation(null);

						member.setDatingInformation(datingInformation);

						try {
							// enregistrement dans la zone tampon

							String content1 = "Thanks to create your count in our website \n"
									+ " Now,lick on this link to activate E-mail adress: "
									+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
									+ member.getPseudonym() + "&meetingName=" + meetingName;
							String subject1 = "confirm your E-mail adress";
							// String form="saphirmfogo@gmail.com";V
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

							// memberBufferRepository.deleteAll();
							memberBufferRepository.save(member);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("the email is not validate"),
									HttpStatus.NOT_FOUND);

						}
					}
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());

				logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
				return new ResponseEntity(new MemberErrorType("the email is not validate1"), HttpStatus.NOT_FOUND);

			}

		} else if (meetingName.equals("Professionnelle")) {

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String profession = request.getParameter("profession");
			String levelStudy = request.getParameter("levelStudy");

			ChooseMeeting chooseMeeting = new ChooseMeeting();
			MemberBuffer member = new MemberBuffer();
			ProfessionalMeetingInformation professionalMeeting = new ProfessionalMeetingInformation();

			TypeMeeting typeMeeting = typeMeetingRepository.findByMeetingName(meetingName);

			String idTypeMeeting = typeMeeting.getId();
			String idChoose = pseudonym + idTypeMeeting;

			try {
				// part = request.getPart("picture");
				// String fileName = SAVE_DIR_PICTURE + File.separator + nom;
				// part.write(SAVE_DIR_PICTURE + File.separator + nom);

				// Member memberBD =
				// memberRepository.findByPseudonym(pseudonym);

				if (memberRepository.findByPseudonym(pseudonym) != null) {

					ChooseMeeting chooseMeetingBd = chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingBd != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {

						Member memberBD = memberRepository.findByPseudonym(pseudonym);
						chooseMeeting.setIdChooseMeeting(idChoose);
						chooseMeetingRepository.save(chooseMeeting);

						professionalMeeting.setFirstName(firstName);
						professionalMeeting.setLastName(lastName);
						professionalMeeting.setLevelStudy(levelStudy);
						professionalMeeting.setProfession(profession);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setEmailAdress(memberBD.getEmailAdress());
						member.setPhoneNumber(memberBD.getPhoneNumber());
						member.setPassword(memberBD.getPassword());
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(memberBD.getFriendlyDatingInformatio());
						member.setAcademicDatingInformation(memberBD.getAcademicDatingInformation());
						member.setDatingInformation(memberBD.getDatingInformation());

						member.setProfessionalMeetingInformation(professionalMeeting);

						try {
							// enregistrement dans la zone tampon

							String content1 = "Thanks to create your count in our website \n"
									+ " Now,lick on this link to activate E-mail adress: "
									+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
									+ member.getPseudonym() + "&meetingName=" + meetingName;
							String subject1 = "confirm your E-mail adress";
							// String form="saphirmfogo@gmail.com";V
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

							// memberBufferRepository.deleteAll();
							memberBufferRepository.save(member);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("the email is not validate"),
									HttpStatus.NOT_FOUND);

						}
					}
				} else {

					try {

						// ChooseMeeting chooseMeetingBd
						// =chooseMeetingRepository.findByIdChooseMeeting(idChoose);
						if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

							return new ResponseEntity(
									new MemberErrorType(
											"this pseudonym is already using, please choose an another own"),
									HttpStatus.NOT_FOUND);
						} else {
							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);

							professionalMeeting.setFirstName(firstName);
							professionalMeeting.setLastName(lastName);
							professionalMeeting.setLevelStudy(levelStudy);
							professionalMeeting.setProfession(profession);

							member.setPseudonym(pseudonym);
							member.setGender(gender);
							member.setBirthDate(birthDate);
							member.setEmailAdress(emailAdress);
							member.setPhoneNumber(phoneNumber);
							member.setPassword(password);
							// member.setPicture(fileName);
							member.setFriendlyDatingInformatio(null);
							member.setAcademicDatingInformation(null);
							member.setDatingInformation(null);

							member.setProfessionalMeetingInformation(professionalMeeting);

							try {
								// enregistrement dans la zone tampon

								String content1 = "Thanks to create your count in our website \n"
										+ " Now,lick on this link to activate E-mail adress: "
										+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
										+ member.getPseudonym() + "&meetingName=" + meetingName;
								String subject1 = "confirm your E-mail adress";
								// String form="saphirmfogo@gmail.com";V
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

								// memberBufferRepository.deleteAll();
								memberBufferRepository.save(member);

								return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
							} catch (Exception ex) {
								System.out.println(ex.getMessage());

								logger.error("Unable to create. A Member with name {} already exist",
										member.getPseudonym());
								return new ResponseEntity(new MemberErrorType("the email is not validate"),
										HttpStatus.NOT_FOUND);

							}

						}
					} catch (Exception ex) {
						System.out.println(ex.getMessage());

						logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
						return new ResponseEntity(new MemberErrorType("the email is not validate"),
								HttpStatus.NOT_FOUND);

					}
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());

				logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
				return new ResponseEntity(new MemberErrorType("the email is not validate"), HttpStatus.NOT_FOUND);

			}

		} else if (meetingName.equals("Academique")) {

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String schoolName = request.getParameter("schoolName");
			String levelStudy = request.getParameter("levelStudy");

			ChooseMeeting chooseMeeting = new ChooseMeeting();
			MemberBuffer member = new MemberBuffer();
			AcademicDatingInformation academicDatingInformation = new AcademicDatingInformation();

			TypeMeeting typeMeeting = typeMeetingRepository.findByMeetingName(meetingName);

			String idTypeMeeting = typeMeeting.getId();
			String idChoose = pseudonym + idTypeMeeting;

			try {
				// part = request.getPart("picture");
				// String fileName = SAVE_DIR_PICTURE + File.separator + nom;
				// part.write(SAVE_DIR_PICTURE + File.separator + nom);

				// Member memberBD =
				// memberRepository.findByPseudonym(pseudonym);

				if (memberRepository.findByPseudonym(pseudonym) != null) {

					ChooseMeeting chooseMeetingBd = chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingBd != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);
						chooseMeeting.setIdChooseMeeting(idChoose);
						chooseMeetingRepository.save(chooseMeeting);

						academicDatingInformation.setFirstName(firstName);
						academicDatingInformation.setLastName(lastName);
						academicDatingInformation.setLevelStudy(levelStudy);
						academicDatingInformation.setSchoolName(schoolName);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setEmailAdress(memberBD.getEmailAdress());
						member.setPhoneNumber(memberBD.getPhoneNumber());
						member.setPassword(memberBD.getPassword());
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(memberBD.getFriendlyDatingInformatio());
						member.setProfessionalMeetingInformation(memberBD.getProfessionalMeetingInformation());
						member.setDatingInformation(memberBD.getDatingInformation());

						member.setAcademicDatingInformation(academicDatingInformation);

						try {
							// enregistrement dans la zone tampon

							String content1 = "Thanks to create your count in our website \n"
									+ " Now,lick on this link to activate E-mail adress: "
									+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
									+ member.getPseudonym() + "&meetingName=" + meetingName;
							String subject1 = "confirm your E-mail adress";
							// String form="saphirmfogo@gmail.com";V
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

							// memberBufferRepository.deleteAll();
							memberBufferRepository.save(member);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("the email is not validate"),
									HttpStatus.NOT_FOUND);

						}
					}
				} else {

					try {

						// ChooseMeeting chooseMeetingBd
						// =chooseMeetingRepository.findByIdChooseMeeting(idChoose);
						if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

							return new ResponseEntity(
									new MemberErrorType(
											"this pseudonym is already using, please choose an another own"),
									HttpStatus.NOT_FOUND);
						} else {
							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);

							academicDatingInformation.setFirstName(firstName);
							academicDatingInformation.setLastName(lastName);
							academicDatingInformation.setLevelStudy(levelStudy);
							academicDatingInformation.setSchoolName(schoolName);

							member.setPseudonym(pseudonym);
							member.setGender(gender);
							member.setBirthDate(birthDate);
							member.setEmailAdress(emailAdress);
							member.setPhoneNumber(phoneNumber);
							member.setPassword(password);
							// member.setPicture(fileName);
							member.setFriendlyDatingInformatio(null);
							member.setProfessionalMeetingInformation(null);
							member.setDatingInformation(null);

							member.setAcademicDatingInformation(academicDatingInformation);

							try {
								// enregistrement dans la zone tampon

								String content1 = "Thanks to create your count in our website \n"
										+ " Now,lick on this link to activate E-mail adress: "
										+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
										+ member.getPseudonym() + "&meetingName=" + meetingName;
								String subject1 = "confirm your E-mail adress";
								// String form="saphirmfogo@gmail.com";V
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

								// memberBufferRepository.deleteAll();
								memberBufferRepository.save(member);

								return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
							} catch (Exception ex) {
								System.out.println(ex.getMessage());

								logger.error("Unable to create. A Member with name {} already exist",
										member.getPseudonym());
								return new ResponseEntity(new MemberErrorType("the email is not validate"),
										HttpStatus.NOT_FOUND);

							}

						}
					} catch (Exception ex) {
						System.out.println(ex.getMessage());

						logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
						return new ResponseEntity(new MemberErrorType("the email is not validate"),
								HttpStatus.NOT_FOUND);

					}
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());

				logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
				return new ResponseEntity(new MemberErrorType("the email is not validate"), HttpStatus.NOT_FOUND);

			}

		} else if (meetingName.equals("Amicale")) {

			String name = request.getParameter("name");

			ChooseMeeting chooseMeeting = new ChooseMeeting();
			MemberBuffer member = new MemberBuffer();
			FriendlyDatingInformation friendlyDatingInformation = new FriendlyDatingInformation();

			TypeMeeting typeMeeting = typeMeetingRepository.findByMeetingName(meetingName);

			String idTypeMeeting = typeMeeting.getId();
			String idChoose = pseudonym + idTypeMeeting;

			try {
				// part = request.getPart("picture");
				// String fileName = SAVE_DIR_PICTURE + File.separator + nom;
				// part.write(SAVE_DIR_PICTURE + File.separator + nom);

				// Member memberBD =
				// memberRepository.findByPseudonym(pseudonym);

				if (memberRepository.findByPseudonym(pseudonym) != null) {

					ChooseMeeting chooseMeetingBd = chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingBd != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);
						chooseMeeting.setIdChooseMeeting(idChoose);
						chooseMeetingRepository.save(chooseMeeting);

						friendlyDatingInformation.setName(name);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setEmailAdress(memberBD.getEmailAdress());
						member.setPhoneNumber(memberBD.getPhoneNumber());
						member.setPassword(memberBD.getPassword());
						// member.setPicture(fileName);
						member.setAcademicDatingInformation(memberBD.getAcademicDatingInformation());
						member.setProfessionalMeetingInformation(memberBD.getProfessionalMeetingInformation());
						member.setDatingInformation(memberBD.getDatingInformation());

						member.setFriendlyDatingInformatio(friendlyDatingInformation);

						try {
							// enregistrement dans la zone tampon

							String content1 = "Thanks to create your count in our website \n"
									+ " Now,lick on this link to activate E-mail adress: "
									+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
									+ member.getPseudonym() + "&meetingName=" + meetingName;
							String subject1 = "confirm your E-mail adress";
							// String form="saphirmfogo@gmail.com";V
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

							// memberBufferRepository.deleteAll();
							memberBufferRepository.save(member);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("the email is not validate"),
									HttpStatus.NOT_FOUND);

						}
					}
				} else {

					try {

						// ChooseMeeting chooseMeetingBd
						// =chooseMeetingRepository.findByIdChooseMeeting(idChoose);
						if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

							return new ResponseEntity(
									new MemberErrorType(
											"this pseudonym is already using, please choose an another own"),
									HttpStatus.NOT_FOUND);
						} else {
							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);

							friendlyDatingInformation.setName(name);

							member.setPseudonym(pseudonym);
							member.setGender(gender);
							member.setBirthDate(birthDate);
							member.setEmailAdress(emailAdress);
							member.setPhoneNumber(phoneNumber);
							member.setPassword(password);
							// member.setPicture(fileName);
							member.setAcademicDatingInformation(null);
							member.setProfessionalMeetingInformation(null);
							member.setDatingInformation(null);

							member.setFriendlyDatingInformatio(friendlyDatingInformation);

							try {
								// enregistrement dans la zone tampon

								String content1 = "Thanks to create your count in our website \n"
										+ " Now,lick on this link to activate E-mail adress: "
										+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?user="
										+ member.getPseudonym() + "&meetingName=" + meetingName;
								String subject1 = "confirm your E-mail adress";
								// String form="saphirmfogo@gmail.com";V
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

								// memberBufferRepository.deleteAll();
								memberBufferRepository.save(member);

								return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
							} catch (Exception ex) {
								System.out.println(ex.getMessage());

								logger.error("Unable to create. A Member with name {} already exist",
										member.getPseudonym());
								return new ResponseEntity(new MemberErrorType("the email is not validate"),
										HttpStatus.NOT_FOUND);

							}

						}
					} catch (Exception ex) {
						System.out.println(ex.getMessage());

						logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
						return new ResponseEntity(new MemberErrorType("the email is not validate"),
								HttpStatus.NOT_FOUND);

					}
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());

				logger.error("Unable to create. A Member with name {} already exist", member.getPseudonym());
				return new ResponseEntity(new MemberErrorType("the email is not validate"), HttpStatus.NOT_FOUND);

			}

		}
		return new ResponseEntity(new MemberErrorType("the type of meeting is not available"), HttpStatus.NOT_FOUND);
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
		String meetingName = request.getParameter("meetingName");
		System.out.println(pseudonym);

		Status statusMember = statusRepository.findByStatusName("connected");

		Member member = new Member();

		MemberBuffer memberBuffer = memberBufferRepository.findByPseudonym(pseudonym);
		Member memberDB = memberRepository.findByPseudonym(pseudonym);

		if (memberDB != null) {

			if (meetingName.equals("Amoureuse")) {

				member.setPseudonym(memberBuffer.getPseudonym());
				member.setBirthDate(memberBuffer.getBirthDate());
				member.setEmailAdress(memberBuffer.getEmailAdress());
				member.setGender(memberBuffer.getGender());
				member.setPhoneNumber(memberBuffer.getPhoneNumber());
				member.setPassword(memberBuffer.getPassword());
				member.setPicture(memberBuffer.getPicture());
				member.setStatus(statusMember);
				member.setDatingInformation(memberBuffer.getDatingInformation());
				member.setAcademicDatingInformation(memberDB.getAcademicDatingInformation());
				member.setProfessionalMeetingInformation(memberDB.getProfessionalMeetingInformation());
				member.setFriendlyDatingInformatio(memberDB.getFriendlyDatingInformatio());

				// memberRepository.deleteAll();
				memberRepository.save(member);
				memberBufferRepository.delete(memberBuffer);

				return new ResponseEntity<Member>(member, HttpStatus.OK);

			} else if (meetingName.equals("Professionnelle")) {

				member.setPseudonym(memberBuffer.getPseudonym());
				member.setBirthDate(memberBuffer.getBirthDate());
				member.setEmailAdress(memberBuffer.getEmailAdress());
				member.setGender(memberBuffer.getGender());
				member.setPhoneNumber(memberBuffer.getPhoneNumber());
				member.setPassword(memberBuffer.getPassword());
				member.setPicture(memberBuffer.getPicture());
				member.setStatus(statusMember);
				member.setDatingInformation(memberDB.getDatingInformation());
				member.setAcademicDatingInformation(memberDB.getAcademicDatingInformation());
				member.setProfessionalMeetingInformation(memberBuffer.getProfessionalMeetingInformation());
				member.setFriendlyDatingInformatio(memberDB.getFriendlyDatingInformatio());

				// memberRepository.deleteAll();
				memberRepository.save(member);
				memberBufferRepository.delete(memberBuffer);
				return new ResponseEntity<Member>(member, HttpStatus.OK);

			} else if (meetingName.equals("Academique")) {

				member.setPseudonym(memberBuffer.getPseudonym());
				member.setBirthDate(memberBuffer.getBirthDate());
				member.setEmailAdress(memberBuffer.getEmailAdress());
				member.setGender(memberBuffer.getGender());
				member.setPhoneNumber(memberBuffer.getPhoneNumber());
				member.setPassword(memberBuffer.getPassword());
				member.setPicture(memberBuffer.getPicture());
				member.setStatus(statusMember);
				member.setDatingInformation(memberDB.getDatingInformation());
				member.setAcademicDatingInformation(memberBuffer.getAcademicDatingInformation());
				member.setProfessionalMeetingInformation(memberDB.getProfessionalMeetingInformation());
				member.setFriendlyDatingInformatio(memberDB.getFriendlyDatingInformatio());

				// memberRepository.deleteAll();
				memberRepository.save(member);
				memberBufferRepository.delete(memberBuffer);
				return new ResponseEntity<Member>(member, HttpStatus.OK);

			} else if (meetingName.equals("Amicale")) {

				member.setPseudonym(memberBuffer.getPseudonym());
				member.setBirthDate(memberBuffer.getBirthDate());
				member.setEmailAdress(memberBuffer.getEmailAdress());
				member.setGender(memberBuffer.getGender());
				member.setPhoneNumber(memberBuffer.getPhoneNumber());
				member.setPassword(memberBuffer.getPassword());
				member.setPicture(memberBuffer.getPicture());
				member.setStatus(statusMember);
				member.setDatingInformation(memberDB.getDatingInformation());
				member.setAcademicDatingInformation(memberDB.getAcademicDatingInformation());
				member.setProfessionalMeetingInformation(memberDB.getProfessionalMeetingInformation());
				member.setFriendlyDatingInformatio(memberBuffer.getFriendlyDatingInformatio());

				memberRepository.save(member);
				memberBufferRepository.delete(memberBuffer);
				return new ResponseEntity<Member>(member, HttpStatus.OK);

			}
		} else {
			member.setPseudonym(memberBuffer.getPseudonym());
			member.setBirthDate(memberBuffer.getBirthDate());
			member.setEmailAdress(memberBuffer.getEmailAdress());
			member.setGender(memberBuffer.getGender());
			member.setPhoneNumber(memberBuffer.getPhoneNumber());
			member.setPassword(memberBuffer.getPassword());
			member.setPicture(memberBuffer.getPicture());
			member.setStatus(statusMember);
			member.setDatingInformation(memberBuffer.getDatingInformation());
			member.setAcademicDatingInformation(memberBuffer.getAcademicDatingInformation());
			member.setProfessionalMeetingInformation(memberBuffer.getProfessionalMeetingInformation());
			member.setFriendlyDatingInformatio(memberBuffer.getFriendlyDatingInformatio());

			memberRepository.save(member);
			memberBufferRepository.delete(memberBuffer);
			return new ResponseEntity<Member>(member, HttpStatus.OK);

		}
		return null;

	}

	/*
	 * Version GET
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/confirmRegistration", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> confirmRegistrationGet(HttpServletRequest request) {

		String pseudonym = request.getParameter("user");
		String meetingName = request.getParameter("meetingName");
		System.out.println(pseudonym);

		Status statusMember = statusRepository.findByStatusName("connected");

		Member member = new Member();

		MemberBuffer memberBuffer = memberBufferRepository.findByPseudonym(pseudonym);
		Member memberDB = memberRepository.findByPseudonym(pseudonym);

		if (memberDB != null) {

			if (meetingName.equals("Amoureuse")) {

				member.setPseudonym(memberBuffer.getPseudonym());
				member.setBirthDate(memberBuffer.getBirthDate());
				member.setEmailAdress(memberBuffer.getEmailAdress());
				member.setGender(memberBuffer.getGender());
				member.setPhoneNumber(memberBuffer.getPhoneNumber());
				member.setPassword(memberBuffer.getPassword());
				member.setPicture(memberBuffer.getPicture());
				member.setStatus(statusMember);
				member.setDatingInformation(memberBuffer.getDatingInformation());
				member.setAcademicDatingInformation(memberDB.getAcademicDatingInformation());
				member.setProfessionalMeetingInformation(memberDB.getProfessionalMeetingInformation());
				member.setFriendlyDatingInformatio(memberDB.getFriendlyDatingInformatio());

				// memberRepository.deleteAll();
				memberRepository.save(member);
				memberBufferRepository.delete(memberBuffer);

				return new ResponseEntity<Member>(member, HttpStatus.OK);

			} else if (meetingName.equals("Professionnelle")) {

				member.setPseudonym(memberBuffer.getPseudonym());
				member.setBirthDate(memberBuffer.getBirthDate());
				member.setEmailAdress(memberBuffer.getEmailAdress());
				member.setGender(memberBuffer.getGender());
				member.setPhoneNumber(memberBuffer.getPhoneNumber());
				member.setPassword(memberBuffer.getPassword());
				member.setPicture(memberBuffer.getPicture());
				member.setStatus(statusMember);
				member.setDatingInformation(memberDB.getDatingInformation());
				member.setAcademicDatingInformation(memberDB.getAcademicDatingInformation());
				member.setProfessionalMeetingInformation(memberBuffer.getProfessionalMeetingInformation());
				member.setFriendlyDatingInformatio(memberDB.getFriendlyDatingInformatio());

				// memberRepository.deleteAll();
				memberRepository.save(member);
				memberBufferRepository.delete(memberBuffer);
				return new ResponseEntity<Member>(member, HttpStatus.OK);

			} else if (meetingName.equals("Academique")) {

				member.setPseudonym(memberBuffer.getPseudonym());
				member.setBirthDate(memberBuffer.getBirthDate());
				member.setEmailAdress(memberBuffer.getEmailAdress());
				member.setGender(memberBuffer.getGender());
				member.setPhoneNumber(memberBuffer.getPhoneNumber());
				member.setPassword(memberBuffer.getPassword());
				member.setPicture(memberBuffer.getPicture());
				member.setStatus(statusMember);
				member.setDatingInformation(memberDB.getDatingInformation());
				member.setAcademicDatingInformation(memberBuffer.getAcademicDatingInformation());
				member.setProfessionalMeetingInformation(memberDB.getProfessionalMeetingInformation());
				member.setFriendlyDatingInformatio(memberDB.getFriendlyDatingInformatio());

				// memberRepository.deleteAll();
				memberRepository.save(member);
				memberBufferRepository.delete(memberBuffer);
				return new ResponseEntity<Member>(member, HttpStatus.OK);

			} else if (meetingName.equals("Amicale")) {

				member.setPseudonym(memberBuffer.getPseudonym());
				member.setBirthDate(memberBuffer.getBirthDate());
				member.setEmailAdress(memberBuffer.getEmailAdress());
				member.setGender(memberBuffer.getGender());
				member.setPhoneNumber(memberBuffer.getPhoneNumber());
				member.setPassword(memberBuffer.getPassword());
				member.setPicture(memberBuffer.getPicture());
				member.setStatus(statusMember);
				member.setDatingInformation(memberDB.getDatingInformation());
				member.setAcademicDatingInformation(memberDB.getAcademicDatingInformation());
				member.setProfessionalMeetingInformation(memberDB.getProfessionalMeetingInformation());
				member.setFriendlyDatingInformatio(memberBuffer.getFriendlyDatingInformatio());

				memberRepository.save(member);
				memberBufferRepository.delete(memberBuffer);
				return new ResponseEntity<Member>(member, HttpStatus.OK);

			}
		} else {
			member.setPseudonym(memberBuffer.getPseudonym());
			member.setBirthDate(memberBuffer.getBirthDate());
			member.setEmailAdress(memberBuffer.getEmailAdress());
			member.setGender(memberBuffer.getGender());
			member.setPhoneNumber(memberBuffer.getPhoneNumber());
			member.setPassword(memberBuffer.getPassword());
			member.setPicture(memberBuffer.getPicture());
			member.setStatus(statusMember);
			member.setDatingInformation(memberBuffer.getDatingInformation());
			member.setAcademicDatingInformation(memberBuffer.getAcademicDatingInformation());
			member.setProfessionalMeetingInformation(memberBuffer.getProfessionalMeetingInformation());
			member.setFriendlyDatingInformatio(memberBuffer.getFriendlyDatingInformatio());

			memberRepository.save(member);
			memberBufferRepository.delete(memberBuffer);
			return new ResponseEntity<Member>(member, HttpStatus.OK);

		}
		return null;

	}	  /** Start visualize testimony
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
