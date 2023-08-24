package com.demo.demo.controller;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.Piloto;
import com.demo.demo.service.PilotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/piloto")
public class PilotoController {
    private final PilotoService service;

    @Autowired
    public PilotoController(PilotoService service) {
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

    @GetMapping("byname/{name}")
    public ResponseEntity<?> getById(@PathVariable String name) throws ResourceNotFoundException {
        return service.getByPilotoName(name);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Piloto piloto) throws SaveErrorException {
        return service.save(piloto);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Piloto piloto) throws SaveErrorException, ResourceNotFoundException {
        return service.update(piloto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestParam Integer id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
