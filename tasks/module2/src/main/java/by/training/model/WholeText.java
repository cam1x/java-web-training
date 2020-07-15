package by.training.model;

import java.util.*;
import java.util.stream.Collectors;

public class WholeText implements TextComposite {

    private List<TextComposite> paragraphs = new LinkedList<>();
    private TextPartTypes textPartType = TextPartTypes.WHOLE_TEXT;

    @Override
    public void addText(TextComposite textComposite) {
        paragraphs.add(textComposite);
    }

    @Override
    public List<TextComposite> getPartsList() {
        return new LinkedList<>(paragraphs);
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
                TextComposite sortedText = new WholeText();
                List<TextComposite> sortedParagraphs = paragraphs.stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());
                sortedParagraphs.forEach(sortedText::addText);
                return sortedText;
            }

            case 1: {
                TextComposite sortedText = new WholeText();
                List<TextComposite> textComposite = paragraphs.stream()
                        .map(x->x.sort(type,comparator))
                        .collect(Collectors.toList());
                textComposite.forEach(sortedText::addText);
                return sortedText;
            }

            default: {
                throw new IllegalArgumentException("The argument passed caused an unexpected error");
            }
        }
    }

    @Override
    public String getText() {
        return paragraphs.stream().map(TextLeaf::getText).collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WholeText wholeText = (WholeText) o;
        return Objects.equals(paragraphs, wholeText.paragraphs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paragraphs);
    }
}
