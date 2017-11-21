package com.bocobi2.rencontre.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bocobi2.rencontre.repositories.MemberRepository;

@Component
public class UserDetailsServices  implements UserDetailsService  {
	

	@Autowired
	MemberRepository memberRepository;


	public UserDetails loadUserByMember(Member member) throws UsernameNotFoundException {
        
		//Member user = memberRepository.findByPseudonym(pseudonym);
		System.out.println(member+"toototot");
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : member.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(member.getPseudonym(), 
        		member.getPassword(), grantedAuthorities);
    }


	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}