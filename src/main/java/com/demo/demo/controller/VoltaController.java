package com.demo.demo.controller;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.Volta;
import com.demo.demo.service.VoltaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volta")
public class VoltaController {
    private final VoltaService service;

    @Autowired
    public VoltaController(VoltaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) throws ResourceNotFoundException {
        return service.getbyID(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Volta volta) throws SaveErrorException {
        return service.save(volta);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Volta volta) throws SaveErrorException, ResourceNotFoundException {
        return service.update(volta);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestParam Integer id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
