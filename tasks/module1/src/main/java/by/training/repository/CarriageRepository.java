package by.training.repository;

import by.training.entity.RailwayCarriage;

import java.util.*;
import java.util.stream.Collectors;

public class CarriageRepository implements Repository<RailwayCarriage> {

    private List<RailwayCarriage> carriages = new ArrayList<>();

    @Override
    public void add(RailwayCarriage carriage) {
        carriages.add(carriage);
    }

    @Override
    public boolean remove(RailwayCarriage railwayCarriage) {
        return carriages.remove(railwayCarriage);
    }

    @Override
    public List<RailwayCarriage> getAll() {
        return  new ArrayList<>(carriages);
    }

    @Override
    public List<RailwayCarriage> find(Specification<RailwayCarriage> spec) {
        return carriages.stream()
                .filter(x->spec.isSatisfiedBy(x))
                .collect(Collectors.toList());
    }
}
