/**
 *  UserDetail Service
 */
		package com.bionic.service;

		import java.util.*;

		import com.bionic.model.dict.UserRoleEnum;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.context.annotation.Bean;
		import org.springframework.security.core.GrantedAuthority;
		import org.springframework.security.core.authority.SimpleGrantedAuthority;
		import org.springframework.security.core.userdetails.UserDetails;
		import org.springframework.security.core.userdetails.UserDetailsService;
		import org.springframework.security.core.userdetails.UsernameNotFoundException;
		import org.springframework.stereotype.Service;

		import com.bionic.model.User;

/**
 * @author vitalii.levash
 * @version 0.3
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);

		boolean enabled = user.isEnabled();
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				enabled,
				accountNonExpired,
				credentialsNonExpired,
				accountNonLocked,
				getAuthorities(user.getRole().ordinal())
		);
		return userDetails;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}

	public List<String> getRoles(Integer role) {

		List<String> roles = new ArrayList<String>();
		roles.add(UserRoleEnum.USER.name());
		if (role.intValue() == UserRoleEnum.ADMIN.ordinal()) {
			roles.add(UserRoleEnum.ADMIN.name());
		}
		return roles;
	}

	private  List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}



}
