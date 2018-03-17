package com.bocobi2.rencontre.controller;

import io.jsonwebtoken.Claims;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bocobi2.rencontre.controller.UserController.UserLogin;
import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.Role;
import com.bocobi2.rencontre.repositories.MemberRepository;
import com.bocobi2.rencontre.repositories.RoleRepository;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "role/{role}", method = RequestMethod.GET)
	public Boolean login(@PathVariable final String role,@RequestBody final UserLogin login,
			final HttpServletRequest request) throws ServletException {
		final Claims claims = (Claims) request.getAttribute("claims");
		
		Role roles = new Role();
		Member member =new Member();
		member = memberRepository.findByPseudonym(login.name);
		Set<Role> roleName=member.getRoles();
		
		System.out.println(roleName);
		return roleName.contains(role);
		
		//return ((List<String>) claims.get("roles")).contains(roleName);
		
	}
	
	
	/*@SuppressWarnings("unused")
    private static class UserLogin {
        public String name;
        public String pass;
    }*/
}
