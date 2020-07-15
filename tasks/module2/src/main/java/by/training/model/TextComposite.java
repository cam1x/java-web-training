package by.training.model;

import java.util.Comparator;
import java.util.List;

public interface TextComposite extends TextLeaf {
    void addText(TextComposite textLeaf);
    List<TextComposite> getPartsList();
    TextPartTypes getTextPartType();

    /**
     * Sorts composite by one of its part
     * @param type sortable type
     * @param comparator sort term
     * @return sorted composite,
     * if type is not part of composite
     * throws IllegalArgumentException
     */
    TextComposite sort(TextPartTypes type, Comparator<TextComposite> comparator);
}
