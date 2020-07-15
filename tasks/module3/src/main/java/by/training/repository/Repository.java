package by.training.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    void add(T entity);
    boolean remove(T entity);
    Optional<T> getById(long id);
    List<T> getAll();
}
