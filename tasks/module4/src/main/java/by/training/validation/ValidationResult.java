package by.training.validation;

import java.util.*;
import java.util.stream.Collectors;

public class ValidationResult {
    private Map<String, Set<String>> validationResult;

    public ValidationResult() {
        validationResult = new HashMap<>();
    }

    public void addError(String errorId, String...errors) {
        if (validationResult.containsKey(errorId)){
            validationResult.get(errorId).addAll(Arrays.stream(errors)
                    .collect(Collectors.toSet()));
        } else{
            validationResult.put(errorId, new HashSet<>(Arrays.asList(errors)));
        }
    }

    public Map<String,Set<String>> getErrors() {
        return validationResult;
    }

    public boolean isValid() {
        return validationResult.isEmpty();
    }

    public ValidationResult and (ValidationResult other) {
        for (String key:other.validationResult.keySet()){
            addError(key, other.validationResult.get(key).toArray(new String[0]));
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationResult that = (ValidationResult) o;
        return validationResult.keySet().equals(that.validationResult.keySet()) &&
                new ArrayList<>(validationResult.values()).equals(new ArrayList<>(that.validationResult.values()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(validationResult);
    }

    @Override
    public String toString() {
        if (validationResult.isEmpty()) return "No validation errors";
        StringBuilder sb = new StringBuilder();
        for (String errorId:validationResult.keySet()){
            sb.append(errorId).append(": ");
            if (validationResult.get(errorId).size()>1){
                sb.append("\n\t\t");
            }
            for (String error:validationResult.get(errorId)){
                sb.append(error).append("\n\t\t");
            }
        }
        return sb.toString().trim();
    }
}
