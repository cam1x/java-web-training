package by.training.controller;

import by.training.model.TextComposite;
import by.training.model.TextPartTypes;
import by.training.parser.ParagraphParser;
import by.training.parser.SentenceParser;
import by.training.parser.WordParser;
import by.training.reader.FromFileReader;
import by.training.repository.*;
import by.training.service.TextService;
import by.training.validator.FileValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(JUnit4.class)
public class TextControllerTest {

    private TextController controller;

    private SmartTextRepository wordRepo;
    private SmartTextRepository sentenceRepo;
    private SmartTextRepository paragraphRepo;
    private SmartTextRepository wholeTextRepo;

    private FromFileReader fromFileReader;

    @Before
    public void prepare() {
        wordRepo = new WordRepository();
        sentenceRepo = new SentenceRepository(wordRepo);
        paragraphRepo = new ParagraphRepository(sentenceRepo);
        wholeTextRepo = new WholeTextRepository(paragraphRepo);

        WordParser wordParser = new WordParser();
        SentenceParser sentenceParser = new SentenceParser();
        sentenceParser.linkWith(wordParser);
        ParagraphParser parser = new ParagraphParser();
        parser.linkWith(sentenceParser);

        TextService service = new TextService(wholeTextRepo);
        FileValidator validator = new FileValidator();
        fromFileReader = new FromFileReader();
        controller = new TextController(service, validator, fromFileReader, parser);
    }

    @Test
    public void shouldSaveAll() {
        String path = new File(this.getClass().getClassLoader()
                .getResource("ValidText2.txt").getFile()).getAbsolutePath();

        controller.saveTextFromFile(path);

        String text = fromFileReader.readInString(path);

        Assert.assertEquals(text.split("\\b\\W+?(\\b|$)").length, wordRepo.getAll().size());
        Assert.assertEquals(text.split("[\\.\\?\\!]+").length, sentenceRepo.getAll().size());
        Assert.assertEquals(7, paragraphRepo.getAll().size());
        Assert.assertEquals(1, wholeTextRepo.getAll().size());
    }

    @Test
    public void shouldSortParagraphsFromEmptyRepo() {
        Comparator<TextComposite> comparator = Comparator.comparingInt(p -> p.getPartsList().size());
        TextComposite sortedText = controller.sort(TextPartTypes.PARAGRAPH, comparator);

        Assert.assertEquals(0, sortedText.getPartsList().size());
    }

    @Test
    public void shouldSortByParagraphsFromFilledRepo() {
        String path = new File(this.getClass().getClassLoader()
                .getResource("ValidText2.txt").getFile()).getAbsolutePath();

        controller.saveTextFromFile(path);

        Comparator<TextComposite> comparator = Comparator.comparingInt(p -> p.getPartsList().size());
        TextComposite sortedText = controller.sort(TextPartTypes.PARAGRAPH, comparator);
        List<TextComposite> sortedParagraphs = sortedText.getPartsList();
        List<Integer> numOfSentences = new ArrayList<>();/*Contains num
        of sentences in each paragraph of saved text*/
        sortedParagraphs.forEach(x->numOfSentences.add(x.getPartsList().size()));

        Assert.assertEquals(numOfSentences.stream()
                .sorted()
                .collect(Collectors.toList()), numOfSentences);
    }

    @Test
    public void shouldSortBySentencesFromFilledRepo() {
        String path = new File(this.getClass().getClassLoader()
                .getResource("ValidText1.txt").getFile()).getAbsolutePath();

        controller.saveTextFromFile(path);

        Comparator<TextComposite> comparator = Comparator.comparingInt(p -> p.getPartsList().size());
        TextComposite sortedText = controller.sort(TextPartTypes.SENTENCE, comparator);
        List<TextComposite> paragraphs = sortedText.getPartsList();
        for (TextComposite paragraph: paragraphs) {
            List<TextComposite> sortedSentences = paragraph.getPartsList();
            List<Integer> numOfWords = new ArrayList<>();/*Contains num
        of words in each sentence of saved paragraphs*/
            sortedSentences.forEach(x -> numOfWords.add(x.getPartsList().size()));

            Assert.assertEquals(numOfWords.stream()
                    .sorted()
                    .collect(Collectors.toList()), numOfWords);
        }
    }

    @Test
    public void shouldSortByWordsFromFilledRepo() {
        String path = new File(this.getClass().getClassLoader()
                .getResource("ValidText2.txt").getFile()).getAbsolutePath();

        controller.saveTextFromFile(path);

        Comparator<TextComposite> comparator = Comparator.comparingInt(p -> p.getText().length());
        TextComposite sortedText = controller.sort(TextPartTypes.WORD, comparator);
        List<TextComposite> paragraphs = sortedText.getPartsList();
        List<TextComposite> sentences = paragraphs.stream()
                .flatMap(x->x.getPartsList().stream())
                .collect(Collectors.toList());

        for (TextComposite sentence: sentences) {
            List<TextComposite> sortedWords = sentence.getPartsList();
            List<Integer> lengthOfWords = new ArrayList<>();/*Contains length
        of words in each sentence*/
            sortedWords.forEach(x -> lengthOfWords.add(x.getText().length()));

            Assert.assertEquals(lengthOfWords.stream()
                    .sorted()
                    .collect(Collectors.toList()), lengthOfWords);
        }
    }
}
