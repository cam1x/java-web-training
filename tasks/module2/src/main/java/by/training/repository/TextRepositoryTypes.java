package by.training.repository;

public enum TextRepositoryTypes {
    WORD_REPO(4),
    SENTENCE_REPO(3),
    PARAGRAPH_REPO(2),
    WHOLE_TEXT_REPO(1);

    private final int level;

    TextRepositoryTypes(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
