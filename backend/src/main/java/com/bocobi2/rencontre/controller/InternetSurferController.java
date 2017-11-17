
package com.bocobi2.rencontre.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.bocobi2.rencontre.model.Cryptograph;
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
import com.bocobi2.rencontre.repositories.CryptographRepository;
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

	// private static final String SAVE_DIR_PICTURE =
	// "/home/saphir/test1/workspaceGit/rencontre/backend/src/main/resources/UploadFile/UploadPicture";

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

	@Autowired
	CryptographRepository cryptographRepository;

	/**
	 * Methode de cryptographie
	 */

	public static String encrypt(String ss) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

		// String ss = "Hello world, haris is here!";
		byte[] plainText = ss.getBytes();
		//
		// get a DES private key
		System.out.println("\nStart generating DES key");
		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		keyGen.init(56);
		Key key = keyGen.generateKey();
		System.out.println("Finish generating DES key");
		//
		// get a DES cipher object and print the provider
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		System.out.println("\n" + cipher.getProvider().getInfo());
		//
		// encrypt using the key and the plaintext
		System.out.println("\nStart encryption");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(plainText);
		System.out.println("Finish encryption: ");
		System.out.println(new String(cipherText, "UTF8"));
		return new String(cipherText, "UTF8");
	}

	public static String cryptographe(String name) {

		String crypte = "";
		for (int i = 0; i < name.length(); i++) {
			int c = name.charAt(i) ^ 48;
			char crypteC = (char) c;
			if (crypteC == '\\') {
				crypte = crypte + "\\" + crypteC;
			} else if (crypteC == '^') {
				crypteC = name.charAt(i);
				crypte = crypte + crypteC;
			}
			crypte = crypte + crypteC;
			// }

		}

		return crypte;
	}

	/*
	 * Methode decryptographe
	 */
	public static String decryptographe(String password) {
		String aCrypter = "";
		for (int i = 0; i < password.length(); i++) {
			int c = password.charAt(i) ^ 48;
			aCrypter = aCrypter + (char) c;
		}

		return aCrypter;
	}

	/****
	 * registration member in the data base methode qui gere l'enregistrement
	 * d'un membre dans la bd
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws ParseException 
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	/*
	 * Version POST
	 */
	@ResponseBody
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> registrationPost(HttpServletRequest request, UriComponentsBuilder ucBuilder)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, ParseException {

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
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
		String birthDate = request.getParameter("birthDate");
		String gender = request.getParameter("gender");
		String emailAdress = request.getParameter("emailAdress");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");
		
		
			

		// String pseudo = encrypt(pseudonym);
		// String meeting = encrypt(meetingName);

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

		long numberMember = memberRepository.count() + 1;
		String idCryptograph = "" + numberMember;
		System.out.println(numberMember);
		Cryptograph cryptograph = new Cryptograph();

		String content1 = "Thanks to create your count in our website \n"
				+ " Now,lick on this link to activate E-mail adress: "
				+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?number=" + numberMember;
		String subject1 = "confirm your E-mail adress";

		if (meetingName.equals("Amoureuse")) {

			String fatherName = request.getParameter("fatherName");
			String motherName = request.getParameter("motherName");
			String countryName = request.getParameter("countryName");
			String regionName = request.getParameter("regionName");
			String departmentName = request.getParameter("departmentName");
			String boroughName = request.getParameter("boroughName");
			String townName = request.getParameter("townName");
			String concessionName = request.getParameter("concessionName");

			Country countryDB = countryRepository.findByCountryName(countryName);
			Region regionDB = regionRepository.findByRegionName(regionName);
			Department departmentDB = departmentRepository.findByDepartmentName(departmentName);
			Borough boroughDB = boroughRepository.findByBoroughName(boroughName);
			// Town town = townRepository.findByTownName(townName);
			// Concession concession=
			// concessionRepository.findByConcessionName(concessionName);

			Town town = new Town();
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
				return new ResponseEntity(new MemberErrorType(
						"Unable to create. " + "A Concession with name " + "" + concessionName + " already exist"),
						HttpStatus.CONFLICT);
			}
			Concession concessionDB = concessionRepository.findByConcessionName(concessionName);

			String idLocality = countryName + regionName + departmentName + boroughName + townName + concessionName;

			Locality locality = new Locality();

			locality.setIdLocalite(idLocality);
			locality.setCountry(countryDB);
			locality.setRegion(regionDB);
			locality.setDepartment(departmentDB);
			locality.setBorough(boroughDB);
			locality.setTown(townDB);
			locality.setConcession(concessionDB);

			localityRepository.save(locality);

			Locality localityDB = localityRepository.findByIdLocalite(idLocality);

			String idLocalityDB = localityDB.getIdLocalite();

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
						try {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);


						datingInformation.setFatherName(fatherName);
						datingInformation.setMotherName(motherName);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setAge(memberBD.getAge());
						member.setEmailAdress(memberBD.getEmailAdress());
						member.setPhoneNumber(memberBD.getPhoneNumber());
						member.setPassword(memberBD.getPassword());
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(memberBD.getFriendlyDatingInformatio());
						member.setAcademicDatingInformation(memberBD.getAcademicDatingInformation());
						member.setProfessionalMeetingInformation(memberBD.getProfessionalMeetingInformation());

						member.setDatingInformation(datingInformation);

						

							String idComeLocality = pseudonym + idLocalityDB;

							if (comeLocalityRepository.exists(idComeLocality)) {

								return new ResponseEntity(new MemberErrorType("this locality is already exist"),
										HttpStatus.NOT_FOUND);
							}
							// enregistrement dans la zone tampon

							// String form="saphirmfogo@gmail.com";V
							MimeMessage msg = new MimeMessage(session);
							/// msg.setFrom(new InternetAddress(form));
							msg.setRecipients(MimeMessage.RecipientType.TO, memberBD.getEmailAdress());
							System.out.println(memberBD.getEmailAdress());
							msg.setSubject(subject1);
							msg.setText(content1);
							msg.setSentDate(new Date());

							Transport transport = session.getTransport("smtp");
							transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
							transport.sendMessage(msg, msg.getAllRecipients());
							transport.close();

							//memberBufferRepository.deleteAll();
							//memberRepository.deleteAll();
							memberBufferRepository.save(member);

							ComeLocality comeLocality = new ComeLocality();

							comeLocality.setId(idComeLocality);

							comeLocalityRepository.save(comeLocality);
							
							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);
							
							cryptograph.setId(idCryptograph);
							cryptograph.setPseudonym(pseudonym);
							cryptograph.setMeetingName(meetingName);
							cryptographRepository.save(cryptograph);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("Unable to create please try aigain"),
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
						
						try {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date date = null;
						
							date = dateFormat.parse(birthDate);
							System.out.println("Date parsée : " + date);

							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);
							int year = calendar.get(Calendar.YEAR);

							Calendar calendarCourante = Calendar.getInstance();

							int yearCourante = calendarCourante.get(Calendar.YEAR);

							int birthDat = yearCourante - year;
							String age= birthDat+"";
						
						// Member memberBD =
						// memberRepository.findByPseudonym(pseudonym);

						datingInformation.setFatherName(fatherName);
						datingInformation.setMotherName(motherName);

						member.setPseudonym(pseudonym);
						member.setGender(gender);
						member.setBirthDate(birthDate);
						member.setAge(age);
						member.setEmailAdress(emailAdress);
						member.setPhoneNumber(phoneNumber);
						member.setPassword(password);
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(null);
						member.setAcademicDatingInformation(null);
						member.setProfessionalMeetingInformation(null);

						member.setDatingInformation(datingInformation);

						
							String idComeLocality = pseudonym + idLocalityDB;

							if (comeLocalityRepository.exists(idComeLocality)) {

								return new ResponseEntity(new MemberErrorType("this locality is already exist"),
										HttpStatus.NOT_FOUND);
							}
							// enregistrement dans la zone tampon

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

							//memberBufferRepository.deleteAll();
							//memberRepository.deleteAll();
							memberBufferRepository.save(member);

							ComeLocality comeLocality = new ComeLocality();

							comeLocality.setId(idComeLocality);

							comeLocalityRepository.save(comeLocality);
							
							chooseMeeting.setIdChooseMeeting(idChoose);
							// chooseMeetingRepository.deleteAll();
							chooseMeetingRepository.save(chooseMeeting);

							cryptograph.setId(idCryptograph);
							cryptograph.setPseudonym(pseudonym);
							cryptograph.setMeetingName(meetingName);
							cryptographRepository.save(cryptograph);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
					} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("the email is not validate am"),
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

					//ChooseMeeting chooseMeetingBd = chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {
						try {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);


						professionalMeeting.setFirstName(firstName);
						professionalMeeting.setLastName(lastName);
						professionalMeeting.setLevelStudy(levelStudy);
						professionalMeeting.setProfession(profession);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setAge(memberBD.getAge());
						member.setEmailAdress(memberBD.getEmailAdress());
						member.setPhoneNumber(memberBD.getPhoneNumber());
						member.setPassword(memberBD.getPassword());
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(memberBD.getFriendlyDatingInformatio());
						member.setAcademicDatingInformation(memberBD.getAcademicDatingInformation());
						member.setDatingInformation(memberBD.getDatingInformation());

						member.setProfessionalMeetingInformation(professionalMeeting);

						
							// enregistrement dans la zone tampon

							// String form="saphirmfogo@gmail.com";V
							MimeMessage msg = new MimeMessage(session);
							/// msg.setFrom(new InternetAddress(form));
							msg.setRecipients(MimeMessage.RecipientType.TO, memberBD.getEmailAdress());
							System.out.println(memberBD.getEmailAdress()+"pour le mail");
							msg.setSubject(subject1);
							msg.setText(content1);
							msg.setSentDate(new Date());

							Transport transport = session.getTransport("smtp");
							transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
							transport.sendMessage(msg, msg.getAllRecipients());
							transport.close();

							//memberBufferRepository.deleteAll();
							//memberRepository.deleteAll();
							memberBufferRepository.save(member);
							
							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);
							
							cryptograph.setId(idCryptograph);
							cryptograph.setPseudonym(pseudonym);
							cryptograph.setMeetingName(meetingName);
							cryptographRepository.save(cryptograph);

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
							
							try {
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							Date date = null;
							
								date = dateFormat.parse(birthDate);
								System.out.println("Date parsée : " + date);

								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								int year = calendar.get(Calendar.YEAR);

								Calendar calendarCourante = Calendar.getInstance();

								int yearCourante = calendarCourante.get(Calendar.YEAR);

								int birthDat = yearCourante - year;
								String age= birthDat+"";
							


							professionalMeeting.setFirstName(firstName);
							professionalMeeting.setLastName(lastName);
							professionalMeeting.setLevelStudy(levelStudy);
							professionalMeeting.setProfession(profession);

							member.setPseudonym(pseudonym);
							member.setGender(gender);
							member.setBirthDate(birthDate);
							member.setAge(age);
							member.setEmailAdress(emailAdress);
							member.setPhoneNumber(phoneNumber);
							member.setPassword(password);
							// member.setPicture(fileName);
							member.setFriendlyDatingInformatio(null);
							member.setAcademicDatingInformation(null);
							member.setDatingInformation(null);

							member.setProfessionalMeetingInformation(professionalMeeting);

							
								// enregistrement dans la zone tampon

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

								//memberBufferRepository.deleteAll();
								//memberRepository.deleteAll();
								memberBufferRepository.save(member);

								
								chooseMeeting.setIdChooseMeeting(idChoose);
								chooseMeetingRepository.save(chooseMeeting);
								
								cryptograph.setId(idCryptograph);
								cryptograph.setPseudonym(pseudonym);
								cryptograph.setMeetingName(meetingName);
								cryptographRepository.save(cryptograph);

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

					//ChooseMeeting chooseMeetingBd = chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);


						academicDatingInformation.setFirstName(firstName);
						academicDatingInformation.setLastName(lastName);
						academicDatingInformation.setLevelStudy(levelStudy);
						academicDatingInformation.setSchoolName(schoolName);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setAge(memberBD.getAge());
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

							// String form="saphirmfogo@gmail.com";V
							MimeMessage msg = new MimeMessage(session);
							/// msg.setFrom(new InternetAddress(form));
							msg.setRecipients(MimeMessage.RecipientType.TO, memberBD.getEmailAdress());
							msg.setSubject(subject1);
							msg.setText(content1);
							msg.setSentDate(new Date());

							Transport transport = session.getTransport("smtp");
							transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
							transport.sendMessage(msg, msg.getAllRecipients());
							transport.close();

							//memberBufferRepository.deleteAll();
							//memberRepository.deleteAll();
							memberBufferRepository.save(member);

							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);
							
							cryptograph.setId(idCryptograph);
							cryptograph.setPseudonym(pseudonym);
							cryptograph.setMeetingName(meetingName);
							cryptographRepository.save(cryptograph);

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
							
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							Date date = null;
							
								date = dateFormat.parse(birthDate);
								System.out.println("Date parsée : " + date);

								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								int year = calendar.get(Calendar.YEAR);

								Calendar calendarCourante = Calendar.getInstance();

								int yearCourante = calendarCourante.get(Calendar.YEAR);

								int birthDat = yearCourante - year;
								String age= birthDat+"";


							academicDatingInformation.setFirstName(firstName);
							academicDatingInformation.setLastName(lastName);
							academicDatingInformation.setLevelStudy(levelStudy);
							academicDatingInformation.setSchoolName(schoolName);

							member.setPseudonym(pseudonym);
							member.setGender(gender);
							member.setBirthDate(birthDate);
							member.setAge(age);
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

								//memberBufferRepository.deleteAll();
								//memberRepository.deleteAll();
								memberBufferRepository.save(member);

								chooseMeeting.setIdChooseMeeting(idChoose);
								chooseMeetingRepository.save(chooseMeeting);
								
								cryptograph.setId(idCryptograph);
								cryptograph.setPseudonym(pseudonym);
								cryptograph.setMeetingName(meetingName);
								cryptographRepository.save(cryptograph);

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


						friendlyDatingInformation.setName(name);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setAge(memberBD.getAge());
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

							// String form="saphirmfogo@gmail.com";V
							MimeMessage msg = new MimeMessage(session);
							/// msg.setFrom(new InternetAddress(form));
							msg.setRecipients(MimeMessage.RecipientType.TO, memberBD.getEmailAdress());
							msg.setSubject(subject1);
							msg.setText(content1);
							msg.setSentDate(new Date());

							Transport transport = session.getTransport("smtp");
							transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
							transport.sendMessage(msg, msg.getAllRecipients());
							transport.close();
							
							//memberBufferRepository.deleteAll();
							//memberRepository.deleteAll();
							memberBufferRepository.save(member);

							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);
							
							cryptograph.setId(idCryptograph);
							cryptograph.setPseudonym(pseudonym);
							cryptograph.setMeetingName(meetingName);
							cryptographRepository.save(cryptograph);

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
							
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							Date date = null;
							
								date = dateFormat.parse(birthDate);
								System.out.println("Date parsée : " + date);

								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								int year = calendar.get(Calendar.YEAR);

								Calendar calendarCourante = Calendar.getInstance();

								int yearCourante = calendarCourante.get(Calendar.YEAR);

								int birthDat = yearCourante - year;
								String age= birthDat+"";
							
							


							friendlyDatingInformation.setName(name);

							member.setPseudonym(pseudonym);
							member.setGender(gender);
							member.setBirthDate(birthDate);
							member.setAge(age);
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

								//memberBufferRepository.deleteAll();
								//memberRepository.deleteAll();
								memberBufferRepository.save(member);

								chooseMeeting.setIdChooseMeeting(idChoose);
								chooseMeetingRepository.save(chooseMeeting);
								
								cryptograph.setId(idCryptograph);
								cryptograph.setPseudonym(pseudonym);
								cryptograph.setMeetingName(meetingName);
								cryptographRepository.save(cryptograph);

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	/*
	 * Version Get
	 */
	@ResponseBody
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ResponseEntity<?> registrationGet(HttpServletRequest request, UriComponentsBuilder ucBuilder) throws ParseException {

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
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
		String birthDate = request.getParameter("birthDate");
		String gender = request.getParameter("gender");
		String emailAdress = request.getParameter("emailAdress");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");
		
		
			

		// String pseudo = encrypt(pseudonym);
		// String meeting = encrypt(meetingName);

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

		long numberMember = memberRepository.count() + 1;
		String idCryptograph = "" + numberMember;
		System.out.println(numberMember);
		Cryptograph cryptograph = new Cryptograph();

		String content1 = "Thanks to create your count in our website \n"
				+ " Now,lick on this link to activate E-mail adress: "
				+ "http://localhost:8091/rencontre/InternetSurfer/confirmRegistration?number=" + numberMember;
		String subject1 = "confirm your E-mail adress";

		if (meetingName.equals("Amoureuse")) {

			String fatherName = request.getParameter("fatherName");
			String motherName = request.getParameter("motherName");
			String countryName = request.getParameter("countryName");
			String regionName = request.getParameter("regionName");
			String departmentName = request.getParameter("departmentName");
			String boroughName = request.getParameter("boroughName");
			String townName = request.getParameter("townName");
			String concessionName = request.getParameter("concessionName");

			Country countryDB = countryRepository.findByCountryName(countryName);
			Region regionDB = regionRepository.findByRegionName(regionName);
			Department departmentDB = departmentRepository.findByDepartmentName(departmentName);
			Borough boroughDB = boroughRepository.findByBoroughName(boroughName);
			// Town town = townRepository.findByTownName(townName);
			// Concession concession=
			// concessionRepository.findByConcessionName(concessionName);

			Town town = new Town();
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
				return new ResponseEntity(new MemberErrorType(
						"Unable to create. " + "A Concession with name " + "" + concessionName + " already exist"),
						HttpStatus.CONFLICT);
			}
			Concession concessionDB = concessionRepository.findByConcessionName(concessionName);

			String idLocality = countryName + regionName + departmentName + boroughName + townName + concessionName;

			Locality locality = new Locality();

			locality.setIdLocalite(idLocality);
			locality.setCountry(countryDB);
			locality.setRegion(regionDB);
			locality.setDepartment(departmentDB);
			locality.setBorough(boroughDB);
			locality.setTown(townDB);
			locality.setConcession(concessionDB);

			localityRepository.save(locality);

			Locality localityDB = localityRepository.findByIdLocalite(idLocality);

			String idLocalityDB = localityDB.getIdLocalite();

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
						try {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);


						datingInformation.setFatherName(fatherName);
						datingInformation.setMotherName(motherName);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setAge(memberBD.getAge());
						member.setEmailAdress(memberBD.getEmailAdress());
						member.setPhoneNumber(memberBD.getPhoneNumber());
						member.setPassword(memberBD.getPassword());
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(memberBD.getFriendlyDatingInformatio());
						member.setAcademicDatingInformation(memberBD.getAcademicDatingInformation());
						member.setProfessionalMeetingInformation(memberBD.getProfessionalMeetingInformation());

						member.setDatingInformation(datingInformation);

						

							String idComeLocality = pseudonym + idLocalityDB;

							if (comeLocalityRepository.exists(idComeLocality)) {

								return new ResponseEntity(new MemberErrorType("this locality is already exist"),
										HttpStatus.NOT_FOUND);
							}
							// enregistrement dans la zone tampon

							// String form="saphirmfogo@gmail.com";V
							MimeMessage msg = new MimeMessage(session);
							/// msg.setFrom(new InternetAddress(form));
							msg.setRecipients(MimeMessage.RecipientType.TO, memberBD.getEmailAdress());
							System.out.println(memberBD.getEmailAdress());
							msg.setSubject(subject1);
							msg.setText(content1);
							msg.setSentDate(new Date());

							Transport transport = session.getTransport("smtp");
							transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
							transport.sendMessage(msg, msg.getAllRecipients());
							transport.close();

							//memberBufferRepository.deleteAll();
							//memberRepository.deleteAll();
							memberBufferRepository.save(member);

							ComeLocality comeLocality = new ComeLocality();

							comeLocality.setId(idComeLocality);

							comeLocalityRepository.save(comeLocality);
							
							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);
							
							cryptograph.setId(idCryptograph);
							cryptograph.setPseudonym(pseudonym);
							cryptograph.setMeetingName(meetingName);
							cryptographRepository.save(cryptograph);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("Unable to create please try aigain"),
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
						
						try {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date date = null;
						
							date = dateFormat.parse(birthDate);
							System.out.println("Date parsée : " + date);

							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);
							int year = calendar.get(Calendar.YEAR);

							Calendar calendarCourante = Calendar.getInstance();

							int yearCourante = calendarCourante.get(Calendar.YEAR);

							int birthDat = yearCourante - year;
							String age= birthDat+"";
						
						// Member memberBD =
						// memberRepository.findByPseudonym(pseudonym);

						datingInformation.setFatherName(fatherName);
						datingInformation.setMotherName(motherName);

						member.setPseudonym(pseudonym);
						member.setGender(gender);
						member.setBirthDate(birthDate);
						member.setAge(age);
						member.setEmailAdress(emailAdress);
						member.setPhoneNumber(phoneNumber);
						member.setPassword(password);
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(null);
						member.setAcademicDatingInformation(null);
						member.setProfessionalMeetingInformation(null);

						member.setDatingInformation(datingInformation);

						
							String idComeLocality = pseudonym + idLocalityDB;

							if (comeLocalityRepository.exists(idComeLocality)) {

								return new ResponseEntity(new MemberErrorType("this locality is already exist"),
										HttpStatus.NOT_FOUND);
							}
							// enregistrement dans la zone tampon

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

							//memberBufferRepository.deleteAll();
							//memberRepository.deleteAll();
							memberBufferRepository.save(member);

							ComeLocality comeLocality = new ComeLocality();

							comeLocality.setId(idComeLocality);

							comeLocalityRepository.save(comeLocality);
							
							chooseMeeting.setIdChooseMeeting(idChoose);
							// chooseMeetingRepository.deleteAll();
							chooseMeetingRepository.save(chooseMeeting);

							cryptograph.setId(idCryptograph);
							cryptograph.setPseudonym(pseudonym);
							cryptograph.setMeetingName(meetingName);
							cryptographRepository.save(cryptograph);

							return new ResponseEntity<MemberBuffer>(member, HttpStatus.CREATED);
					} catch (Exception ex) {
							System.out.println(ex.getMessage());

							logger.error("Unable to create. A Member with name {} already exist",
									member.getPseudonym());
							return new ResponseEntity(new MemberErrorType("the email is not validate am"),
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

					//ChooseMeeting chooseMeetingBd = chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {
						try {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);


						professionalMeeting.setFirstName(firstName);
						professionalMeeting.setLastName(lastName);
						professionalMeeting.setLevelStudy(levelStudy);
						professionalMeeting.setProfession(profession);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setAge(memberBD.getAge());
						member.setEmailAdress(memberBD.getEmailAdress());
						member.setPhoneNumber(memberBD.getPhoneNumber());
						member.setPassword(memberBD.getPassword());
						// member.setPicture(fileName);
						member.setFriendlyDatingInformatio(memberBD.getFriendlyDatingInformatio());
						member.setAcademicDatingInformation(memberBD.getAcademicDatingInformation());
						member.setDatingInformation(memberBD.getDatingInformation());

						member.setProfessionalMeetingInformation(professionalMeeting);

						
							// enregistrement dans la zone tampon

							// String form="saphirmfogo@gmail.com";V
							MimeMessage msg = new MimeMessage(session);
							/// msg.setFrom(new InternetAddress(form));
							msg.setRecipients(MimeMessage.RecipientType.TO, memberBD.getEmailAdress());
							System.out.println(memberBD.getEmailAdress()+"pour le mail");
							msg.setSubject(subject1);
							msg.setText(content1);
							msg.setSentDate(new Date());

							Transport transport = session.getTransport("smtp");
							transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
							transport.sendMessage(msg, msg.getAllRecipients());
							transport.close();

							//memberBufferRepository.deleteAll();
							//memberRepository.deleteAll();
							memberBufferRepository.save(member);
							
							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);
							
							cryptograph.setId(idCryptograph);
							cryptograph.setPseudonym(pseudonym);
							cryptograph.setMeetingName(meetingName);
							cryptographRepository.save(cryptograph);

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
							
							try {
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							Date date = null;
							
								date = dateFormat.parse(birthDate);
								System.out.println("Date parsée : " + date);

								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								int year = calendar.get(Calendar.YEAR);

								Calendar calendarCourante = Calendar.getInstance();

								int yearCourante = calendarCourante.get(Calendar.YEAR);

								int birthDat = yearCourante - year;
								String age= birthDat+"";
							


							professionalMeeting.setFirstName(firstName);
							professionalMeeting.setLastName(lastName);
							professionalMeeting.setLevelStudy(levelStudy);
							professionalMeeting.setProfession(profession);

							member.setPseudonym(pseudonym);
							member.setGender(gender);
							member.setBirthDate(birthDate);
							member.setAge(age);
							member.setEmailAdress(emailAdress);
							member.setPhoneNumber(phoneNumber);
							member.setPassword(password);
							// member.setPicture(fileName);
							member.setFriendlyDatingInformatio(null);
							member.setAcademicDatingInformation(null);
							member.setDatingInformation(null);

							member.setProfessionalMeetingInformation(professionalMeeting);

							
								// enregistrement dans la zone tampon

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

								//memberBufferRepository.deleteAll();
								//memberRepository.deleteAll();
								memberBufferRepository.save(member);

								
								chooseMeeting.setIdChooseMeeting(idChoose);
								chooseMeetingRepository.save(chooseMeeting);
								
								cryptograph.setId(idCryptograph);
								cryptograph.setPseudonym(pseudonym);
								cryptograph.setMeetingName(meetingName);
								cryptographRepository.save(cryptograph);

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

					//ChooseMeeting chooseMeetingBd = chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);


						academicDatingInformation.setFirstName(firstName);
						academicDatingInformation.setLastName(lastName);
						academicDatingInformation.setLevelStudy(levelStudy);
						academicDatingInformation.setSchoolName(schoolName);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setAge(memberBD.getAge());
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

							// String form="saphirmfogo@gmail.com";V
							MimeMessage msg = new MimeMessage(session);
							/// msg.setFrom(new InternetAddress(form));
							msg.setRecipients(MimeMessage.RecipientType.TO, memberBD.getEmailAdress());
							msg.setSubject(subject1);
							msg.setText(content1);
							msg.setSentDate(new Date());

							Transport transport = session.getTransport("smtp");
							transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
							transport.sendMessage(msg, msg.getAllRecipients());
							transport.close();

							//memberBufferRepository.deleteAll();
							//memberRepository.deleteAll();
							memberBufferRepository.save(member);

							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);
							
							cryptograph.setId(idCryptograph);
							cryptograph.setPseudonym(pseudonym);
							cryptograph.setMeetingName(meetingName);
							cryptographRepository.save(cryptograph);

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
							
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							Date date = null;
							
								date = dateFormat.parse(birthDate);
								System.out.println("Date parsée : " + date);

								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								int year = calendar.get(Calendar.YEAR);

								Calendar calendarCourante = Calendar.getInstance();

								int yearCourante = calendarCourante.get(Calendar.YEAR);

								int birthDat = yearCourante - year;
								String age= birthDat+"";


							academicDatingInformation.setFirstName(firstName);
							academicDatingInformation.setLastName(lastName);
							academicDatingInformation.setLevelStudy(levelStudy);
							academicDatingInformation.setSchoolName(schoolName);

							member.setPseudonym(pseudonym);
							member.setGender(gender);
							member.setBirthDate(birthDate);
							member.setAge(age);
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

								//memberBufferRepository.deleteAll();
								//memberRepository.deleteAll();
								memberBufferRepository.save(member);

								chooseMeeting.setIdChooseMeeting(idChoose);
								chooseMeetingRepository.save(chooseMeeting);
								
								cryptograph.setId(idCryptograph);
								cryptograph.setPseudonym(pseudonym);
								cryptograph.setMeetingName(meetingName);
								cryptographRepository.save(cryptograph);

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

					//ChooseMeeting chooseMeetingBd = chooseMeetingRepository.findByIdChooseMeeting(idChoose);
					if (chooseMeetingRepository.findByIdChooseMeeting(idChoose) != null) {

						return new ResponseEntity(
								new MemberErrorType("the email and pseudonym are already created in this type meeting"),
								HttpStatus.NOT_FOUND);
					} else {
						Member memberBD = memberRepository.findByPseudonym(pseudonym);


						friendlyDatingInformation.setName(name);

						member.setPseudonym(memberBD.getPseudonym());
						member.setGender(memberBD.getGender());
						member.setBirthDate(memberBD.getBirthDate());
						member.setAge(memberBD.getAge());
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

							// String form="saphirmfogo@gmail.com";V
							MimeMessage msg = new MimeMessage(session);
							/// msg.setFrom(new InternetAddress(form));
							msg.setRecipients(MimeMessage.RecipientType.TO, memberBD.getEmailAdress());
							msg.setSubject(subject1);
							msg.setText(content1);
							msg.setSentDate(new Date());

							Transport transport = session.getTransport("smtp");
							transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
							transport.sendMessage(msg, msg.getAllRecipients());
							transport.close();
							
							//memberBufferRepository.deleteAll();
							//memberRepository.deleteAll();
							memberBufferRepository.save(member);

							chooseMeeting.setIdChooseMeeting(idChoose);
							chooseMeetingRepository.save(chooseMeeting);
							
							cryptograph.setId(idCryptograph);
							cryptograph.setPseudonym(pseudonym);
							cryptograph.setMeetingName(meetingName);
							cryptographRepository.save(cryptograph);

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
							
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							Date date = null;
							
								date = dateFormat.parse(birthDate);
								System.out.println("Date parsée : " + date);

								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								int year = calendar.get(Calendar.YEAR);

								Calendar calendarCourante = Calendar.getInstance();

								int yearCourante = calendarCourante.get(Calendar.YEAR);

								int birthDat = yearCourante - year;
								String age= birthDat+"";
							
							


							friendlyDatingInformation.setName(name);

							member.setPseudonym(pseudonym);
							member.setGender(gender);
							member.setBirthDate(birthDate);
							member.setAge(age);
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

								//memberBufferRepository.deleteAll();
								//memberRepository.deleteAll();
								memberBufferRepository.save(member);

								chooseMeeting.setIdChooseMeeting(idChoose);
								chooseMeetingRepository.save(chooseMeeting);
								
								cryptograph.setId(idCryptograph);
								cryptograph.setPseudonym(pseudonym);
								cryptograph.setMeetingName(meetingName);
								cryptographRepository.save(cryptograph);

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
	
	/*
	 * delete all
	 */
	 @ResponseBody
	 @RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
	 public String delete(HttpServletRequest request) {
		memberBufferRepository.deleteAll();
		memberRepository.deleteAll();
		chooseMeetingRepository.deleteAll();
		townRepository.deleteAll();
		concessionRepository.deleteAll();
		comeLocalityRepository.deleteAll();
		cryptographRepository.deleteAll();
		localityRepository.deleteAll();
		 
		 return "bien";
	 }
	
	/**
	 * start confirm registration
	 */
	/*
	 * Version POST
	 * 
	 * @SuppressWarnings({ "unchecked", "rawtypes" })
	 * 
	 * @RequestMapping(value = "/confirmRegistration", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public ResponseEntity<?>
	 * confirmRegistrationPost(HttpServletRequest request) {
	 * 
	 * String number=request.getParameter("number"); Cryptograph
	 * cryptograph=cryptographRepository.findById(number); String pseudonym =
	 * cryptograph.getPseudonym(); String meetingName =
	 * cryptograph.getMeetingName(); ////String user =
	 * request.getParameter("user"); //String meeting =
	 * request.getParameter("meetingName"); //String pseudonym =
	 * decryptographe(user); //String meetingName = decryptographe(meeting);
	 * 
	 * //System.out.println(pseudonym);
	 * 
	 * Status statusMember = statusRepository.findByStatusName("connected");
	 * 
	 * Member member = new Member();
	 * 
	 * MemberBuffer memberBuffer =
	 * memberBufferRepository.findByPseudonym(pseudonym); Member memberDB =
	 * memberRepository.findByPseudonym(pseudonym);
	 * 
	 * if (memberDB != null) {
	 * 
	 * if (meetingName.equals("Amoureuse")) {
	 * 
	 * member.setPseudonym(memberBuffer.getPseudonym());
	 * member.setBirthDate(memberBuffer.getBirthDate());
	 * member.setEmailAdress(memberBuffer.getEmailAdress());
	 * member.setGender(memberBuffer.getGender());
	 * member.setPhoneNumber(memberBuffer.getPhoneNumber());
	 * member.setPassword(memberBuffer.getPassword());
	 * member.setPicture(memberBuffer.getPicture());
	 * member.setStatus(statusMember);
	 * member.setDatingInformation(memberBuffer.getDatingInformation());
	 * member.setAcademicDatingInformation(memberDB.getAcademicDatingInformation
	 * ()); member.setProfessionalMeetingInformation(memberDB.
	 * getProfessionalMeetingInformation());
	 * member.setFriendlyDatingInformatio(memberDB.getFriendlyDatingInformatio()
	 * );
	 * 
	 * // memberRepository.deleteAll(); memberRepository.save(member);
	 * memberBufferRepository.delete(memberBuffer);
	 * 
	 * return new ResponseEntity<Member>(member, HttpStatus.OK);
	 * 
	 * } else if (meetingName.equals("Professionnelle")) {
	 * 
	 * member.setPseudonym(memberBuffer.getPseudonym());
	 * member.setBirthDate(memberBuffer.getBirthDate());
	 * member.setEmailAdress(memberBuffer.getEmailAdress());
	 * member.setGender(memberBuffer.getGender());
	 * member.setPhoneNumber(memberBuffer.getPhoneNumber());
	 * member.setPassword(memberBuffer.getPassword());
	 * member.setPicture(memberBuffer.getPicture());
	 * member.setStatus(statusMember);
	 * member.setDatingInformation(memberDB.getDatingInformation());
	 * member.setAcademicDatingInformation(memberDB.getAcademicDatingInformation
	 * ()); member.setProfessionalMeetingInformation(memberBuffer.
	 * getProfessionalMeetingInformation());
	 * member.setFriendlyDatingInformatio(memberDB.getFriendlyDatingInformatio()
	 * );
	 * 
	 * // memberRepository.deleteAll(); memberRepository.save(member);
	 * memberBufferRepository.delete(memberBuffer); return new
	 * ResponseEntity<Member>(member, HttpStatus.OK);
	 * 
	 * } else if (meetingName.equals("Academique")) {
	 * 
	 * member.setPseudonym(memberBuffer.getPseudonym());
	 * member.setBirthDate(memberBuffer.getBirthDate());
	 * member.setEmailAdress(memberBuffer.getEmailAdress());
	 * member.setGender(memberBuffer.getGender());
	 * member.setPhoneNumber(memberBuffer.getPhoneNumber());
	 * member.setPassword(memberBuffer.getPassword());
	 * member.setPicture(memberBuffer.getPicture());
	 * member.setStatus(statusMember);
	 * member.setDatingInformation(memberDB.getDatingInformation());
	 * member.setAcademicDatingInformation(memberBuffer.
	 * getAcademicDatingInformation());
	 * member.setProfessionalMeetingInformation(memberDB.
	 * getProfessionalMeetingInformation());
	 * member.setFriendlyDatingInformatio(memberDB.getFriendlyDatingInformatio()
	 * );
	 * 
	 * // memberRepository.deleteAll(); memberRepository.save(member);
	 * memberBufferRepository.delete(memberBuffer); return new
	 * ResponseEntity<Member>(member, HttpStatus.OK);
	 * 
	 * } else if (meetingName.equals("Amicale")) {
	 * 
	 * member.setPseudonym(memberBuffer.getPseudonym());
	 * member.setBirthDate(memberBuffer.getBirthDate());
	 * member.setEmailAdress(memberBuffer.getEmailAdress());
	 * member.setGender(memberBuffer.getGender());
	 * member.setPhoneNumber(memberBuffer.getPhoneNumber());
	 * member.setPassword(memberBuffer.getPassword());
	 * member.setPicture(memberBuffer.getPicture());
	 * member.setStatus(statusMember);
	 * member.setDatingInformation(memberDB.getDatingInformation());
	 * member.setAcademicDatingInformation(memberDB.getAcademicDatingInformation
	 * ()); member.setProfessionalMeetingInformation(memberDB.
	 * getProfessionalMeetingInformation());
	 * member.setFriendlyDatingInformatio(memberBuffer.
	 * getFriendlyDatingInformatio());
	 * 
	 * memberRepository.save(member);
	 * memberBufferRepository.delete(memberBuffer); return new
	 * ResponseEntity<Member>(member, HttpStatus.OK);
	 * 
	 * } } else { member.setPseudonym(memberBuffer.getPseudonym());
	 * member.setBirthDate(memberBuffer.getBirthDate());
	 * member.setEmailAdress(memberBuffer.getEmailAdress());
	 * member.setGender(memberBuffer.getGender());
	 * member.setPhoneNumber(memberBuffer.getPhoneNumber());
	 * member.setPassword(memberBuffer.getPassword());
	 * member.setPicture(memberBuffer.getPicture());
	 * member.setStatus(statusMember);
	 * member.setDatingInformation(memberBuffer.getDatingInformation());
	 * member.setAcademicDatingInformation(memberBuffer.
	 * getAcademicDatingInformation());
	 * member.setProfessionalMeetingInformation(memberBuffer.
	 * getProfessionalMeetingInformation());
	 * member.setFriendlyDatingInformatio(memberBuffer.
	 * getFriendlyDatingInformatio());
	 * 
	 * memberRepository.save(member);
	 * memberBufferRepository.delete(memberBuffer); return new
	 * ResponseEntity<Member>(member, HttpStatus.OK);
	 * 
	 * } return null;
	 * 
	 * }
	 * 
	 * /* Version GET
	 */
	
	@RequestMapping(value = "/confirmRegistration", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> confirmRegistrationGet(HttpServletRequest request) {

		String number = request.getParameter("number");
		Cryptograph cryptograph = cryptographRepository.findById(number);
		System.out.println(cryptograph);
		String pseudonym = cryptograph.getPseudonym();
		String meetingName = cryptograph.getMeetingName();
		// String pseudonym = request.getParameter("user");
		// String meetingName = request.getParameter("meetingName");
		System.out.println(pseudonym);

		Status statusMember = statusRepository.findByStatusName("connected");

		Member member = new Member();

		MemberBuffer memberBuffer = memberBufferRepository.findByPseudonym(pseudonym);
		Member memberDB = memberRepository.findByPseudonym(pseudonym);

		if (memberDB != null) {

			if (meetingName.equals("Amoureuse")) {

				member.setPseudonym(memberBuffer.getPseudonym());
				member.setBirthDate(memberBuffer.getBirthDate());
				member.setAge(memberBuffer.getAge());
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

				//memberRepository.deleteAll();
				memberRepository.save(member);
				memberBufferRepository.delete(memberBuffer);
				cryptographRepository.deleteAll();
				//cryptographRepository.delete(number);
				return new ResponseEntity<Member>(member, HttpStatus.OK);

			} else if (meetingName.equals("Professionnelle")) {

				member.setPseudonym(memberBuffer.getPseudonym());
				member.setBirthDate(memberBuffer.getBirthDate());
				member.setAge(memberBuffer.getAge());
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
				cryptographRepository.delete(number);
				return new ResponseEntity<Member>(member, HttpStatus.OK);

			} else if (meetingName.equals("Academique")) {

				member.setPseudonym(memberBuffer.getPseudonym());
				member.setBirthDate(memberBuffer.getBirthDate());
				member.setAge(memberBuffer.getAge());
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
				cryptographRepository.delete(number);
				return new ResponseEntity<Member>(member, HttpStatus.OK);

			} else if (meetingName.equals("Amicale")) {

				member.setPseudonym(memberBuffer.getPseudonym());
				member.setBirthDate(memberBuffer.getBirthDate());
				member.setAge(memberBuffer.getAge());
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
				cryptographRepository.delete(number);
				return new ResponseEntity<Member>(member, HttpStatus.OK);

			}
		} else {
			member.setPseudonym(memberBuffer.getPseudonym());
			member.setBirthDate(memberBuffer.getBirthDate());
			member.setAge(memberBuffer.getAge());
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
			cryptographRepository.delete(number);
			return new ResponseEntity<Member>(member, HttpStatus.OK);

		}
		return null;

	}

	/**
	 * Start visualize testimony
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
