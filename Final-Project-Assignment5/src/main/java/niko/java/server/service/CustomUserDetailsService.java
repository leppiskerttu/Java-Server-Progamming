package niko.java.server.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import niko.java.server.model.CustomUserDetails;
import niko.java.server.model.User;
import niko.java.server.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Optional<User> userOptional = userRepo.findByUsername(username);
	    User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	    return new CustomUserDetails(user);
	}

}
