package by.training.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Word implements TextComposite {

    private String word;
    private String ending;
    private String beginning;
    private TextPartTypes textPartType = TextPartTypes.WORD;

    public Word(String word) {
        this(word, ""," ");
    }

    public Word(String word, String beginning, String ending) {
        this.word = word;
        this.beginning = beginning;
        this.ending = ending;
    }

    @Override
    public String getText() {
        return beginning+word+ending;
    }

    @Override
    public void addText(TextComposite textLeaf) {

    }

    @Override
    public List<TextComposite> getPartsList() {
        return Collections.emptyList();
    }

    @Override
    public TextPartTypes getTextPartType() {
        return textPartType;
    }

    @Override
    public TextComposite sort(TextPartTypes type, Comparator<TextComposite> comparator) {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }
}
