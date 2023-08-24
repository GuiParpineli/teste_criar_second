package com.demo.demo.service;


import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.UserApp;
import com.demo.demo.repository.UserAppRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserService implements UserDetailsService {

    private final UserAppRepository userAppRepository;

    @Autowired
    public UserService(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    @SneakyThrows
    @Override
    public UserApp loadUserByUsername(String username) {
        return userAppRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("None founded"));
    }

    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        List<UserApp> saved = userAppRepository.findAll();
        if (saved.isEmpty()) throw new ResourceNotFoundException("None founded");
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> getById(Integer id) throws ResourceNotFoundException {
        UserApp saved = userAppRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None founded"));
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> delete(Integer id) throws SaveErrorException {
        if (userAppRepository.findById(id).isEmpty()) {
            throw new SaveErrorException("None UserApp founded");
        }
        userAppRepository.deleteById(id);
        return ResponseEntity.ok("UserApp Deleted Sucessfully");
    }

}
