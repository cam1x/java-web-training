package by.training.service;

import by.training.entity.RailwayCarriage;
import by.training.repository.CarriageRepository;
import by.training.repository.Specification;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RailwayCarriageService {

    private CarriageRepository repo;

    public RailwayCarriageService(CarriageRepository repo) {
        this.repo=repo;
    }

    public void save(RailwayCarriage carriage) {
       repo.add(carriage);
    }

    public boolean remove(RailwayCarriage railwayCarriage) {
        return repo.remove(railwayCarriage);
    }

    public List<RailwayCarriage> getAll() {
        return repo.getAll();
    }

    public List<RailwayCarriage> sort(Comparator<RailwayCarriage> comparator) {
        return repo.getAll().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<RailwayCarriage> find(Specification<RailwayCarriage> spec) {
        return repo.find(spec);
    }

    public Integer calcPassenger() {
        return repo.getAll().stream()
                .mapToInt(RailwayCarriage::getMaxPassengerCount)
                .sum();
    }

    public Double calcCapacity() {
        return repo.getAll().stream()
                .mapToDouble(RailwayCarriage::getMaxLuggageCapacity)
                .sum();
    }
}
