package ru.otus.pk.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.Inspection;
import ru.otus.pk.spring.domain.Report;

@Service
public class ReportService {

    public Report generateReport(Inspection inspection) throws InterruptedException {
        System.out.println("******Generating report for inspection: " + inspection.getId());
        Thread.sleep(3000);
        System.out.println("******Generating report for inspection: " + inspection.getId() + " done");
        return new Report("Report for inspection " + inspection.getId());
    }
}
