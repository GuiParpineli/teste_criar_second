package com.demo.demo.service.intefaces;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import org.springframework.http.ResponseEntity;

public interface ICrud<T> {
    public ResponseEntity<?> getAll();
    public ResponseEntity<?> getbyID(Integer id) throws ResourceNotFoundException;
    public ResponseEntity<?> save(T input) throws SaveErrorException;
    public ResponseEntity<?> update(T input) throws SaveErrorException, ResourceNotFoundException;
    public ResponseEntity<?> delete(Integer id) throws ResourceNotFoundException;
}
