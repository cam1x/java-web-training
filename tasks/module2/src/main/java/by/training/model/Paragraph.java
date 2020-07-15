package by.training.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Paragraph implements TextComposite {

    private List<TextComposite> sentences = new LinkedList<>();
    private TextPartTypes textPartType = TextPartTypes.PARAGRAPH;

    @Override
    public void addText(TextComposite textComposite) {
        sentences.add(textComposite);
    }

    @Override
    public List<TextComposite> getPartsList() {
        return new LinkedList<>(sentences);
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
                TextComposite sortedParagraph = new Paragraph();
                List<TextComposite> sortedSentences = sentences.stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());
                sortedSentences.forEach(sortedParagraph::addText);
                return sortedParagraph;
            }

            case 1: {
                TextComposite sortedParagraph = new Paragraph();
                List<TextComposite> sentenceComposite = sentences.stream()
                        .map(x->x.sort(type,comparator))
                        .collect(Collectors.toList());
                sentenceComposite.forEach(sortedParagraph::addText);
                return sortedParagraph;
            }

            default: {
                throw new IllegalArgumentException("The argument passed caused an unexpected error");
            }
        }
    }

    @Override
    public String getText() {
        return "\t" + sentences.stream().map(TextLeaf::getText).collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paragraph paragraph = (Paragraph) o;
        return Objects.equals(sentences, paragraph.sentences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentences);
    }
}
