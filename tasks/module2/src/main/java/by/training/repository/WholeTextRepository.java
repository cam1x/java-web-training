package by.training.repository;

import by.training.model.TextComposite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WholeTextRepository extends SmartTextRepository {

    private volatile AtomicLong currentTextNum = new AtomicLong(-1);

    private List<TextComposite> wholeTexts = new ArrayList<>();
    private SmartTextRepository paragraphRepository;

    public WholeTextRepository(SmartTextRepository paragraphRepository) {
        super(TextRepositoryTypes.WHOLE_TEXT_REPO);
        this.paragraphRepository = paragraphRepository;
        paragraphRepository.parent = this;
        child = paragraphRepository;
    }

    @Override
    public void add(TextComposite textComposite) {
        currentTextNum.getAndIncrement();
        wholeTexts.add(textComposite);
        textComposite.getPartsList().forEach(x->paragraphRepository.add(x));
    }

    @Override
    public boolean remove(TextComposite textComposite) {
        textComposite.getPartsList().forEach(x->paragraphRepository.remove(x));
        return wholeTexts.remove(textComposite);
    }

    @Override
    public List<TextComposite> getAll() {
        return new ArrayList<>(wholeTexts);
    }

    @Override
    public List<TextComposite> find(Predicate<TextComposite> predicate) {
        List<TextComposite> res =
                wholeTexts.stream().filter(predicate).collect(Collectors.toList());
        return (res.isEmpty())?paragraphRepository.find(predicate):res;
    }

    @Override
    public long getCurrentTextNum() {
        long currNum = currentTextNum.get();
        return (currNum==-1)?0:currNum;
    }

    public void setCurrentTextNum(long num) {
        if (num >= 0 && num < wholeTexts.size()) {
            currentTextNum = new AtomicLong(num);
        }
    }
}
