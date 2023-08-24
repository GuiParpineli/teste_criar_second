package com.demo.demo.controller;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.Prova;
import com.demo.demo.service.ProvaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prova")
public class ProvaController {
    private final ProvaService service;

    @Autowired
    public ProvaController(ProvaService service) {
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
    public ResponseEntity<?> save(@RequestBody Prova prova) throws SaveErrorException {
        return service.save(prova);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Prova prova) throws SaveErrorException, ResourceNotFoundException {
        return service.update(prova);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestParam Integer id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
