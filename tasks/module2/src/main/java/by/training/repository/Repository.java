package by.training.repository;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {
    void add(T textLeaf);
    boolean remove(T textLeaf);
    List<T> getAll();
    List<T> find(Predicate<T> predicate);
}