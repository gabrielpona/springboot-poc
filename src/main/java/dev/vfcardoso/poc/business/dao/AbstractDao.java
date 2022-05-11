package dev.vfcardoso.poc.business.dao;

import org.hibernate.SessionFactory;

public class AbstractDao {

    protected final SessionFactory sessionFactory;

    public AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
