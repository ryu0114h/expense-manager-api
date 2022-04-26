package com.example.expensetrackerapi.controller;

import com.example.expensetrackerapi.entity.AuthModel;
import com.example.expensetrackerapi.entity.JWTResponse;
import com.example.expensetrackerapi.entity.User;
import com.example.expensetrackerapi.entity.UserModel;
import com.example.expensetrackerapi.security.CustomUserDetailService;
import com.example.expensetrackerapi.service.UserService;
import com.example.expensetrackerapi.util.JwtTokenUtil;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")

    public ResponseEntity<User> save(@Valid @RequestBody UserModel user) {
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody AuthModel authModel) throws Exception {
//        (1) basic
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authModel.getEmail(), authModel.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<HttpStatus>(HttpStatus.OK);

//        (2) JWT
        authenticate(authModel.getEmail(), authModel.getPassword());

        final UserDetails userDetails = userDetailService.loadUserByUsername(authModel.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<JWTResponse>(new JWTResponse(token), HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials");
        }
    }
}
