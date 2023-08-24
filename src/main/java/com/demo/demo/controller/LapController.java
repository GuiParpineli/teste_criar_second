package com.demo.demo.controller;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.Lap;
import com.demo.demo.service.LapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volta")
public class LapController {
    private final LapService service;

    @Autowired
    public LapController(LapService service) {
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
    public ResponseEntity<?> save(@RequestBody Lap lap) throws SaveErrorException {
        return service.save(lap);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Lap lap) throws SaveErrorException, ResourceNotFoundException {
        return service.update(lap);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestParam Integer id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
