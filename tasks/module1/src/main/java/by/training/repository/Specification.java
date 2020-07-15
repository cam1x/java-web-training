package by.training.repository;

public interface Specification<T> {

    boolean isSatisfiedBy(T entity);

    default Specification<T> and(Specification<T> other) {
        return entity -> isSatisfiedBy(entity) &&
                other.isSatisfiedBy(entity);
    }

    default Specification<T> or(Specification<T> other) {
        return entity -> isSatisfiedBy(entity) ||
                 other.isSatisfiedBy(entity);
    }
}