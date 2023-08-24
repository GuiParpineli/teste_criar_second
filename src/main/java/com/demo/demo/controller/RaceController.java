package com.demo.demo.controller;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.Race;
import com.demo.demo.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prova")
public class RaceController {
    private final RaceService service;

    @Autowired
    public RaceController(RaceService service) {
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
    @GetMapping("/pilotobyprova")
    public ResponseEntity<?> getProvaByPiloto() {
        return service.getProvaByPilotoId();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Race race) throws SaveErrorException {
        return service.save(race);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Race race) throws SaveErrorException, ResourceNotFoundException {
        return service.update(race);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestParam Integer id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
