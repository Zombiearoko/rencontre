
package com.bocobi2.rencontre.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.bocobi2.rencontre.model.AcademicDatingInformation;
import com.bocobi2.rencontre.model.Borough;
import com.bocobi2.rencontre.model.ChooseMeeting;
import com.bocobi2.rencontre.model.ComeLocality;
import com.bocobi2.rencontre.model.Concession;
import com.bocobi2.rencontre.model.Conversation;
import com.bocobi2.rencontre.model.Country;
import com.bocobi2.rencontre.model.DatingInformation;
import com.bocobi2.rencontre.model.Department;
import com.bocobi2.rencontre.model.Friendly;
import com.bocobi2.rencontre.model.Locality;
import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.MemberBuffer;
import com.bocobi2.rencontre.model.MemberErrorType;
import com.bocobi2.rencontre.model.Message;
import com.bocobi2.rencontre.model.ProfessionalMeetingInformation;
import com.bocobi2.rencontre.model.Region;
import com.bocobi2.rencontre.model.Status;
import com.bocobi2.rencontre.model.Testimony;
import com.bocobi2.rencontre.model.Town;
import com.bocobi2.rencontre.model.TypeMeeting;
import com.bocobi2.rencontre.repositories.ChooseMeetingRepository;
import com.bocobi2.rencontre.repositories.ConversationRepository;
import com.bocobi2.rencontre.repositories.FriendlyRepository;
import com.bocobi2.rencontre.repositories.MemberBufferRepository;
import com.bocobi2.rencontre.repositories.MemberRepository;
import com.bocobi2.rencontre.repositories.MessageRepository;
import com.bocobi2.rencontre.repositories.StatusRepository;
import com.bocobi2.rencontre.repositories.TestimonyRepository;
import com.bocobi2.rencontre.repositories.TypeMeetingRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rencontre/Member")

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberBufferRepository memberBufferRepository;

	@Autowired
	TestimonyRepository testimonyRepository;

	@Autowired
	private MailSender sender;

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	ConversationRepository conversationRepository;

	@Autowired
	StatusRepository statusRepository;

	@Autowired
	TypeMeetingRepository typeMeetingRepository;

	@Autowired
	ChooseMeetingRepository chooseMeetingRepository;

	@Autowired
	private SimpMessagingTemplate webSocket;

	@Autowired
	GridFsOperations gridOperations;

	@Autowired
	FriendlyRepository freindlyRepository;

	public MemberController(SimpMessagingTemplate webSocket) {
		this.webSocket = webSocket;
	}

	private String imageFileId = "";
	private String FOLDER = "/home/saphir/Images/style/";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/savePicture", method = RequestMethod.POST)
	public ResponseEntity<?> savePicturePost(@RequestParam("file") MultipartFile file, HttpServletRequest req)
			throws UnknownHostException, Exception, FileNotFoundException {

		DBObject metaData = new BasicDBObject();

		InputStream iamgeStream = file.getInputStream();
		metaData.put("type", "image");

		// Store picture to MongoDB
		imageFileId = gridOperations.store(iamgeStream, "jsa-logo.png", "image/png", metaData).getId().toString();

		System.out.println("ImageFileId = " + imageFileId);

		InputStream iamgeStreamText = file.getInputStream();
		metaData.put("type", "text");

		// Store file text to MongoDB
		imageFileId = gridOperations.store(iamgeStream, "saphir.pdf", "texte/pdf", metaData).getId().toString();

		System.out.println("ImageFileId = " + imageFileId);

		InputStream iamgeStreamVideo = file.getInputStream();
		metaData.put("type", "video");

		// Store video to MongoDB
		imageFileId = gridOperations.store(iamgeStreamVideo, "volviane.mp4", "video/mp4", metaData).getId().toString();

		System.out.println("ImageFileId = " + imageFileId);

		String status = "Upload has been successful";

		return new ResponseEntity<String>(status, HttpStatus.OK);
		// return Response.status(200).entity(status).build();

	}

	/**
	 * connexion of the member
	 * 
	 * cette methode permet de connecter un membre dans une session
	 */
	/*
	 * Version POST
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/connexion", method = RequestMethod.POST)
	public ResponseEntity<?> connexionMemberPost(HttpServletRequest requestConnexion) {

		HttpSession session = requestConnexion.getSession();
		// String connexionResult;
		// recuperation des champs de connexion
		String pseudonym = requestConnexion.getParameter("pseudonym");
		String password = requestConnexion.getParameter("password");
		String meetingName = requestConnexion.getParameter("meetingName");
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
					String status = "Connected";
					Status statusDB = statusRepository.findByStatusName(status);
					member.setStatus(statusDB);
					member.setMeetingNameConnexion(meetingName);
					memberRepository.save(member);
					session.setAttribute("Member", member);
					return new ResponseEntity<Member>(member, HttpStatus.OK);
				} else {
					session.setAttribute("Member", null);
					logger.error("Member with password {} not found.", password);
					return new ResponseEntity(
							new MemberErrorType("Member with " + "password " + password + " not found."),
							HttpStatus.NOT_FOUND);
				}
			} else {
				logger.error("Member with password {} not found.", pseudonym);
				return new ResponseEntity(
						new MemberErrorType("Member with " + "pseudonym " + pseudonym + " not found."),
						HttpStatus.NOT_FOUND);

			}
		} catch (Exception ex) {
			logger.error("Member with pseudonym {} not found.", pseudonym);
			return new ResponseEntity(new MemberErrorType("Member with " + "pseudonym " + pseudonym + " not found."),
					HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * version Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/connexion", method = RequestMethod.GET)
	public ResponseEntity<?> connexionMemberGet(HttpServletRequest requestConnexion) {


		HttpSession session = requestConnexion.getSession();
		// String connexionResult;
		// recuperation des champs de connexion
		String pseudonym = requestConnexion.getParameter("pseudonym");
		String password = requestConnexion.getParameter("password");
		String meetingName = requestConnexion.getParameter("meetingName");
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
					String status = "Connected";
					Status statusDB = statusRepository.findByStatusName(status);
					member.setStatus(statusDB);
					member.setMeetingNameConnexion(meetingName);
					memberRepository.save(member);
					session.setAttribute("Member", member);
					return new ResponseEntity<Member>(member, HttpStatus.OK);
				} else {
					session.setAttribute("Member", null);
					logger.error("Member with password {} not found.", password);
					return new ResponseEntity(
							new MemberErrorType("Member with " + "password " + password + " not found."),
							HttpStatus.NOT_FOUND);
				}
			} else {
				logger.error("Member with password {} not found.", pseudonym);
				return new ResponseEntity(
						new MemberErrorType("Member with " + "pseudonym " + pseudonym + " not found."),
						HttpStatus.NOT_FOUND);

			}
		} catch (Exception ex) {
			logger.error("Member with pseudonym {} not found.", pseudonym);
			return new ResponseEntity(new MemberErrorType("Member with " + "pseudonym " + pseudonym + " not found."),
					HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * end connexion
	 */
	/*
	 * methode pour retourner les type de rencontre auxquels un individus est
	 * connecte
	 */
	/*
	 * version Post
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/returnTypeMeeting", method = RequestMethod.POST)
	public ResponseEntity<List<TypeMeeting>> returnTypeMeetingPost(HttpServletRequest request) {

		// HttpSession sessionMember = request.getSession();
		// Member member = (Member) sessionMember.getAttribute("Member");
		String pseudonymMember = request.getParameter("pseudonym");
		Member member = memberRepository.findByPseudonym(pseudonymMember);
		String pseudonym = member.getPseudonym();

		List<TypeMeeting> listTypeMeeting = new ArrayList<TypeMeeting>();
		TypeMeeting typeMeeting;
		TypeMeeting datingMeeting = typeMeetingRepository.findByMeetingName("Amoureuse");
		TypeMeeting professionnalMeeting = typeMeetingRepository.findByMeetingName("Professionnelle");
		TypeMeeting friendlyMeeting = typeMeetingRepository.findByMeetingName("Amicale");
		TypeMeeting schoolMeeting = typeMeetingRepository.findByMeetingName("Academique");

		if (chooseMeetingRepository.exists(pseudonym + datingMeeting.getId())) {

			typeMeeting = datingMeeting;
			listTypeMeeting.add(typeMeeting);

		} else if (chooseMeetingRepository.exists(pseudonym + professionnalMeeting.getId())) {

			typeMeeting = professionnalMeeting;
			listTypeMeeting.add(typeMeeting);

		} else if (chooseMeetingRepository.exists(pseudonym + friendlyMeeting.getId())) {

			typeMeeting = friendlyMeeting;
			listTypeMeeting.add(typeMeeting);

		} else if (chooseMeetingRepository.exists(pseudonym + schoolMeeting.getId())) {
			typeMeeting = schoolMeeting;
			listTypeMeeting.add(typeMeeting);
		}

		return new ResponseEntity<List<TypeMeeting>>(listTypeMeeting, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/returnTypeMeeting", method = RequestMethod.GET)
	public ResponseEntity<List<TypeMeeting>> returnTypeMeetingGet(HttpServletRequest request) {

		// HttpSession sessionMember = request.getSession();
		// Member member = (Member) sessionMember.getAttribute("Member");
		String pseudonymMember = request.getParameter("pseudonym");
		Member member = memberRepository.findByPseudonym(pseudonymMember);
		String pseudonym = member.getPseudonym();

		List<TypeMeeting> listTypeMeeting = new ArrayList<TypeMeeting>();
		TypeMeeting typeMeeting;
		TypeMeeting datingMeeting = typeMeetingRepository.findByMeetingName("Amoureuse");
		TypeMeeting professionnalMeeting = typeMeetingRepository.findByMeetingName("Professionnelle");
		TypeMeeting friendlyMeeting = typeMeetingRepository.findByMeetingName("Amicale");
		TypeMeeting schoolMeeting = typeMeetingRepository.findByMeetingName("Academique");

		if (chooseMeetingRepository.exists(pseudonym + datingMeeting.getId())) {

			typeMeeting = datingMeeting;
			listTypeMeeting.add(typeMeeting);

		} else if (chooseMeetingRepository.exists(pseudonym + professionnalMeeting.getId())) {

			typeMeeting = professionnalMeeting;
			listTypeMeeting.add(typeMeeting);

		} else if (chooseMeetingRepository.exists(pseudonym + friendlyMeeting.getId())) {

			typeMeeting = friendlyMeeting;
			listTypeMeeting.add(typeMeeting);

		} else if (chooseMeetingRepository.exists(pseudonym + schoolMeeting.getId())) {
			typeMeeting = schoolMeeting;
			listTypeMeeting.add(typeMeeting);
		}

		return new ResponseEntity<List<TypeMeeting>>(listTypeMeeting, HttpStatus.OK);

	}

	
	
	/**
	 * Method change status
	 */
	/*
	 * VERSION Post
	 */

	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public ResponseEntity<?> changeStatusPost(HttpServletRequest request) {

		try {
			String statusName = request.getParameter("statusName");
			Status status = statusRepository.findByStatusName(statusName);
			System.out.println(status);
			//String pseudonym = request.getParameter("pseudonym");

			HttpSession session= request.getSession();
			 Member member= (Member) session.getAttribute("Member");

			//Member member = memberRepository.findByPseudonym(pseudonym);
			member.setStatus(status);
			memberRepository.save(member);
			return new ResponseEntity<Member>(member, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("Status not found.");
			return new ResponseEntity(new MemberErrorType("Status not found."), HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * VERSION GET
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	public ResponseEntity<?> changeStatusGet(HttpServletRequest request) {
		try {
			String statusName = request.getParameter("statusName");
			Status status = statusRepository.findByStatusName(statusName);
			System.out.println(status);
			//String pseudonym = request.getParameter("pseudonym");

			 HttpSession session= request.getSession();
			 Member member= (Member) session.getAttribute("Member");

			//Member member = memberRepository.findByPseudonym(pseudonym);
			member.setStatus(status);
			memberRepository.save(member);
			return new ResponseEntity<Member>(member, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("Status not found.");
			return new ResponseEntity(new MemberErrorType("Status not found."), HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * add testimony
	 */
	/*
	 * Version POST
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addTestimony", method = RequestMethod.POST)
	public ResponseEntity<?> addTestimonyPost(HttpServletRequest requestTestimony,
			@RequestParam("file") MultipartFile file, UriComponentsBuilder ucBuilder)
			throws IOException, ServletException {
		HttpSession session = requestTestimony.getSession();
		Member member = (Member) session.getAttribute("Member");
		// String author=member.getPseudonym();
		Testimony testimony = new Testimony();
		// String resultTestimony;

		DBObject metaData = new BasicDBObject();
		// private String imageFileId = "";
		String testimonyType = requestTestimony.getParameter("testimonyType");
		String author = requestTestimony.getParameter("author");
		String fileName = "testimony" + author;
		if (testimonyType.equalsIgnoreCase("videos")) {

			try {

				InputStream fileStreamVideo = file.getInputStream();
				metaData.put("type", "video");

				// Store video to MongoDB
				imageFileId = gridOperations.store(fileStreamVideo, fileName, "video/mp4", metaData).getId().toString();

				System.out.println("ImageFileId = " + imageFileId);
				// GridFSDBFile videoFile = gridOperations.findOne(new
				// Query(Criteria.where("_id").is(imageFileId)));

				testimony.setTestimonyType(testimonyType);
				testimony.setTestimonyContent(fileName);
				testimony.setAuthor(author);
				// testimony.setAuthor(member.getPseudonym());
				member = memberRepository.findByPseudonym(author);
				testimony.setAuthor(member.getPseudonym());
				testimonyRepository.insert(testimony);
				List<Testimony> testimonydb = testimonyRepository.findByTestimonyType("videos");
				// List<Testimony> tes = new ArrayList<Testimony>();
				// tes.add(testimonydb);
				member.setTestimonies(testimonydb);
				// member.getTestimonies().add(testimony);
				memberRepository.save(member);

				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(
						ucBuilder.path("/Member/addTestimony/{id}").buildAndExpand(testimony.getId()).toUri());

				return new ResponseEntity<String>(headers, HttpStatus.CREATED);

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Unable to create. A Testimony with name {} already exist",
						testimony.getTestimonyContent());
				return new ResponseEntity(new MemberErrorType("Unable to create. " + "A Testimony with name "
						+ testimony.getTestimonyContent() + " already exist"), HttpStatus.CONFLICT);
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
				logger.error("Unable to create. A Testimony with name {} already exist",
						testimony.getTestimonyContent());
				return new ResponseEntity(new MemberErrorType("Unable to create. " + "A Testimony with name "
						+ testimony.getTestimonyContent() + " already exist"), HttpStatus.CONFLICT);

			}
		}

	}

	/*
	 * Version Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
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

			
			String name = "testimony";
			Part part = null;


			part = requestTestimony.getPart("testimony");

			try {


				testimony.setTestimonyType(testimonyType);
				
				testimony.setAuthor(member.getPseudonym());
				member = memberRepository.findByPseudonym(author);
				testimony.setAuthor(member.getPseudonym());
				testimonyRepository.insert(testimony);
				List<Testimony> testimonydb = testimonyRepository.findByTestimonyType("videos");
				// List<Testimony> tes = new ArrayList<Testimony>();
				// tes.add(testimonydb);
				member.setTestimonies(testimonydb);
				// member.getTestimonies().add(testimony);
				memberRepository.save(member);

				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(
						ucBuilder.path("/Member/addTestimony/{id}").buildAndExpand(testimony.getId()).toUri());

				return new ResponseEntity<String>(headers, HttpStatus.CREATED);

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Unable to create. A Testimony with name {} already exist",
						testimony.getTestimonyContent());
				return new ResponseEntity(new MemberErrorType("Unable to create. " + "A Testimony with name "
						+ testimony.getTestimonyContent() + " already exist"), HttpStatus.CONFLICT);
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
				headers.setLocation(ucBuilder.path("/rencontre/Member/addTestimony/{id}")
						.buildAndExpand(testimony.getId()).toUri());

				return new ResponseEntity<String>(headers, HttpStatus.CREATED);

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Unable to create. A Testimony with name {} already exist",
						testimony.getTestimonyContent());
				return new ResponseEntity(new MemberErrorType("Unable to create. " + "A Testimony with name "
						+ testimony.getTestimonyContent() + " already exist"), HttpStatus.CONFLICT);

			}
		}

	}
	/*
	 * end add testimony
	 */

	/**
	 * Start send message
	 */
	/*
	 * version POST
	 */
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	@MessageMapping("/messages")
	@SendTo("/topic/messages")
	@Async
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> sendMessagePost(HttpServletRequest request) throws Exception {

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

		String messageContent = request.getParameter("messageContent");
		String topicExchange = request.getParameter("topicExchange");
		String receiver = request.getParameter("receiver");
		String sender = request.getParameter("sender");
		String sendingDate = new SimpleDateFormat("HH:mm").format(new Date());
		// HttpSession sessionMember = request.getSession();
		// Member member= (Member) sessionMember.getAttribute("Member");
		String idConversation = sender + "&" + receiver;

		Message messageDb = new Message();
		Conversation conversation = new Conversation();

		try {

			// messageDb.setSender(member.getPseudonym()); une session n'existe
			// pas encore
			messageDb.setSendingDate(DateTime.now());
			messageDb.setSender(sender);
			messageDb.setReceiver(receiver);
			messageDb.setMessageContent(messageContent);
			messageDb.setStatusMessage("Non lu");
			// messageRepository.deleteAll();
			messageRepository.save(messageDb);

			if (conversationRepository.exists(idConversation)) {

				// List<Message> messages = messageRepository
				// .findAll(new PageRequest(0, 1, Sort.Direction.DESC,
				// "sendingDate",sender,receiver)).getContent();
				// messages.add(messageDb);
				List<Message> messages = messageRepository.findBySenderOrderByReceiver(sender);
				List<String> members = new ArrayList<String>();
				members.add(sender);
				members.add(receiver);
				System.out.println(messages);
				System.out.println("je suis le if");
				conversation.setMessages(messages);
				conversation.setMembres(members);
				conversation.setStatusConversation("Non lu");
				conversation.setIdConversation(idConversation);
				conversation.setNewMessageNumber();

				conversationRepository.save(conversation);

				// this.webSocket.convertAndSend("/topic/sendMessage",
				// messageContent);

				// Member memberDb = memberRepository.findByPseudonym(receiver);

				if (memberRepository.findByPseudonym(receiver).getStatus().getStatusName().equals("disconnected")) {
					// this.webSocket.convertAndSend("/topic/sendMessage",
					// messageContent);

					String content1 = "you have received " + conversation.getNewMessageNumber() + " new message now.\n"
							+ "you can connect to view your messaging.";
					String subject1 = "Message";
					// String form="saphirmfogo@gmail.com";
					MimeMessage msg = new MimeMessage(session);
					/// msg.setFrom(new InternetAddress(form));
					msg.setRecipients(MimeMessage.RecipientType.TO,
							memberRepository.findByPseudonym(receiver).getEmailAdress());
					msg.setSubject(subject1);
					msg.setText(content1);
					msg.setSentDate(new Date());
					Transport transport = session.getTransport("smtp");
					transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
					transport.sendMessage(msg, msg.getAllRecipients());
					transport.close();

				} /*
					 * else {
					 * this.webSocket.convertAndSend("/topic/sendMessage",
					 * messageContent); }
					 */

				return new ResponseEntity<Conversation>(conversation, HttpStatus.OK);

			} else {

				List<Message> messages = new ArrayList<Message>();
				messages.add(messageDb);
				List<String> members = new ArrayList<String>();
				members.add(sender);
				members.add(receiver);
				System.out.println(members);
				System.out.println(messages);
				conversation.setIdConversation(idConversation);
				conversation.setMembres(members);
				conversation.setMessages(messages);
				conversation.setStatusConversation("Non lu");
				conversation.setNewMessageNumber();
				conversationRepository.insert(conversation);
				System.out.println("je suis le else");
				// this.webSocket.convertAndSend("/topic/sendMessage",
				// messageContent);
				System.out.println(receiver);
				// Member memberDb = memberRepository.findByPseudonym(receiver);

				// System.out.println(memberDb);

				if (memberRepository.findByPseudonym(receiver).getStatus().getStatusName().equals("disconnected")) {
					// this.webSocket.convertAndSend("/topic/sendMessage",
					// messageContent);

					String content1 = "you have received " + conversation.getNewMessageNumber() + " new message now.\n"
							+ "you can connect to view your messaging.";
					String subject1 = "Message";
					// String form="saphirmfogo@gmail.com";
					MimeMessage msg = new MimeMessage(session);
					/// msg.setFrom(new InternetAddress(form));
					msg.setRecipients(MimeMessage.RecipientType.TO,
							memberRepository.findByPseudonym(receiver).getEmailAdress());
					msg.setSubject(subject1);
					msg.setText(content1);
					msg.setSentDate(new Date());
					Transport transport = session.getTransport("smtp");
					transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
					transport.sendMessage(msg, msg.getAllRecipients());
					transport.close();

				} /*
					 * else {
					 * this.webSocket.convertAndSend("/topic/sendMessage",
					 * messageContent); }
					 */
				return new ResponseEntity<Conversation>(conversation, HttpStatus.OK);
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			logger.error("Unable to send message. A message can't be send");
			return new ResponseEntity(new MemberErrorType("Unable to send. A message can't be send"),
					HttpStatus.CONFLICT);

		}

	}

	/*
	 * version GET
	 */
	// @MessageMapping("/chat")
	// @SendTo("/topic/messages")
	@Async
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
	public ResponseEntity<?> sendMessageGet(HttpServletRequest request) throws Exception {

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

		String messageContent = request.getParameter("messageContent");
		String topicExchange = request.getParameter("topicExchange");
		String receiver = request.getParameter("receiver");
		String sender = request.getParameter("sender");
		String sendingDate = new SimpleDateFormat("HH:mm").format(new Date());
		// HttpSession sessionMember = request.getSession();
		// Member member= (Member) sessionMember.getAttribute("Member");
		String idConversation = sender + "&" + receiver;

		Message messageDb = new Message();
		Conversation conversation = new Conversation();

		try {

			// messageDb.setSender(member.getPseudonym()); une session n'existe
			// pas encore
			messageDb.setSendingDate(DateTime.now());
			messageDb.setSender(sender);
			messageDb.setReceiver(receiver);
			messageDb.setMessageContent(messageContent);
			messageDb.setStatusMessage("Non lu");
			// messageRepository.deleteAll();
			messageRepository.save(messageDb);

			if (conversationRepository.exists(idConversation)) {

				// List<Message> messages = messageRepository
				// .findAll(new PageRequest(0, 1, Sort.Direction.DESC,
				// "sendingDate",sender,receiver)).getContent();
				// messages.add(messageDb);
				List<Message> messages = messageRepository.findBySenderOrderByReceiver(sender);
				List<String> members = new ArrayList<String>();
				members.add(sender);
				members.add(receiver);
				System.out.println(messages);
				System.out.println("je suis le if");
				conversation.setMessages(messages);
				conversation.setMembres(members);
				conversation.setStatusConversation("Non lu");
				conversation.setIdConversation(idConversation);
				conversation.setNewMessageNumber();

				conversationRepository.save(conversation);

				// this.webSocket.convertAndSend("/topic/sendMessage",
				// messageContent);

				// Member memberDb = memberRepository.findByPseudonym(receiver);

				if (memberRepository.findByPseudonym(receiver).getStatus().getStatusName().equals("disconnected")) {
					// this.webSocket.convertAndSend("/topic/sendMessage",
					// messageContent);

					String content1 = "you have received " + conversation.getNewMessageNumber() + " new message now.\n"
							+ "you can connect to view your messaging.";
					String subject1 = "Message";
					// String form="saphirmfogo@gmail.com";
					MimeMessage msg = new MimeMessage(session);
					/// msg.setFrom(new InternetAddress(form));
					msg.setRecipients(MimeMessage.RecipientType.TO,
							memberRepository.findByPseudonym(receiver).getEmailAdress());
					msg.setSubject(subject1);
					msg.setText(content1);
					msg.setSentDate(new Date());
					Transport transport = session.getTransport("smtp");
					transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
					transport.sendMessage(msg, msg.getAllRecipients());
					transport.close();

				} /*
					 * else {
					 * this.webSocket.convertAndSend("/topic/sendMessage",
					 * messageContent); }
					 */

				return new ResponseEntity<Conversation>(conversation, HttpStatus.OK);

			} else {

				List<Message> messages = new ArrayList<Message>();
				messages.add(messageDb);
				List<String> members = new ArrayList<String>();
				members.add(sender);
				members.add(receiver);
				System.out.println(members);
				System.out.println(messages);
				conversation.setIdConversation(idConversation);
				conversation.setMembres(members);
				conversation.setMessages(messages);
				conversation.setStatusConversation("Non lu");
				conversation.setNewMessageNumber();
				conversationRepository.insert(conversation);
				System.out.println("je suis le else");
				// this.webSocket.convertAndSend("/topic/sendMessage",
				// messageContent);
				System.out.println(receiver);
				// Member memberDb = memberRepository.findByPseudonym(receiver);

				// System.out.println(memberDb);

				if (memberRepository.findByPseudonym(receiver).getStatus().getStatusName().equals("disconnected")) {
					// this.webSocket.convertAndSend("/topic/sendMessage",
					// messageContent);

					String content1 = "you have received " + conversation.getNewMessageNumber() + " new message now.\n"
							+ "you can connect to view your messaging.";
					String subject1 = "Message";
					// String form="saphirmfogo@gmail.com";
					MimeMessage msg = new MimeMessage(session);
					/// msg.setFrom(new InternetAddress(form));
					msg.setRecipients(MimeMessage.RecipientType.TO,
							memberRepository.findByPseudonym(receiver).getEmailAdress());
					msg.setSubject(subject1);
					msg.setText(content1);
					msg.setSentDate(new Date());
					Transport transport = session.getTransport("smtp");
					transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
					transport.sendMessage(msg, msg.getAllRecipients());
					transport.close();

				} /*
					 * else {
					 * this.webSocket.convertAndSend("/topic/sendMessage",
					 * messageContent); }
					 */
				return new ResponseEntity<Conversation>(conversation, HttpStatus.OK);
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			logger.error("Unable to send message. A message can't be send");
			return new ResponseEntity(new MemberErrorType("Unable to send. A message can't be send"),
					HttpStatus.CONFLICT);

		}

	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/chat")
	public String chat() {
		return "chat";
	}

	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	@MessageMapping("/newMessage")
	@SendTo("/topic/newMessage")
	public Message save(Message chatMessageModel, HttpServletRequest request) {

		String messageContent = request.getParameter("messageContent");
		String topicExchange = request.getParameter("topicExchange");
		String receiver = request.getParameter("receiver");
		String sender = request.getParameter("sender");
		String sendingDate = new SimpleDateFormat("HH:mm").format(new Date());
		// HttpSession sessionMember = request.getSession();
		// Member member= (Member) sessionMember.getAttribute("Member");

		// messageDb.setSender(member.getPseudonym()); une session n'existe
		// pas encore
		chatMessageModel.setSendingDate(DateTime.now());
		chatMessageModel.setSender(sender);
		chatMessageModel.setReceiver(receiver);
		chatMessageModel.setMessageContent(messageContent);
		chatMessageModel.setStatusMessage("Non lu");

		messageRepository.save(chatMessageModel);

		List<Message> chatMessageModelList = messageRepository
				.findAll(new PageRequest(0, 5, Sort.Direction.DESC, "sendingDate")).getContent();
		return new Message(chatMessageModelList.toString(), receiver, sendingDate, null);
		// return new Message(chatMessageModelList.toString());
	}

	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public HttpEntity list() {
		List<Message> chatMessageModelList = messageRepository
				.findAll(new PageRequest(0, 5, Sort.Direction.DESC, "sendingDate")).getContent();
		return new ResponseEntity(chatMessageModelList, HttpStatus.OK);
	}

	/*
	 * Methode de recherche des membres
	 */

	/*
	 * Version post
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchMemberWithPseudonym", method = RequestMethod.POST)
	public ResponseEntity<?> searchMemberWithPseudonymPost(HttpServletRequest request) throws Exception {

		// HttpSession sessionMember = request.getSession();
		// Member member = (Member) sessionMember.getAttribute("Member");
		// String pseudo= member.getPseudonym();
		String pseudo = request.getParameter("monPseudo");

		Member member = memberRepository.findByPseudonym(pseudo);

		String gender = member.getGender();

		String searhPseudonym = request.getParameter("pseudonym");

		String meetingNameConnexion = member.getMeetingNameConnexion();
		TypeMeeting typeMeetingSearch = typeMeetingRepository.findByMeetingName(meetingNameConnexion);

		TypeMeeting datingMeeting = typeMeetingRepository.findByMeetingName("Amoureuse");
		TypeMeeting professionnalMeeting = typeMeetingRepository.findByMeetingName("Professionnelle");
		TypeMeeting friendlyMeeting = typeMeetingRepository.findByMeetingName("Amicale");
		TypeMeeting schoolMeeting = typeMeetingRepository.findByMeetingName("Academique");
		// Member memberSearch=
		// memberRepository.findByPseudonym(searhPseudonym);

		String idChoose = searhPseudonym + typeMeetingSearch.getId();

		if (memberRepository.exists(searhPseudonym) && chooseMeetingRepository.exists(idChoose)) {

			String genderSearch = memberRepository.findByPseudonym(searhPseudonym).getGender();

			if (gender.equals(genderSearch)) {

				logger.error("Unable to find  member. The member " + searhPseudonym
						+ " exist but you can search member " + "with a same gender");
				return new ResponseEntity(new MemberErrorType("Unable to find  member. The member " + searhPseudonym
						+ " exist but you can search member " + "with a same gender"), HttpStatus.NOT_FOUND);
			} else {
				Member memberSearch = memberRepository.findByPseudonym(searhPseudonym);

				return new ResponseEntity<Member>(memberSearch, HttpStatus.OK);
			}

		} else {
			logger.error("Unable to find  member. The member " + searhPseudonym + " doest not exist");
			return new ResponseEntity(new MemberErrorType("Unable to find  member. The member " + searhPseudonym + " "
					+ "doest not exist in this type of meeting"), HttpStatus.NOT_ACCEPTABLE);
		}

	}

	/*
	 * Version post
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchMemberWithGenderAndBirtDate", method = RequestMethod.POST)
	public ResponseEntity<List<Member>> searchMemberWithGenderAndBirtDatePost(HttpServletRequest request)
			throws Exception {

		// HttpSession sessionMember = request.getSession();
		// Member member = (Member) sessionMember.getAttribute("Member");
		String pseudo = request.getParameter("monPseudo");

		Member member = memberRepository.findByPseudonym(pseudo);

		String genderMember = member.getGender();
		String meetingNameConnexion = member.getMeetingNameConnexion();
		TypeMeeting typeMeetingSearch = typeMeetingRepository.findByMeetingName(meetingNameConnexion);

		// String searhPseudonym = request.getParameter("pseudonym");
		String birthDateFrom = request.getParameter("birthDateFrom");
		String birthDateTo = request.getParameter("birthDateTo");
		String searchGender;
		// Member memberSearch=
		// memberRepository.findByPseudonym(searhPseudonym);

		if (genderMember.equals("femme")) {
			searchGender = "homme";
		} else {
			searchGender = "femme";
		}

		List<Member> listMember = memberRepository.findByGenderAndBirthDateBetween(searchGender, birthDateFrom,
				birthDateTo);
		List<Member> finalListMember = new ArrayList<Member>();
		List<Member> finalDefaultListMember = new ArrayList<Member>();
		if (listMember.isEmpty()) {
			System.out.println("if");
			List<Member> listMemberDefault = memberRepository.findByGender(searchGender);

			for (Member memberP : listMemberDefault) {
				if (chooseMeetingRepository.exists(memberP.getPseudonym() + typeMeetingSearch.getId())) {
					finalDefaultListMember.add(memberP);
				}
			}

			return new ResponseEntity(
					new MemberErrorType("Unable to find  member. The member with this birthDate doest not exist. "
							+ "but we propose you this another person"
							+ new ResponseEntity<List<Member>>(finalDefaultListMember, HttpStatus.OK)),
					HttpStatus.NOT_ACCEPTABLE);

		} else {

			for (Member memberP : listMember) {
				if (chooseMeetingRepository.exists(memberP.getPseudonym() + typeMeetingSearch.getId())) {
					finalListMember.add(memberP);
				}
			}
			if (finalListMember.isEmpty()) {
				List<Member> listMemberDefault = memberRepository.findByGender(searchGender);

				for (Member memberP : listMemberDefault) {
					if (chooseMeetingRepository.exists(memberP.getPseudonym() + typeMeetingSearch.getId())) {
						finalDefaultListMember.add(memberP);
					}
				}

				return new ResponseEntity(
						new MemberErrorType("Unable to find  member. The member with this birthDate doest not exist. "
								+ "but we propose you this another person"
								+ new ResponseEntity<List<Member>>(finalDefaultListMember, HttpStatus.OK)),
						HttpStatus.NOT_ACCEPTABLE);

			} else {
				return new ResponseEntity<List<Member>>(finalListMember, HttpStatus.OK);
			}

		}

	}

	/*
	 * methodes de modification du profil: je ferai la methode d'ajout ou
	 * monification de la photo a part
	 */
	/*
	 * Version post
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/savePicturessas", method = RequestMethod.POST)
	public ResponseEntity<?> savePicturesPost(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws UnknownHostException, Exception, FileNotFoundException {

		// HttpSession sessionMember = request.getSession();
		// Member member = (Member) sessionMember.getAttribute("Member");
		// String pseudonym= member.getPseudonym();
		String pseudonym = request.getParameter("monPseudo");
		Member member = memberRepository.findByPseudonym(pseudonym);

		DBObject metaData = new BasicDBObject();

		InputStream iamgeStream = file.getInputStream();
		metaData.put("type", "image");
		String pictureName = "picture" + pseudonym;

		// Store picture to MongoDB
		imageFileId = gridOperations.store(iamgeStream, pictureName, "image/png", metaData).getId().toString();

		System.out.println("ImageFileId = " + imageFileId);

		member.setPicture(pictureName);
		memberRepository.save(member);
		String status = "Upload has been successful";

		return new ResponseEntity<String>(status, HttpStatus.OK);
		// return Response.status(200).entity(status).build();

	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/downloadPicture", method = RequestMethod.GET)
	public ResponseEntity<?> downloadPicturePost( HttpServletRequest request)
			throws UnknownHostException, Exception, FileNotFoundException {

		String SAVE_DIR_TESTIMONY = "/home/saphir/test1/";
		File fileWay = new File(SAVE_DIR_TESTIMONY);
		if (!fileWay.exists()){
			fileWay.mkdir();}
		// HttpSession sessionMember = request.getSession();
		// Member member = (Member) sessionMember.getAttribute("Member");
		// String pseudonym= member.getPseudonym();
		String pseudonym = request.getParameter("monPseudo");
		Member member = memberRepository.findByPseudonym(pseudonym);
		
		
		
		DBObject metaData = new BasicDBObject();

		//InputStream iamgeStream = file.getInputStream();
		metaData.put("type", "image");
		
		//String pictureName = "picture" + pseudonym;
		String pictureName ="picture"+pseudonym;
		
		
		GridFSDBFile pictureFile = gridOperations.findOne(new
		 Query(Criteria.where("filename").is(pictureName)));
		System.out.println(pictureFile);
		System.out.println(fileWay+ File.separator + pictureName);
		pictureFile.writeTo(fileWay+ File.separator + pictureName);
		
		// Store picture to MongoDB
		//imageFileId = gridOperations.store(iamgeStream, pictureName, "image/png", metaData).getId().toString();

		System.out.println("ImageFileId = " + imageFileId);

		//member.setPicture(pictureName);
		//memberRepository.save(member);
		String status = "Upload has been successful";

		return new ResponseEntity<String>(status, HttpStatus.OK);
		// return Response.status(200).entity(status).build();

	}

	
	
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public ResponseEntity<?> updateProfilePost(HttpServletRequest request) throws Exception {

		// HttpSession sessionMember = request.getSession();
		// Member member = (Member) sessionMember.getAttribute("Member");
		// String pseudonym= member.getPseudonym();
		String pseudonym = request.getParameter("monPseudo");
		Member member = memberRepository.findByPseudonym(pseudonym);
		String typeMeeting = member.getMeetingNameConnexion();
		String imageFileId = "";

		if (typeMeeting.equals("Amoureuse")) {

			String fatherName = request.getParameter("fatherName");
			String motherName = request.getParameter("motherName");
			String motherProfession = request.getParameter("motherProfession");
			String fatherProfession = request.getParameter("fatherProfession");
			DatingInformation datingInformation = new DatingInformation();

			datingInformation.setFatherName(fatherName);
			datingInformation.setMotherName(motherName);
			datingInformation.setFatherProfession(fatherProfession);
			datingInformation.setMotherProfession(motherProfession);

			member.setDatingInformation(datingInformation);
			memberRepository.save(member);

			return new ResponseEntity<Member>(member, HttpStatus.OK);

		} else if (typeMeeting.equals("Professionnelle")) {

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String profession = request.getParameter("profession");
			String levelStudy = request.getParameter("levelStudy");

			ProfessionalMeetingInformation professionnalMeeting = new ProfessionalMeetingInformation();
			professionnalMeeting.setFirstName(firstName);
			professionnalMeeting.setLastName(lastName);
			professionnalMeeting.setLevelStudy(levelStudy);
			professionnalMeeting.setProfession(profession);

			member.setProfessionalMeetingInformation(professionnalMeeting);
			memberRepository.save(member);
			return new ResponseEntity<Member>(member, HttpStatus.OK);

		} else if (typeMeeting.equals("Academique")) {

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String schoolName = request.getParameter("schoolName");
			String levelStudy = request.getParameter("levelStudy");

			AcademicDatingInformation academicDating = new AcademicDatingInformation();

			academicDating.setFirstName(firstName);
			academicDating.setLastName(lastName);
			academicDating.setLevelStudy(levelStudy);
			academicDating.setSchoolName(schoolName);

			member.setAcademicDatingInformation(academicDating);
			memberRepository.save(member);
			return new ResponseEntity<Member>(member, HttpStatus.OK);

		} else if (typeMeeting.equals("Amicale")) {

		}

		return null;
	}

	/*
	 * Version get
	 */
	@RequestMapping(value = "/updateProfile", method = RequestMethod.GET)
	public ResponseEntity<?> updateProfileGet(HttpServletRequest request) throws Exception {

		// HttpSession sessionMember = request.getSession();
		// Member member = (Member) sessionMember.getAttribute("Member");
		// String pseudonym= member.getPseudonym();
		String pseudonym = request.getParameter("monPseudo");
		Member member = memberRepository.findByPseudonym(pseudonym);
		String typeMeeting = member.getMeetingNameConnexion();
		String imageFileId = "";

		if (typeMeeting.equals("Amoureuse")) {

			String fatherName = request.getParameter("fatherName");
			String motherName = request.getParameter("motherName");
			String motherProfession = request.getParameter("motherProfession");
			String fatherProfession = request.getParameter("fatherProfession");
			DatingInformation datingInformation = new DatingInformation();

			datingInformation.setFatherName(fatherName);
			datingInformation.setMotherName(motherName);
			datingInformation.setFatherProfession(fatherProfession);
			datingInformation.setMotherProfession(motherProfession);

			member.setDatingInformation(datingInformation);
			memberRepository.save(member);

			return new ResponseEntity<Member>(member, HttpStatus.OK);

		} else if (typeMeeting.equals("Professionnelle")) {

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String profession = request.getParameter("profession");
			String levelStudy = request.getParameter("levelStudy");

			ProfessionalMeetingInformation professionnalMeeting = new ProfessionalMeetingInformation();
			professionnalMeeting.setFirstName(firstName);
			professionnalMeeting.setLastName(lastName);
			professionnalMeeting.setLevelStudy(levelStudy);
			professionnalMeeting.setProfession(profession);

			member.setProfessionalMeetingInformation(professionnalMeeting);
			memberRepository.save(member);
			return new ResponseEntity<Member>(member, HttpStatus.OK);

		} else if (typeMeeting.equals("Academique")) {

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String schoolName = request.getParameter("schoolName");
			String levelStudy = request.getParameter("levelStudy");

			AcademicDatingInformation academicDating = new AcademicDatingInformation();

			academicDating.setFirstName(firstName);
			academicDating.setLastName(lastName);
			academicDating.setLevelStudy(levelStudy);
			academicDating.setSchoolName(schoolName);

			member.setAcademicDatingInformation(academicDating);
			memberRepository.save(member);
			return new ResponseEntity<Member>(member, HttpStatus.OK);

		} else if (typeMeeting.equals("Amicale")) {

		}

		return null;
	}

	/*
	 * methode envoyer une demande d'amite et couple
	 */
	/*
	 * Version Post
	 */
	@RequestMapping(value = "/sendAnInquiry", method = RequestMethod.POST)
	@MessageMapping("/request")
	@SendTo("/topic/request")
	@Async
	public ResponseEntity<?> send(HttpServletRequest request) throws Exception {

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

		// HttpSession sessionMember = request.getSession();
		// Member member = (Member) sessionMember.getAttribute("Member");
		// String pseudonym= member.getPseudonym();
		String pseudonymMember = request.getParameter("monPseudo");
		Member memberSender = memberRepository.findByPseudonym(pseudonymMember);
		String typeMeeting = memberSender.getMeetingNameConnexion();
		String pseudonym = request.getParameter("pseudonym");
		Member member = memberRepository.findByPseudonym(pseudonymMember);
		String statut = member.getStatus().getStatusName();

		String idFriendly = pseudonymMember + pseudonym + typeMeeting;
		String friendlyStatut = "demande envoye";

		Friendly friendly = new Friendly();

		friendly.setIdFriendly(idFriendly);
		friendly.setFriendlyStatut(friendlyStatut);

		freindlyRepository.save(friendly);

		if (statut.equals("disconnected")) {

			String content1 = "vous venez de recevoir une demande de relation " + typeMeeting + "\n"
					+ "c'est peut etre votre jour de chance connectez vous maintenant ";
			String subject1 = "Notification";
			// String form="saphirmfogo@gmail.com";
			MimeMessage msg = new MimeMessage(session);
			/// msg.setFrom(new InternetAddress(form));
			msg.setRecipients(MimeMessage.RecipientType.TO,
					memberRepository.findByPseudonym(pseudonym).getEmailAdress());
			msg.setSubject(subject1);
			msg.setText(content1);
			msg.setSentDate(new Date());
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", "saphirmfogo@gmail.com", "meilleure");
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

		}

		return new ResponseEntity<Friendly>(friendly, HttpStatus.OK);
	}
}