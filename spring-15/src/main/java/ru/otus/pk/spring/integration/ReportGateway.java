package ru.otus.pk.spring.integration;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.pk.spring.domain.Inspection;
import ru.otus.pk.spring.domain.Report;

import java.util.Collection;

@MessagingGateway
public interface ReportGateway {

    @Gateway(requestChannel = "inspectionChannel", replyChannel = "reportChannel")
    Collection<Report> process(Collection<Inspection> inspections);
}
