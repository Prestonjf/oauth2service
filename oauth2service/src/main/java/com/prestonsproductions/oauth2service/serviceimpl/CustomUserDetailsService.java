package com.prestonsproductions.oauth2service.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prestonsproductions.oauth2service.domain.RoleCd;
import com.prestonsproductions.oauth2service.repository.UserRepository;


@Service("userDetailsService")
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public UserDetails loadUserByUsername(String ssoId) throws UsernameNotFoundException {
		com.prestonsproductions.oauth2service.domain.User user = userRepository.findByEmail(ssoId);
		if (user == null) {
			throw new UsernameNotFoundException(ssoId);
		} else {
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;

			return new User(user.getEmail(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
					accountNonLocked, getAuthorities(user.getRoleCds()));
		}
	}

	public Collection<? extends GrantedAuthority> getAuthorities(Set<RoleCd> roles) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(roles));
		return authList;
	}

	public List<String> getRoles(Set<RoleCd> roles) {

		List<String> assignedRoles = new ArrayList<String>();
		for (RoleCd role : roles) {
			assignedRoles.add(role.getName());
		}
		return assignedRoles;
	}

	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
}
