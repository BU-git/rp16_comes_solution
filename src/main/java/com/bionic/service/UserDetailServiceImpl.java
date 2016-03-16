/**
 * 
 */
package com.bionic.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bionic.model.User;

/**
 * @author vitalii.levash
 * @version 0.1
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		Set<GrantedAuthority> roles = new HashSet();
		/**
		 * Set Default Role to user
		 */
		roles.add(new SimpleGrantedAuthority("USER"));
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPassword(), roles);
		return userDetails;
	}

}
