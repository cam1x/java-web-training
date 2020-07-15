package by.training.repository;

import java.util.List;

public interface Repository<T> {
    void add(T carriage);
    boolean remove(T railwayCarriage);
    List<T> getAll();
    List<T> find(Specification<T> spec);
}
