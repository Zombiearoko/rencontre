package com.bocobi2.rencontre.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bocobi2.rencontre.repositories.MemberRepository;

@Component
public class UserDetailsServices  implements UserDetailsService  {
	




	@Autowired
	MemberRepository memberRepository;

	

		@Override
	    public UserDetails loadUserByUsername(String pseudonym)
	            throws UsernameNotFoundException {

	         return memberRepository.findByPseudonym(pseudonym);
	   
	}

}
