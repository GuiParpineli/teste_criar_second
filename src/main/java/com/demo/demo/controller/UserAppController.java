package com.demo.demo.controller;


import com.demo.demo.model.UserApp;
import com.demo.demo.model.dto.UserAppDTO;
import com.demo.demo.security.JwtUtil;
import com.demo.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class UserAppController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final Jackson2ObjectMapperBuilder mapperBuilder;

    @Autowired
    public UserAppController(UserService service, AuthenticationManager authenticationManager,
                             JwtUtil jwtUtil, Jackson2ObjectMapperBuilder mapperBuilder) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.mapperBuilder = mapperBuilder;
    }

    @PostMapping("public/user/login")
    public ResponseEntity<?> login(@RequestBody UserApp userApp) {

        ObjectMapper mapper = mapperBuilder.build();
        UserApp saved = service.loadUserByUsername(userApp.getUsername());

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userApp.getUsername(),
                        userApp.getPassword())
                );
        final UserDetails userDetails = service.loadUserByUsername(userApp.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        saved.setJwt(jwt);

        UserAppDTO userAppDTO = mapper.convertValue(saved, UserAppDTO.class);
        return ResponseEntity.ok(userAppDTO);
    }

}
