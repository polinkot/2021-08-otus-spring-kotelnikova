package ru.otus.pk.spring.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.Inspection;
import ru.otus.pk.spring.domain.Report;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.RandomUtils.nextLong;

@RequiredArgsConstructor
@IntegrationComponentScan
@Service
public class IntegrationService {

    private final ReportGateway reportGateway;

    @Async
    @Scheduled(fixedRate = 1000)
    public void start() {
        Collection<Inspection> inspections = generateInspections();
        System.out.println("---------New Inspections: " + inspections.stream()
                .map(Inspection::toString)
                .collect(joining("\n")));

        Collection<Report> reports = reportGateway.process(inspections);
        System.out.println("---------Reports: " + reports.stream()
                .map(Report::toString)
                .collect(joining("\n")));
    }

    private static Collection<Inspection> generateInspections() {
        return IntStream.range(0, nextInt(1, 5))
                .mapToObj(i -> new Inspection(nextLong()))
                .collect(toList());
    }
}
