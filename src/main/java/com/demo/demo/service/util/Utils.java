package com.demo.demo.service.util;

import com.demo.demo.model.Lap;
import com.demo.demo.model.Race;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Utils {
    public static void setPodio(List<Race> data) {
        data.forEach(a -> {
            HashMap<String, LocalTime> podium = new LinkedHashMap<>();
            a.getLaps().forEach(v -> podium.put(v.getPilot().getName(), v.getTempoTotal()));
            List<Map.Entry<String, LocalTime>> list = new ArrayList<>(podium.entrySet());
            list.sort(Map.Entry.comparingByValue());

            Optional<LocalTime> maxTempoOptional = a.getLaps().stream()
                    .map(Lap::getTempoTotal)
                    .max(Comparator.comparingLong(t -> t.until(LocalTime.MIDNIGHT, ChronoUnit.SECONDS)));

            maxTempoOptional.ifPresent(a::setDuracao);

            Map<String, LocalTime> sortedPodium = new LinkedHashMap<>();
            for (Map.Entry<String, LocalTime> entry : list) {
                sortedPodium.put(entry.getKey(), entry.getValue());
            }
            a.setPodio(getFinalPodio(sortedPodium));
        });
    }

    private static List<String> getFinalPodio(Map<String, LocalTime> sortedPodium) {
        List<String> finalPodio = new ArrayList<>(sortedPodium.keySet().stream().toList());
        for (int counter = 0; counter < finalPodio.size(); counter++) {
            finalPodio.set(counter, (counter + 1) + ". " + finalPodio.get(counter));
        }
        return finalPodio;
    }

    public static void setDataQtdPilotos(List<Race> data) {
        data.forEach(a -> a.setQtdPilotos(a.getLaps().size()));
    }

}
