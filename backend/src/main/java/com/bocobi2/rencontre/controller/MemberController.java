
package com.bocobi2.rencontre.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bocobi2.rencontre.model.Conversation;
import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.MemberErrorType;
import com.bocobi2.rencontre.model.Message;
import com.bocobi2.rencontre.model.Status;
import com.bocobi2.rencontre.model.Testimony;
import com.bocobi2.rencontre.repositories.ConversationRepository;
import com.bocobi2.rencontre.repositories.MemberBufferRepository;
import com.bocobi2.rencontre.repositories.MemberRepository;
import com.bocobi2.rencontre.repositories.MessageRepository;
import com.bocobi2.rencontre.repositories.StatusRepository;
import com.bocobi2.rencontre.repositories.TestimonyRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rencontre/Member")

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	private static final String SAVE_DIR_TESTIMONY = "/home/saphir/test1/workspaceGit/rencontre/backend/src/main/resources/UploadFile/UploadTestimony";
	// private static final String SAVE_DIR_PICTURE =
	// "/home/saphir/test1/workspaceGit/rencontre/backend/src/main/resources/UploadFile/UploadPicture";

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
	private SimpMessagingTemplate webSocket;

	public MemberController(SimpMessagingTemplate webSocket) {
		this.webSocket = webSocket;
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
	@RequestMapping(value = "/Connexion", method = RequestMethod.POST)
	public ResponseEntity<?> connexionMemberPost(HttpServletRequest requestConnexion) {

		HttpSession session = requestConnexion.getSession();
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
	@RequestMapping(value = "/Connexion", method = RequestMethod.GET)
	public ResponseEntity<?> connexionMemberGet(HttpServletRequest requestConnexion) {

		HttpSession session = requestConnexion.getSession();

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

	/**
	 * Method change status
	 */
	/*
	 * VERSION Post
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public ResponseEntity<?> changeStatusPost(HttpServletRequest request) {

		try {
			String statusName = request.getParameter("statusName");
			Status status = statusRepository.findByStatusName(statusName);
			System.out.println(status);
			String pseudonym = request.getParameter("pseudonym");

			// HttpSession session= request.getSession();
			// Member member= (Member) session.getAttribute("Member");

			Member member = memberRepository.findByPseudonym(pseudonym);
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
			String pseudonym = request.getParameter("pseudonym");

			// HttpSession session= request.getSession();
			// Member member= (Member) session.getAttribute("Member");

			Member member = memberRepository.findByPseudonym(pseudonym);
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
	public ResponseEntity<?> addTestimonyPost(HttpServletRequest requestTestimony, UriComponentsBuilder ucBuilder)
			throws IOException, ServletException {
		HttpSession session = requestTestimony.getSession();
		Member member = (Member) session.getAttribute("Member");

		Testimony testimony = new Testimony();
		// String resultTestimony;

		String testimonyType = requestTestimony.getParameter("testimonyType");
		String author = requestTestimony.getParameter("author");

		if (testimonyType.equalsIgnoreCase("videos")) {

			File fileWay = new File(SAVE_DIR_TESTIMONY);
			String name = "testimony" + author + ".avi";
			Part part = null;
			if (!fileWay.exists())
				fileWay.mkdir();

			part = requestTestimony.getPart("testimony");

			try {

				String fileName = SAVE_DIR_TESTIMONY + File.separator + name;
				part.write(SAVE_DIR_TESTIMONY + File.separator + name);

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

		HttpSession sessionMember = request.getSession();
		Member member = (Member) sessionMember.getAttribute("Member");
		String gender = member.getGender();
		String searhPseudonym = request.getParameter("pseudonym");

		// Member memberSearch=
		// memberRepository.findByPseudonym(searhPseudonym);

		if (memberRepository.exists(searhPseudonym)) {

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
			return new ResponseEntity(
					new MemberErrorType("Unable to find  member. The member " + searhPseudonym + " doest not exist"),
					HttpStatus.NO_CONTENT);
		}

	}
}