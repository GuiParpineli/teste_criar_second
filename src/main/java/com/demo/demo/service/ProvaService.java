package com.demo.demo.service;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.Prova;
import com.demo.demo.repository.ProvaRepository;
import com.demo.demo.service.intefaces.ICrud;
import com.demo.demo.service.util.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.demo.demo.service.util.Utils.setDataQtdPilotos;

@Service
@Transactional
public class ProvaService implements ICrud<Prova> {
    private final ProvaRepository repository;

    @Autowired
    public ProvaService(ProvaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Prova> data = repository.findAll();
        setDataQtdPilotos(data);
        Utils.setPodio(data);
        List<Prova> dataCopy = data.stream().map(Prova::new)
                .collect(Collectors.toList());
        dataCopy.forEach(a -> a.setPodio(a.getPodio().subList(0, 3)));
        return ResponseEntity.ok(dataCopy);
    }

    @Override
    public ResponseEntity<?> getbyID(Integer id) throws ResourceNotFoundException {
        if (repository.findById(id).isEmpty()) throw new ResourceNotFoundException("Nenhuma prova com id informado");
        return ResponseEntity.ok(repository.findById(id));
    }

    @Override
    public ResponseEntity<?> save(Prova input) throws SaveErrorException {
        try {
            repository.save(input);
        } catch (Exception e) {
            throw new SaveErrorException("Erro, Prova nao salva" + e.getMessage());
        }
        return ResponseEntity.ok("Salvo com sucesso");
    }

    @Override
    public ResponseEntity<?> update(Prova input) throws SaveErrorException, ResourceNotFoundException {
        if (repository.findById(input.getId()).isEmpty())
            throw new ResourceNotFoundException("Nenhuma prova com id informado");
        try {
            repository.saveAndFlush(input);
        } catch (Exception e) {
            throw new SaveErrorException("Erro, Prova nao atualizada" + e.getMessage());
        }
        return ResponseEntity.ok("Atulizado com sucesso");
    }

    @Override
    public ResponseEntity<?> delete(Integer id) throws ResourceNotFoundException {
        if (repository.findById(id).isEmpty()) throw new ResourceNotFoundException("Nenhuma prova com id informado");
        repository.deleteById(id);
        return ResponseEntity.ok("Deletado com sucesso");
    }
}
