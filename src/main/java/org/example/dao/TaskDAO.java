package org.example.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.example.models.Task;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class TaskDAO extends AbstractDAO<Task> {

    public TaskDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public List<Task> findAll() {
        return list(namedQuery("Task.findAll"));
    }

    public Task create(Task task) {
        return persist(task);
    }

    public void delete(Task task) {
        currentSession().delete(task);
    }
}
