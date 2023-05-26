package ru.itis.hibernateexample.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.itis.hibernateexample.config.HibernateConfig;
import ru.itis.hibernateexample.model.Driver;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class DriverRepositoryEntityManagerImpl implements CrudRepository<Driver, Long> {

    SessionFactory factory = HibernateConfig.getSessionFactory();
    EntityManager entityManager = factory.createEntityManager();
    private String HQL_SELECT_ALL = "from Driver";
    private String HQL_DELETE_BY_ID = "delete from Driver where id = :id";

    @Override
    public Optional<Driver> findById(Long id) {
        try (Session session = factory.getCurrentSession()) {
            session.getTransaction().begin();
            Driver driver = session.get(Driver.class, id);
            session.getTransaction().commit();
            return Optional.ofNullable(driver);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Driver> findAll() {
        try (Session session = factory.getCurrentSession()) {
            session.getTransaction().begin();
            Query query = session.createQuery(HQL_SELECT_ALL, Driver.class);
            List<Driver> drivers = query.getResultList();
            session.getTransaction().commit();
            return drivers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Driver save(Driver item) {
        try (Session session = factory.getCurrentSession()) {
            session.getTransaction().begin();
            session.saveOrUpdate(item);
            session.getTransaction().commit();
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = factory.getCurrentSession()) {
            session.getTransaction().begin();
            Query query = session.createQuery(HQL_DELETE_BY_ID);
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
