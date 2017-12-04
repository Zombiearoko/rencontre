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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bocobi2.rencontre.repositories.MemberRepository;

@Component
@Service
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
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String pseudonym) throws UsernameNotFoundException {
		System.out.println(pseudonym+" donne pardon");
		//Member user = memberRepository.findByPseudonym(pseudonym);
				System.out.println(memberRepository.findByPseudonym(pseudonym)+"toototot");
		        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		        for (Role role : memberRepository.findByPseudonym(pseudonym).getRoles()){
		            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		        }

		        return new org.springframework.security.core.userdetails.User(memberRepository.findByPseudonym(pseudonym).getPseudonym(), 
		        		memberRepository.findByPseudonym(pseudonym).getPassword(), grantedAuthorities);
	}

}