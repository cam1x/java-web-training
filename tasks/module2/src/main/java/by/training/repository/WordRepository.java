package by.training.repository;

import by.training.model.TextComposite;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WordRepository extends SmartTextRepository {

    /**
     * Key is text num in repo(from one to ...),
     * -1 if text repo is empty or word should not be added to any text
     * Value is list of words
     */
    private Map<Long, List<TextComposite>> words = new HashMap<>();

    public WordRepository() {
        super(TextRepositoryTypes.WORD_REPO);
    }

    @Override
    public void add(TextComposite textComposite) {
        long parentId = (parent!=null)?parent.getCurrentTextNum():-1;

        if (words.containsKey(parentId)) {
            words.get(parentId).add(textComposite);
        } else {
            words.put(parentId, new ArrayList<>(Collections.singletonList(textComposite)));
        }
    }

    @Override
    public boolean remove(TextComposite textComposite) {
        long parentId = (parent!=null)?parent.getCurrentTextNum():-1;

        if (!words.containsKey(parentId)) {
            return false;
        }

        if (words.get(parentId).contains(textComposite)) {
            return words.get(parentId).remove(textComposite);
        }

        return false;
    }

    @Override
    public List<TextComposite> getAll() {
        long parentId = (parent!=null)?parent.getCurrentTextNum():-1;
        return (words.containsKey(parentId))?
                new ArrayList<>(words.get(parentId)): Collections.emptyList();
    }

    @Override
    public List<TextComposite> find(Predicate<TextComposite> predicate) {
        long parentId = (parent!=null)?parent.getCurrentTextNum():-1;

        if (!words.containsKey(parentId)) {
            return Collections.emptyList();
        }

        return words.get(parentId).stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public long getCurrentTextNum() {
        return (parent!=null)?parent.getCurrentTextNum():-1;
    }

    @Override
    public void setCurrentTextNum(long num) {
        if (parent!=null) {
            parent.setCurrentTextNum(num);
        }
    }
}
