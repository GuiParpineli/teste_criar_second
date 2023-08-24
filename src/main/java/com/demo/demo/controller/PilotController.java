package com.demo.demo.controller;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.Pilot;
import com.demo.demo.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/piloto")
public class PilotController {
    private final PilotService service;

    @Autowired
    public PilotController(PilotService service) {
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
    public ResponseEntity<?> save(@RequestBody Pilot pilot) throws SaveErrorException {
        return service.save(pilot);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Pilot pilot) throws SaveErrorException, ResourceNotFoundException {
        return service.update(pilot);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestParam Integer id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
