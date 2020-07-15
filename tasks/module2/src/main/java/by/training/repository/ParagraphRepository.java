package by.training.repository;

import by.training.model.TextComposite;

import java.util.*;
import java.util.function.Predicate;

public class ParagraphRepository extends SmartTextRepository {

    /**
     * Key is text num in repo(from one to ...),
     * -1 if text repo is empty or paragraph should not be added to any text
     * Value is list of paragraphs
     */
    private Map<Long, List<TextComposite>> paragraphs = new HashMap<>();
    private SmartTextRepository sentenceRepository;

    public ParagraphRepository(SmartTextRepository sentenceRepository) {
        super(TextRepositoryTypes.PARAGRAPH_REPO);
        this.sentenceRepository = sentenceRepository;
        sentenceRepository.parent = this;
        child = sentenceRepository;
    }

    @Override
    public void add(TextComposite textComposite) {
        addComposite(textComposite, paragraphs, sentenceRepository);
    }

    @Override
    public boolean remove(TextComposite textComposite) {
        return removeComposite(textComposite, paragraphs, sentenceRepository);
    }

    @Override
    public List<TextComposite> getAll() {
        long parentId = (parent!=null)?parent.getCurrentTextNum():-1;
        return (paragraphs.containsKey(parentId))?
                new ArrayList<>(paragraphs.get(parentId)): Collections.emptyList();
    }

    @Override
    public List<TextComposite> find(Predicate<TextComposite> predicate) {
        return findComposite(predicate, paragraphs, sentenceRepository);
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
