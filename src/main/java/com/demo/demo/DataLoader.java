package com.demo.demo;

import com.demo.demo.model.Piloto;
import com.demo.demo.model.Prova;
import com.demo.demo.model.Volta;
import com.demo.demo.repository.PilotoRepository;
import com.demo.demo.repository.ProvaRepository;
import com.demo.demo.repository.VoltaRepository;
import com.demo.demo.service.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.demo.demo.service.util.Utils.setDataQtdPilotos;

@Component
public class DataLoader implements ApplicationRunner {
    final PilotoRepository pilotoRepository;
    final ProvaRepository provaRepository;
    final VoltaRepository voltaRepository;
    private Utils utils;


    @Autowired
    public DataLoader(PilotoRepository pilotoRepository, ProvaRepository provaRepository, VoltaRepository voltaRepository) {
        this.pilotoRepository = pilotoRepository;
        this.provaRepository = provaRepository;
        this.voltaRepository = voltaRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        provaRepository.save(new Prova(LocalDateTime.now(), "SP", null, null, 4

        ));

        pilotoRepository.save(new Piloto("F.MASSA"));
        pilotoRepository.save(new Piloto("R.BARRICHELLO"));
        pilotoRepository.save(new Piloto("K.RAIKKONEN"));
        pilotoRepository.save(new Piloto("M.WEBBER"));
        pilotoRepository.save(new Piloto("F.ALONSO"));
        pilotoRepository.save(new Piloto("S.VETTEL"));

        voltaRepository.save(new Volta(0, List.of(LocalTime.parse("00:01:02.852"), LocalTime.parse("00:01:03.170"), LocalTime.parse("00:01:02.769"), LocalTime.parse("00:01:02.787")), pilotoRepository.findByName("F.MASSA"), provaRepository.findAll().get(0)));
        voltaRepository.save(new Volta(0, List.of(LocalTime.parse("00:01:04.352"), LocalTime.parse("00:01:04.002"), LocalTime.parse("00:01:03.716"), LocalTime.parse("00:01:04.010")), pilotoRepository.findByName("R.BARRICHELLO"), provaRepository.findAll().get(0)));
        voltaRepository.save(new Volta(0, List.of(LocalTime.parse("00:01:04.108"), LocalTime.parse("00:01:03.982"), LocalTime.parse("00:01:03.987"), LocalTime.parse("00:01:03.076")), pilotoRepository.findByName("K.RAIKKONEN"), provaRepository.findAll().get(0)));
        voltaRepository.save(new Volta(0, List.of(LocalTime.parse("00:01:04.414"), LocalTime.parse("00:01:04.805"), LocalTime.parse("00:01:04.287"), LocalTime.parse("00:01:04.216")), pilotoRepository.findByName("M.WEBBER"), provaRepository.findAll().get(0)));
        voltaRepository.save(new Volta(0, List.of(LocalTime.parse("00:01:18.456"), LocalTime.parse("00:01:07.011"), LocalTime.parse("00:01:08.704"), LocalTime.parse("00:01:20.050")), pilotoRepository.findByName("F.ALONSO"), provaRepository.findAll().get(0)));
        voltaRepository.save(new Volta(0, List.of(LocalTime.parse("00:03:31.315"), LocalTime.parse("00:01:37.864"), LocalTime.parse("00:01:18.097")), pilotoRepository.findByName("S.VETTEL"), provaRepository.findAll().get(0)));

        List<Prova> data = provaRepository.findAll();
        setDataQtdPilotos(data);
        Utils.setPodio(data);

    }
}
