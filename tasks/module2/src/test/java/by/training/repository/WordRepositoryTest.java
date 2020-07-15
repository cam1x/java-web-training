package by.training.repository;

import by.training.model.TextComposite;
import by.training.model.Word;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class WordRepositoryTest {

    private SmartTextRepository repository;

    @Before
    public void prepare() {
        repository = new WordRepository();
    }

    @Test
    public void shouldAddAllWords() {
        List<TextComposite> words = new ArrayList<>(Arrays.asList(
                new Word("first"),
                new Word("monkey"),
                new Word("football"),
                new Word("monday"),
                new Word("holiday")
                ));

        words.forEach(x->repository.add(x));
        List<TextComposite> addedWords = repository.getAll();

        Assert.assertEquals(words, addedWords);
    }
}

