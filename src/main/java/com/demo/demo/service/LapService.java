package com.demo.demo.service;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.Lap;
import com.demo.demo.repository.VoltaRepository;
import com.demo.demo.service.intefaces.ICrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LapService implements ICrud<Lap> {
    private final VoltaRepository repository;

    @Autowired
    public LapService(VoltaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Lap> data = repository.findAll();
        return ResponseEntity.ok(repository.findAll());
    }

    @Override
    public ResponseEntity<?> getbyID(Integer id) throws ResourceNotFoundException {
        if (repository.findById(id).isEmpty()) throw new ResourceNotFoundException("Nenhuma volta com id informado");
        return ResponseEntity.ok(repository.findById(id));
    }

    @Override
    public ResponseEntity<?> save(Lap input) throws SaveErrorException {
        try {
            repository.save(input);
        } catch (Exception e) {
            throw new SaveErrorException("Erro, Lap nao salva" + e.getMessage());
        }
        return ResponseEntity.ok("Salvo com sucesso");
    }

    public ResponseEntity<?> saveAll(List<Lap> laps) throws SaveErrorException {
        try {
            repository.saveAll(laps);
        } catch (Exception e) {
            throw new SaveErrorException("Erro, Lap nao salva" + e.getMessage());
        }
        return ResponseEntity.ok("Salvo com sucesso");
    }

    @Override
    public ResponseEntity<?> update(Lap input) throws SaveErrorException, ResourceNotFoundException {
        if (repository.findById(input.getId()).isEmpty())
            throw new ResourceNotFoundException("Nenhuma volta com id informado");
        try {
            repository.saveAndFlush(input);
        } catch (Exception e) {
            throw new SaveErrorException("Erro, Lap nao atualizada" + e.getMessage());
        }
        return ResponseEntity.ok("Atulizado com sucesso");
    }

    @Override
    public ResponseEntity<?> delete(Integer id) throws ResourceNotFoundException {
        if (repository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Nenhuma volta com id informado");
        repository.deleteById(id);
        return ResponseEntity.ok("Deletado com sucesso");
    }
}
