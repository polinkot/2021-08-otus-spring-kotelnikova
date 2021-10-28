package ru.otus.pk.spring.repository;

import org.hibernate.SessionFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class Statistics {
    private final SessionFactory sessionFactory;

    public Statistics(TestEntityManager em) {
        this.sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
    }

    public void setStatisticsEnabled(boolean value) {
        this.sessionFactory.getStatistics().setStatisticsEnabled(value);
    }

    public long getPrepareStatementCount() {
        return sessionFactory.getStatistics().getPrepareStatementCount();
    }
}
