package com.bocobi2.rencontre.controller;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
//import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.MemberErrorType;
import com.bocobi2.rencontre.model.Messages;
import com.bocobi2.rencontre.repositories.MessageRepository;

@RestController
@RequestMapping("/Member/Messages")
public class MessageController {
	
	//public static final Logger logger = LoggerFactory.getLogger(MessageController.class);
		@Autowired
		MessageRepository messageRepository;
		

		
		/**
		 * Start  send message
		 */
		
		
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value="/sendMessage", method=RequestMethod.POST)
		public ResponseEntity<?> sendMessage(HttpServletRequest request, UriComponentsBuilder ucBuilder) throws IOException, ServletException {
			
			String messageContent = request.getParameter("messageContent");
			String receiver = request.getParameter("receiver");
			HttpSession sessionMember = request.getSession();
			Member member= (Member) sessionMember.getAttribute("Member");
			
			Context jndiContext = null;
			ConnectionFactory connectionFactory = null;
			Connection connection = null;
			Session session = null;
			Destination destination = null;
			MessageProducer producer = null;
			
			
			
			try {
				jndiContext = new InitialContext();
				connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
				destination = (Destination) jndiContext.lookup(receiver);
			} catch (NamingException e) {
				e.printStackTrace();
				System.exit(1);
			}
			
			try {
				connection = connectionFactory.createConnection();
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				producer = session.createProducer(destination);
				TextMessage message = session.createTextMessage();
				message.setText(messageContent);
				System.out.println("Sending message: " + message.getText());
				producer.send(message);
				Messages messageDb =new Messages();
				messageDb.setSender(member.getPseudonym());
				messageDb.setReceiver(receiver);
				messageDb.setMessageContent(messageContent);
				messageRepository.insert(messageDb);
				
				HttpHeaders headers = new HttpHeaders();
		        headers.setLocation(ucBuilder.path("/Member/Messages/sendMessage/{id}").buildAndExpand(messageDb.getIdMessage()).toUri());
		        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
			} catch (JMSException e) {
				e.printStackTrace();
				//logger.error("Unable to send. A message can't be send");
				return new ResponseEntity(new MemberErrorType("Unable to send. A message can't be send"),HttpStatus.CONFLICT);
			} finally {
				try {
					jndiContext.close();
				} catch (NamingException e1) {
				}
				if (connection != null) {
					try {
						connection.stop();
						connection.close();
					} catch (JMSException e) {
					}
			
		}
		
	
			}
			
	        //headers.setLocation(ucBuilder.path("/Member/sendMessage/{id}").buildAndExpand(testimony.getId()).toUri());
			
	        
	        
		}
		/***
		 * Receive message
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value="/receiveMessage", method=RequestMethod.POST)
		public ResponseEntity<?> receiveMessage(HttpServletRequest request, UriComponentsBuilder ucBuilder) throws IOException, ServletException {
			String receiver = request.getParameter("receiver");
			Context jndiContext = null;
			ConnectionFactory connectionFactory = null;
			Connection connection = null;
			Session session = null;
			Destination destination = null;
			MessageConsumer consumer = null;
			String destinationName = null;

			

			try {
				jndiContext = new InitialContext();
				connectionFactory = (ConnectionFactory) jndiContext
						.lookup("ConnectionFactory");
				destination = (Destination) jndiContext.lookup(receiver);
			} catch (NamingException e) {
				e.printStackTrace();
				System.exit(1);
			}

			try {
				connection = connectionFactory.createConnection();
				connection.start();
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				consumer = session.createConsumer(destination);

				while (true) {
					Message message = consumer.receive(2000);
					if (message == null) {
						System.out.println("waiting...");
						continue;
					}
					if (message instanceof TextMessage) {
						TextMessage txtMessage = (TextMessage) message;
						
						System.out.println("Message received: " + txtMessage.getText());
					} else {
						System.out.println("Invalid message received.");
					}
					Thread.sleep(100);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					jndiContext.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			return null;
	
		
		
		}
	}
		
