package by.training.builder;

import by.training.entity.RailwayCarriage;

import java.util.Map;

public interface Builder {
    RailwayCarriage build(Map<String,String> fields);
}
