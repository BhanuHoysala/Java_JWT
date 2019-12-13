package com.fss.controller;

import com.fss.config.secure.JwtTokenProvider;
import com.fss.datatransferobject.JwtRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationController
{

    AuthenticationManager authenticationManager;

    JwtTokenProvider jwtTokenProvider;


    /**
     * Authenticates and generates JWT token
     *
     * @param authRequest
     * @return
     */
    @PostMapping("/authenticate")
    public String generateAuthenticationToken(@RequestBody JwtRequest authRequest)
    {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            authRequest.getUsername(),
            authRequest.getPassword()));

        return jwtTokenProvider.createToken(authRequest.getUsername());
    }
}
