package ru.otus.pk.spring.repository;

import org.hibernate.SessionFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class Statistics {
    private final SessionFactory sessionFactory;

    public Statistics(TestEntityManager em) {
        this.sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        this.sessionFactory.getStatistics().clear();
        this.sessionFactory.getStatistics().setStatisticsEnabled(true);
    }

    public long getPrepareStatementCount() {
        return sessionFactory.getStatistics().getPrepareStatementCount();
    }
}
