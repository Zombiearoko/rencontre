
package com.bocobi2.rencontre.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bocobi2.rencontre.model.Conversation;
import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.MemberBuffer;
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
//@Resource(name="myMessageQueue",type="javax.jms.ConnectionFactory")

@MultipartConfig(fileSizeThreshold=1024*1024*2,maxFileSize=1024*1024*10,maxRequestSize=1024*1024*50)
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);


	private static final String SAVE_DIR_TESTIMONY="/home/saphir/test1/workspaceGit/rencontre/backend/src/main/resources/UploadFile/UploadTestimony";
	private static final String SAVE_DIR_PICTURE="/home/saphir/test1/workspaceGit/rencontre/backend/src/main/resources/UploadFile/UploadPicture";

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

	/**
	 * connexion of the member
	 * 
	 * cette methode permet de connecter un membre dans une session
	 */

	/**
	 * VERSION POST
	 * @param request
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value="/choiseRencontre", method = RequestMethod.POST)
	public ResponseEntity<?>  choiseRencontrePost(HttpServletRequest request,UriComponentsBuilder ucBuilder ){

		String birthDate= request.getParameter("bithDate");
		String gender=  request.getParameter("gender");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int birthYear=0;
		try{
			birthYear= new Integer(birthDate);
			int age= year-birthYear;
			if(age<18){
				String  typeMeeting="Amicale";
			}else{
				String  typeMeeting=request.getParameter("typeMeeting");
			}
		}catch(NumberFormatException numberEx){

		}

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	/**
	 * VERSION GET
	 */
	/*@RequestMapping(value="/choiseRencontre", method = RequestMethod.GET)
	    public ResponseEntity<?>  choiseRencontreGet(HttpServletRequest request,UriComponentsBuilder ucBuilder ){

			String bithDate= request.getParameter("bithDate");
			String gender=  request.getParameter("gender");
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			if(bithDate.regionMatches(year, "1980", year, 50)){

				 return new ResponseEntity(HttpStatus.OK);
			}

			 return new ResponseEntity(HttpStatus.NO_CONTENT);
		}*/

	/**** registration member in the data base
	 * methode qui gere l'enregistrement d'un membre dans la bd 
	 * 
	 */
	@SuppressWarnings("unchecked")
	
	/**
	 * connexion of the member
	 * 
	 * cette methode permet de connecter un membre dans une session

	 */
	/*
	 * Version POST
	 */
	

	@RequestMapping(value="/Connexion", method= RequestMethod.POST)
	public ResponseEntity<?> connexionMemberPost(HttpServletRequest requestConnexion){

		HttpSession session = requestConnexion.getSession();
		//String connexionResult;
		//recuperation des champs de connexion
		String pseudonym= requestConnexion.getParameter("pseudonym");
		String password= requestConnexion.getParameter("password");
		System.out.println("-------------------------------");
		System.out.println(pseudonym);
		System.out.println("-------------------------------");

		System.out.println("-------------------------------");
		System.out.println(password);
		//recherche du membre dans la base de donnees
		try{
			Member member =new Member();
			member=memberRepository.findByPseudonym(pseudonym);
			if(member!=null){
				if(member.getPassword().equals(password)){
					session.setAttribute("Member", member);
					return new ResponseEntity<Member>(member, HttpStatus.OK);
				}else{
					session.setAttribute("Member", null);
					logger.error("Member with password {} not found.", password);
					return new ResponseEntity(new MemberErrorType("Member with "
							+ "password "+ password+" not found."),HttpStatus.NOT_FOUND);
				}
			}else{
				logger.error("Member with password {} not found.", pseudonym);
				return new ResponseEntity(new MemberErrorType("Member with "
						+ "pseudonym "+ pseudonym+" not found."),HttpStatus.NOT_FOUND);

			}
		}catch(Exception ex){
			logger.error("Member with pseudonym {} not found.", pseudonym);
			return new ResponseEntity(new MemberErrorType("Member with "
					+ "pseudonym "+ pseudonym+" not found."),HttpStatus.NOT_FOUND);
		}




	}

	/*
	 * version Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/Connexion", method=RequestMethod.GET)
	public ResponseEntity<?> connexionMemberGet(HttpServletRequest requestConnexion){

		HttpSession session = requestConnexion.getSession();

		//String connexionResult;
		//recuperation des champs de connexion
		String pseudonym= requestConnexion.getParameter("pseudonym");
		String password= requestConnexion.getParameter("password");
		System.out.println("-------------------------------");
		System.out.println(pseudonym);
		System.out.println("-------------------------------");

		System.out.println("-------------------------------");
		System.out.println(password);
		//recherche du membre dans la base de donnees
		try{
			Member member =new Member();
			member=memberRepository.findByPseudonym(pseudonym);
			if(member!=null){
				if(member.getPassword().equals(password)){
					session.setAttribute("Member", member);
					return new ResponseEntity<Member>(member, HttpStatus.OK);
				}else{
					session.setAttribute("Member", null);
					logger.error("Member with password {} not found.", password);
					return new ResponseEntity(new MemberErrorType("Member with "
							+ "password "+ password+" not found."),HttpStatus.NOT_FOUND);
				}
			}else{
				logger.error("Member with password {} not found.", pseudonym);
				return new ResponseEntity(new MemberErrorType("Member with "
						+ "pseudonym "+ pseudonym+" not found."),HttpStatus.NOT_FOUND);

			}
		}catch(Exception ex){
			logger.error("Member with pseudonym {} not found.", pseudonym);
			return new ResponseEntity(new MemberErrorType("Member with "
					+ "pseudonym "+ pseudonym+" not found."),HttpStatus.NOT_FOUND);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/addTestimony", method= RequestMethod.POST)
	public  ResponseEntity<?> addTestimonyPost(HttpServletRequest requestTestimony, UriComponentsBuilder ucBuilder) throws IOException, ServletException {
		HttpSession session= requestTestimony.getSession();
		Member member= (Member) session.getAttribute("Member");

		Testimony testimony=new Testimony();
		//String resultTestimony;

		String testimonyType=requestTestimony.getParameter("testimonyType");
		String author=requestTestimony.getParameter("author");



		if(testimonyType.equalsIgnoreCase("videos")){


			File fileWay= new File(SAVE_DIR_TESTIMONY);
			String name="testimony"+author+".avi";
			Part part=null;
			if(!fileWay.exists()) fileWay.mkdir();

			part=requestTestimony.getPart("testimony");

			try{

				String fileName=SAVE_DIR_TESTIMONY + File.separator + name;
				part.write(SAVE_DIR_TESTIMONY + File.separator + name);

				testimony.setTestimonyType(testimonyType);
				testimony.setTestimonyContent(fileName);
				testimony.setAuthor(author);
				//testimony.setAuthor(member.getPseudonym());
				member=memberRepository.findByPseudonym(author);
				testimony.setAuthor(member.getPseudonym());
				testimonyRepository.insert(testimony);
				List<Testimony> testimonydb= testimonyRepository.findByTestimonyType("videos");
				//List<Testimony> tes = new ArrayList<Testimony>();
				//tes.add(testimonydb);
				member.setTestimonies(testimonydb);
				//member.getTestimonies().add(testimony);
				memberRepository.save(member);

				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(ucBuilder.path("/Member/addTestimony/{id}").buildAndExpand(testimony.getId()).toUri());


				return new ResponseEntity<String>(headers, HttpStatus.CREATED);

			}catch (Exception ex){
				ex.printStackTrace();
				logger.error("Unable to create. A Testimony with name {} already exist", testimony.getTestimonyContent());
				return new ResponseEntity(new MemberErrorType("Unable to create. "
						+ "A Testimony with name "+testimony.getTestimonyContent()+" already exist"),HttpStatus.CONFLICT);
			}
		}else{
			String testimonyContent= requestTestimony.getParameter("testimonyContent");
			try{


				testimony.setTestimonyType(testimonyType);
				testimony.setTestimonyContent(testimonyContent);
				testimony.setAuthor(member.getPseudonym());
				testimonyRepository.insert(testimony);
				member.getTestimonies().add(testimony);

				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(ucBuilder.path("/Member/addTestimony/{id}").buildAndExpand(testimony.getId()).toUri());


				return new ResponseEntity<String>(headers, HttpStatus.CREATED);

			}catch (Exception ex){
				ex.printStackTrace();
				logger.error("Unable to create. A Testimony with name {} already exist", testimony.getTestimonyContent());
				return new ResponseEntity(new MemberErrorType("Unable to create. "
						+ "A Testimony with name "+testimony.getTestimonyContent()+" already exist"),HttpStatus.CONFLICT);


			}
		}			


	}
	/**
	 * Start send message
	 */
	/*
	 * VERSION POST
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/sendMessage", method=RequestMethod.POST)
	public ResponseEntity<?> sendMessagePost(HttpServletRequest request, UriComponentsBuilder ucBuilder) throws Exception {

		String messageContent = request.getParameter("messageContent");
		String topicExchange= request.getParameter("topicExchange");
		String receiver = request.getParameter("receiver");
		String sender = request.getParameter("sender");
		Date date =new Date();
		System.out.println(date.getDay()+"/"+date.getMonth()+"/"+date.getYear()+ "A" +date.getHours()+":"+date.getMinutes());
		//HttpSession sessionMember = request.getSession();
		//Member member= (Member) sessionMember.getAttribute("Member");

		try{
			ConnectionFactory cf = new CachingConnectionFactory();

			// set up the queue, exchange, binding on the broker
			RabbitAdmin admin = new RabbitAdmin(cf);
			Queue queue = new Queue("myQueue");
			admin.declareQueue(queue);
			TopicExchange exchange = new TopicExchange(topicExchange);
			admin.declareExchange(exchange);
			//admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("foo.*"));
			admin.declareBinding(
					BindingBuilder.bind(queue).to(exchange).with(receiver));

			// set up the listener and container
			SimpleMessageListenerContainer container =
					new SimpleMessageListenerContainer(cf);
			Object listener = new Object() {
				public void handleMessage(String foo) {
					System.out.println(foo);
				}
			};
			MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
			container.setMessageListener(adapter);
			container.setQueueNames("myQueue");
			container.start();

			// send something
			RabbitTemplate template = new RabbitTemplate(cf);
			template.convertAndSend(topicExchange, receiver, messageContent);
			Message messageDb =new Message();
			//messageDb.setSender(member.getPseudonym()); une session n'existe pas encore
			messageDb.setSender(sender);
			messageDb.setReceiver(receiver);
			messageDb.setMessageContent(messageContent);
			messageRepository.insert(messageDb);

			Thread.sleep(1000);
			container.stop();

			return new ResponseEntity<Message>(messageDb, HttpStatus.OK);
			//HttpHeaders headers = new HttpHeaders();
			//headers.setLocation(ucBuilder.path("/rencontre/Member/sendMessage/{id}").buildAndExpand(messageDb.getIdMessage()).toUri());
			//return new ResponseEntity<String>(headers, HttpStatus.CREATED);

		}catch(Exception e){
			logger.error("Unable to send sender. A message can't be send");
			return new ResponseEntity(new MemberErrorType("Unable to send. A message can't be send"),HttpStatus.CONFLICT);

		}




	}
	/*
	 * VERSION Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/sendMessage", method=RequestMethod.GET)
	public ResponseEntity<?> sendMessageGet(HttpServletRequest request, UriComponentsBuilder ucBuilder) throws Exception {

		String messageContent = request.getParameter("messageContent");
		String topicExchange= request.getParameter("topicExchange");
		String receiver = request.getParameter("receiver");
		String sender = request.getParameter("sender");
		Date date =new Date();
		System.out.println(date.getDay()+"/"+date.getMonth()+"/"+date.getYear()+ "A" +date.getHours()+":"+date.getMinutes());
		//HttpSession sessionMember = request.getSession();
		//Member member= (Member) sessionMember.getAttribute("Member");

		try{
			ConnectionFactory cf = new CachingConnectionFactory();

			// set up the queue, exchange, binding on the broker
			RabbitAdmin admin = new RabbitAdmin(cf);
			Queue queue = new Queue("myQueue");
			admin.declareQueue(queue);
			TopicExchange exchange = new TopicExchange(topicExchange);
			admin.declareExchange(exchange);
			//admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("foo.*"));
			admin.declareBinding(
					BindingBuilder.bind(queue).to(exchange).with(receiver));

			// set up the listener and container
			SimpleMessageListenerContainer container =
					new SimpleMessageListenerContainer(cf);
			Object listener = new Object() {
				public void handleMessage(String foo) {
					System.out.println(foo);
				}
			};
			MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
			container.setMessageListener(adapter);
			container.setQueueNames("myQueue");
			container.start();

			
			// send something
			RabbitTemplate template = new RabbitTemplate(cf);
			template.convertAndSend(topicExchange, receiver, messageContent);
			Message messageDb =new Message();
			//messageDb.setSender(member.getPseudonym()); une session n'existe pas encore
			messageDb.setSender(sender);
			messageDb.setReceiver(receiver);
			messageDb.setMessageContent(messageContent);
			messageRepository.insert(messageDb);

			Thread.sleep(1000);
			container.stop();

			return new ResponseEntity<Message>(messageDb, HttpStatus.OK);
			//HttpHeaders headers = new HttpHeaders();
			//headers.setLocation(ucBuilder.path("/rencontre/Member/sendMessage/{id}").buildAndExpand(messageDb.getIdMessage()).toUri());
			//return new ResponseEntity<String>(headers, HttpStatus.CREATED);

		}catch(Exception e){
			logger.error("Unable to send sender. A message can't be send");
			return new ResponseEntity(new MemberErrorType("Unable to send. A message can't be send"),HttpStatus.CONFLICT);

		}
	}
}

