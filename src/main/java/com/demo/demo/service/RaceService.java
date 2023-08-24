package com.demo.demo.service;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.Race;
import com.demo.demo.model.dto.RaceDTO;
import com.demo.demo.repository.ProvaRepository;
import com.demo.demo.service.intefaces.ICrud;
import com.demo.demo.service.util.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.demo.demo.service.util.Utils.setDataQtdPilotos;

@Service
@Transactional
public class RaceService implements ICrud<Race> {
    private final ProvaRepository repository;

    @Autowired
    public RaceService(ProvaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Race> data = repository.findAll();
        setDataQtdPilotos(data);
        Utils.setPodio(data);
        List<Race> dataCopy = data.stream().map(Race::new)
                .collect(Collectors.toList());
        dataCopy.forEach(a -> a.setPodio(a.getPodio().subList(0, 3)));
        return ResponseEntity.ok(dataCopy);
    }

    public ResponseEntity<?> getProvaByPilotoId() {
        List<Race> data = repository.findAll();
        setDataQtdPilotos(data);
        Utils.setPodio(data);
        List<Race> dataCopy = data.stream().map(Race::new)
                .collect(Collectors.toList());

        List<Race> inputCopy = new ArrayList<>(data);
        HashMap<String, LocalTime> bestLapPerPiloto = new HashMap<>();

        for (Race lap : inputCopy) {
            lap.getLaps().forEach(a -> {
                a.getTempo().forEach( b -> {
                    LocalTime currentBest = bestLapPerPiloto.getOrDefault(a.getPilot().getName(), LocalTime.MAX);
                    if(b.isBefore(currentBest)) {
                        bestLapPerPiloto.put(a.getPilot().getName(), b);
                    }
                });
            });
        }

        return ResponseEntity.ok(new RaceDTO(
                dataCopy, bestLapPerPiloto
        ));
    }

    @Override
    public ResponseEntity<?> getbyID(Integer id) throws ResourceNotFoundException {
        if (repository.findById(id).isEmpty()) throw new ResourceNotFoundException("Nenhuma prova com id informado");
        return ResponseEntity.ok(repository.findById(id));
    }

    @Override
    public ResponseEntity<?> save(Race input) throws SaveErrorException {
        try {
            repository.save(input);
        } catch (Exception e) {
            throw new SaveErrorException("Erro, Race nao salva" + e.getMessage());
        }
        return ResponseEntity.ok("Salvo com sucesso");
    }

    @Override
    public ResponseEntity<?> update(Race input) throws SaveErrorException, ResourceNotFoundException {
        if (repository.findById(input.getId()).isEmpty())
            throw new ResourceNotFoundException("Nenhuma prova com id informado");
        try {
            repository.saveAndFlush(input);
        } catch (Exception e) {
            throw new SaveErrorException("Erro, Race nao atualizada" + e.getMessage());
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
