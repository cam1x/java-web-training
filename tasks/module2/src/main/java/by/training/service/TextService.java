package by.training.service;

import by.training.model.TextComposite;
import by.training.model.TextPartTypes;
import by.training.model.WholeText;
import by.training.repository.*;

import java.util.*;
import java.util.function.Predicate;

public class TextService {

    private SmartTextRepository wholeTextRepo;

    public TextService(SmartTextRepository wholeTextRepo) {
        this.wholeTextRepo = wholeTextRepo;
    }

    public void save(TextComposite textComposite) {
        wholeTextRepo.smartAdd(textComposite);
    }

    public void remove(TextComposite textComposite) {
        wholeTextRepo.smartRemove(textComposite);
    }

    public List<TextComposite> getAll() {
        return wholeTextRepo.getAll();
    }

    public List<TextComposite> find(Predicate<TextComposite> predicate) {
        return wholeTextRepo.find(predicate);
    }

    public TextComposite sort(TextPartTypes type, Comparator<TextComposite> comparator) {
        List<TextComposite> savedTexts = wholeTextRepo.getAll();
        if (savedTexts.isEmpty()) {
            return new WholeText();
        }
        return savedTexts.get(Math.toIntExact(wholeTextRepo.getCurrentTextNum()))
                .sort(type, comparator);
    }
}