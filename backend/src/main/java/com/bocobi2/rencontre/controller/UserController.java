package com.bocobi2.rencontre.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bocobi2.rencontre.model.JwtAuthenticationResponse;
import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.Status;
import com.bocobi2.rencontre.repositories.MemberRepository;
import com.bocobi2.rencontre.repositories.StatusRepository;
import com.bocobi2.rencontre.util.JwtTokenUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	StatusRepository statusRepository;
	
//	@Autowired
//	UserDetailsServices use;
	
	@Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("userDetailsServices")
    private UserDetailsService use;
	
	public static String decryptographe(String password) {
		String aCrypter = "";
		for (int i = 0; i < password.length(); i++) {
			int c = password.charAt(i) ^ 48;
			aCrypter = aCrypter + (char) c;
		}

		return aCrypter;
	}
	
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> loginPost( HttpServletRequest req) throws AuthenticationException{
    	
		String name=req.getParameter("pseudonym");
		String pass=req.getParameter("password");
		String meetingName=req.getParameter("meetingName");
		
		
		
    	Member member = new Member();
		member = memberRepository.findByPseudonym(name);
		System.out.println("hors  member!= null");
		if (member!= null) {
			System.out.println("dans  member!= null");
			String passw = decryptographe(member.getPasswordSec());
			
			System.out.println(passw);
			System.out.println(pass);
			System.out.println(name);
			System.out.println(meetingName);
			
			if (passw.equals(pass)) {
				
				
				try{
					
					String status = "connected";
					Status statusDB = statusRepository.findByStatusName(status);
					System.out.println(statusDB);
					member.setStatus(statusDB);
					member.setMeetingNameConnexion(meetingName);
					UserDetails users = use.loadUserByUsername(name);
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(users, null,
							users.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authToken);
					System.out.println("Auth reussi");
					System.out.println("generate token laoding...");
					 final String token = jwtTokenUtil.generateToken(users);
					 System.out.println("done");
					memberRepository.save(member);
					
					System.out.println(new LoginResponse(Jwts.builder().setSubject(name)
				            .claim("roles", member.getRoles()).setIssuedAt(new Date())
				            .signWith(SignatureAlgorithm.HS256, "secretkey").compact()).token);
					 return ResponseEntity.ok(new JwtAuthenticationResponse(token));
					
				}catch(Exception ex){
					System.out.println("catch");
				}
					
			}else{
				 return ResponseEntity.badRequest().body("Invalid password");
			}
			
			}else{
				 return ResponseEntity.badRequest().body("Invalid pseudonym");
				}
		return null;
    }
	
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
    public ResponseEntity<?> loginGet( HttpServletRequest req) throws AuthenticationException{
    	
		String name=req.getParameter("pseudonym");
		String pass=req.getParameter("password");
		String meetingName=req.getParameter("meetingName");
		
		
		
    	Member member = new Member();
		member = memberRepository.findByPseudonym(name);
		System.out.println("hors  member!= null");
		if (member!= null) {
			System.out.println("dans  member!= null");
			String passw = decryptographe(member.getPasswordSec());
			
			System.out.println(passw);
			System.out.println(pass);
			System.out.println(name);
			System.out.println(meetingName);
			
			if (passw.equals(pass)) {
				
				
				try{
					
					String status = "connected";
					Status statusDB = statusRepository.findByStatusName(status);
					System.out.println(statusDB);
					member.setStatus(statusDB);
					member.setMeetingNameConnexion(meetingName);
					UserDetails users = use.loadUserByUsername(name);
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(users, null,
							users.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authToken);
					System.out.println("Auth reussi");
					System.out.println("generate token laoding...");
					 final String token = jwtTokenUtil.generateToken(users);
					 System.out.println("done");
					memberRepository.save(member);
					
					System.out.println(new LoginResponse(Jwts.builder().setSubject(name)
				            .claim("roles", member.getRoles()).setIssuedAt(new Date())
				            .signWith(SignatureAlgorithm.HS256, "secretkey").compact()).token);
					 return ResponseEntity.ok(new JwtAuthenticationResponse(token));
					
				}catch(Exception ex){
					System.out.println("catch");
				}
					
			}else{
				 return ResponseEntity.badRequest().body("Invalid password");
			}
			
			}else{
				 return ResponseEntity.badRequest().body("Invalid pseudonym");
				}
		return null;
    }
	
	 @RequestMapping(value = "refreshToken", method = RequestMethod.GET)
	    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
	        String authToken = request.getHeader(tokenHeader);
	        final String token = authToken.substring(7);
	        String username = jwtTokenUtil.getUsernameFromToken(token);
	        Member user = (Member) use.loadUserByUsername(username);

	        if (jwtTokenUtil.canTokenBeRefreshed(token,new Date() )) {
	            String refreshedToken = jwtTokenUtil.refreshToken(token);
	            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
	        } else {
	            return ResponseEntity.badRequest().body(null);
	        }
	}
	
	
	
	

