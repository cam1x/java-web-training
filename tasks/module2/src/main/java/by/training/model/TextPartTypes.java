package by.training.model;

public enum TextPartTypes {
    WORD(4),
    SENTENCE(3),
    PARAGRAPH(2),
    WHOLE_TEXT(1);

    private final int level;

    TextPartTypes(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}