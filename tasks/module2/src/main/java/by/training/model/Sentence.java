package by.training.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Sentence implements TextComposite {

    private List<TextComposite> words = new LinkedList<>();
    private TextPartTypes textPartType = TextPartTypes.SENTENCE;
    private boolean isNewLine;

    public Sentence(boolean isNewLine) {
        this.isNewLine = isNewLine;
    }

    @Override
    public void addText(TextComposite textComposite) {
        words.add(textComposite);
    }

    @Override
    public List<TextComposite> getPartsList() {
        return new LinkedList<>(words);
    }

    @Override
    public TextPartTypes getTextPartType() {
        return textPartType;
    }

    @Override
    public TextComposite sort(TextPartTypes type, Comparator<TextComposite> comparator) {
        int compare = Integer.compare(type.getLevel(), textPartType.getLevel()+1);

        switch (compare) {
            case -1: {
                throw new IllegalArgumentException("Operation is not possible!" +
                        " The argument passed is not part of the composite.");
            }

            case 0: {
                TextComposite sortedSentences = new Sentence(isNewLine);
                List<TextComposite> sortedWords = words.stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());
                sortedWords.forEach(sortedSentences::addText);
                return sortedSentences;
            }

            case 1: {
                TextComposite sortedSentences = new Sentence(isNewLine);
                List<TextComposite> wordComposite = words.stream()
                        .map(x->x.sort(type,comparator))
                        .collect(Collectors.toList());
                wordComposite.forEach(sortedSentences::addText);
                return sortedSentences;
            }

            default: {
                throw new IllegalArgumentException("The argument passed caused an unexpected error");
            }
        }
    }

    @Override
    public String getText() {
        return ((isNewLine)?"\n":"")+ words.stream().map(TextLeaf::getText).collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return Objects.equals(words, sentence.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(words);
    }
}
