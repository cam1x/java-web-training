package by.training.service;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    void save(T entity);
    boolean remove(T entity);
    Optional<T> findById(long id);
    List<T> getAll();
}
