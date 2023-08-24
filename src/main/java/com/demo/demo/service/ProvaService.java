package com.demo.demo.service;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.Prova;
import com.demo.demo.model.Volta;
import com.demo.demo.repository.ProvaRepository;
import com.demo.demo.service.intefaces.ICrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
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
        setPodio(data);
        return ResponseEntity.ok(data);
    }

    private void setDataQtdPilotos(List<Prova> data) {
        data.forEach(a -> a.setQtdPilotos(a.getVoltas().size()));
    }

    private void setPodio(List<Prova> data) {
        data.forEach(a -> {
            HashMap<String, LocalTime> podium = new LinkedHashMap<>();
            a.getVoltas().forEach(v -> podium.put(v.getPiloto().getName(), v.getTempoTotal()));
            List<Map.Entry<String, LocalTime>> list = new ArrayList<>(podium.entrySet());
            list.sort(Map.Entry.comparingByValue());

            Optional<LocalTime> maxTempoOptional = a.getVoltas().stream()
                    .map(Volta::getTempoTotal)
                    .max(Comparator.comparingLong(t -> t.until(LocalTime.MIDNIGHT, ChronoUnit.SECONDS)));

            maxTempoOptional.ifPresent(a::setDuracao);

            Map<String, LocalTime> sortedPodium = new LinkedHashMap<>();
            for (Map.Entry<String, LocalTime> entry : list) {
                sortedPodium.put(entry.getKey(), entry.getValue());
            }
            a.setPodio(getFinalPodio(sortedPodium));
        });
    }

    private List<String> getFinalPodio(Map<String, LocalTime> sortedPodium) {
        List<String> finalPodio = new ArrayList<>(sortedPodium.keySet().stream().toList());
        for (int counter = 0; counter < finalPodio.size(); counter++) {
            finalPodio.set(counter, (counter + 1) + ". " + finalPodio.get(counter));
        }
        return finalPodio;
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
