package by.training.repository;

import by.training.model.TextComposite;

import java.util.*;
import java.util.function.Predicate;

public class SentenceRepository extends SmartTextRepository {

    /**
     * Key is text num in repo(from one to ...),
     * -1 if text repo is empty or sentence should not be added to any text
     * Value is list of sentences
     */
    private Map<Long, List<TextComposite>> sentences = new HashMap<>();
    private SmartTextRepository wordRepository;

    public SentenceRepository(SmartTextRepository wordRepository) {
        super(TextRepositoryTypes.SENTENCE_REPO);
        this.wordRepository = wordRepository;
        wordRepository.parent = this;
        child = wordRepository;
    }

    @Override
    public void add(TextComposite textComposite) {
       addComposite(textComposite, sentences, wordRepository);
    }

    @Override
    public boolean remove(TextComposite textComposite) {
        return removeComposite(textComposite, sentences, wordRepository);
    }

    @Override
    public List<TextComposite> getAll() {
        long parentId = (parent!=null)? parent.getCurrentTextNum(): -1;
        return (sentences.containsKey(parentId))?
                new ArrayList<>(sentences.get(parentId)): Collections.emptyList();
    }

    @Override
    public List<TextComposite> find(Predicate<TextComposite> predicate) {
        return findComposite(predicate, sentences, wordRepository);
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