//    private final Map<String, List<String>> userDb = new HashMap<>();
//
//    public UserController() {
//        userDb.put("saph", Arrays.asList("user"));
//        userDb.put("saphir", Arrays.asList("admin"));
//        userDb.put("volvi", Arrays.asList("user", "admin"));
//       
//    }

//    @RequestMapping(value = "login", method = RequestMethod.POST)
//    public LoginResponse login(@RequestBody final UserLogin login) throws Exception{
//    	
//    	Member member = new Member();
//		member = memberRepository.findByPseudonym(login.name);
//		System.out.println("hors  member!= null");
//		if (member!= null) {
//			System.out.println("dans  member!= null");
//			String passw = decryptographe(member.getPasswordSec());
//			
//			System.out.println(passw);
//			System.out.println(login.pass);
//			System.out.println(login.name);
//			System.out.println(login.meetingName);
//			
//			if (passw.equals(login.pass)) {
//				
//			
//				try{
//					
//					String status = "connected";
//					Status statusDB = statusRepository.findByStatusName(status);
//					System.out.println(statusDB);
//					member.setStatus(statusDB);
//					member.setMeetingNameConnexion(login.meetingName);
//					UserDetails users = use.loadUserByUsername(login.name);
//				      
//					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(users, null,
//							users.getAuthorities());
//					SecurityContextHolder.getContext().setAuthentication(authToken);
//					memberRepository.save(member);
//					
//					System.out.println(new LoginResponse(Jwts.builder().setSubject(login.name)
//				            .claim("roles", member.getRoles()).setIssuedAt(new Date())
//				            .signWith(SignatureAlgorithm.HS256, "secretkey").compact()).token);
//					 return new LoginResponse(Jwts.builder().setSubject(login.name)
//					            .claim("roles", member.getRoles()).setIssuedAt(new Date())
//					            .signWith(SignatureAlgorithm.HS256, "secretkey").compact());
//					
//				}catch(Exception ex){
//					throw new ServletException("Authentification failed");
//				}
//					
//			}else{
//			throw new ServletException("Invalid password");
//			}
//			
//			}else{
//				throw new ServletException("Invalid pseudonym");
//				}
//		//return null;
//		
//       /* if (login.name == null || !userDb.containsKey(login.name)) {
//            throw new ServletException("Invalid login");
//        }
//		
//        return new LoginResponse(Jwts.builder().setSubject(login.name)
//            .claim("roles", userDb.get(login.name)).setIssuedAt(new Date())
//            .signWith(SignatureAlgorithm.HS256, "secretkey").compact());*/
//    }

    @SuppressWarnings("unused")
	public
    static class UserLogin {
        public String name;
        public String pass;
        public String meetingName;
    }

    @SuppressWarnings("unused")
    private static class LoginResponse {
        public String token;

        public LoginResponse(final String token) {
            this.token = token;
        }
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public Map<String, String> logoutMemberGet(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,String> message= new HashMap<>();
		try {
			String userDetails = SecurityContextHolder.getContext().getAuthentication().getName();
			Member member = memberRepository.findByPseudonym(userDetails);
			Status status = statusRepository.findByStatusName("disconnected");
			member.setStatus(status);
			member.setMeetingNameConnexion(null);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {

				memberRepository.save(member);
				new SecurityContextLogoutHandler().logout(request, response, auth);
				message.put("Message", "succes");
				return message;
			}
		} catch (Exception ex) {
			message.put("Message", "failed");
			return message;
		}
		message.put("Message", "failed");
		return message;
	}


@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Map<String, String> logoutMemberPost(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,String> message= new HashMap<>();
		try {
			String userDetails = SecurityContextHolder.getContext().getAuthentication().getName();
			Member member = memberRepository.findByPseudonym(userDetails);
			Status status = statusRepository.findByStatusName("disconnected");
			member.setStatus(status);
			member.setMeetingNameConnexion(null);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {

				memberRepository.save(member);
				new SecurityContextLogoutHandler().logout(request, response, auth);
				message.put("Message", "succes");
				return message;
			}
		} catch (Exception ex) {
			message.put("Message", "failed");
			return message;
		}
		message.put("Message", "failed");
		return message;
	}

@SuppressWarnings("unchecked")
@RequestMapping(value = "/retrieve", method = RequestMethod.GET)
public Object retrieve(String error, String logout, Authentication authenticationg, Principal principal,
		HttpServletRequest request) {
	String userDetails = SecurityContextHolder.getContext().getAuthentication().getName();
	System.out.println("je suis en session Saphir " + userDetails);
	/*
	 * if (userDetails instanceof UserDetails) { return ((UserDetails)
	 * userDetails).getUsername(); }
	 */

	return userDetails;

}


@SuppressWarnings("unchecked")
@RequestMapping(value = "/retrieve", method = RequestMethod.GET)
public Object retrievePost(String error, String logout, Authentication authenticationg, Principal principal,
		HttpServletRequest request) {
	String userDetails = SecurityContextHolder.getContext().getAuthentication().getName();
	System.out.println("je suis en session Saphir " + userDetails);
	/*
	 * if (userDetails instanceof UserDetails) { return ((UserDetails)
	 * userDetails).getUsername(); }
	 */

	return userDetails;

}
}
