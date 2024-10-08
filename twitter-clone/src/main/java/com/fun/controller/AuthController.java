package com.fun.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fun.config.JwtProvider;
import com.fun.exception.UserException;
import com.fun.model.User;
import com.fun.model.Verification;
import com.fun.repository.UserRepository;
import com.fun.response.AuthResponse;
import com.fun.service.CustomUserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
 @Autowired	
  private UserRepository userRepository;
 @Autowired	
  private PasswordEncoder passwordEncoder;
 @Autowired	
  private JwtProvider jwtProvider;
 @Autowired	
  private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;
 
 @PostMapping("/signup")
 public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
	 String  email = user.getEmail();
	 String password = user.getPassword();
	 String fullName = user.getFullName();
	 String birthDate = user.getBirthDate();
	 
	 User isEmailExist = userRepository.findByEmail(email);
	 if(isEmailExist!=null) {
		 throw new UserException("EMail is already  used with another Account");
	 }
	 User createdUser = new User();
	 createdUser.setEmail(email);
	 createdUser.setFullName(fullName);
	 createdUser.setPassword(passwordEncoder.encode(password));
	 createdUser.setBirthDate(birthDate);
	 createdUser.setVerification(new Verification());
	 
	 User savedUser =userRepository.save(createdUser);
	 UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(email, password);
	 //Authentication authentication= new UsernamePasswordAuthenticationToken(email, password);
	 SecurityContextHolder.getContext().setAuthentication(authentication);
	 String token = jwtProvider.generateToken(authentication);
	 
	 AuthResponse response = new AuthResponse(token,true);
	 
	 return new ResponseEntity<AuthResponse>(response, HttpStatus.CREATED);
 }
  
 @PostMapping("/signin")
 public ResponseEntity<AuthResponse> signin(@RequestBody User user){
	 String username =user.getEmail();
	 String password = user.getPassword();
	 
	 UsernamePasswordAuthenticationToken authentication = authenticate(username,password);
	 
     String token = jwtProvider.generateToken(authentication);
	 
	 AuthResponse response = new AuthResponse(token,true);
	 
	 return new ResponseEntity<AuthResponse>(response, HttpStatus.ACCEPTED);
 }

private UsernamePasswordAuthenticationToken authenticate(String username, String password) {
	UserDetails  userDetails =customUserDetailsServiceImpl.loadUserByUsername(username);
	
	if(userDetails==null) {
		throw new BadCredentialsException("Invalid Username");
	}
	if(!passwordEncoder.matches(password, userDetails.getPassword())) {
		throw new BadCredentialsException("Invalid Username or password");
	}
	return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
}
}
