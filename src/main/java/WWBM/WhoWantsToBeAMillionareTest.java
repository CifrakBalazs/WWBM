package WWBM;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WhoWantsToBeAMillionareTest {

    @Test
    public void testGetAnswers() {
        AddQuestionDialog dialog = new AddQuestionDialog(null);

        dialog.answerFields.get(0).setText("A");
        dialog.answerFields.get(1).setText("B");
        dialog.answerFields.get(2).setText("C");
        dialog.answerFields.get(3).setText("D");

        assertEquals(Arrays.asList("A", "B", "C", "D"), dialog.getAnswers());
    }

    @Test
    public void testSetAndGetQuestion() {
        QuizItem quizItem = new QuizItem();
        quizItem.setQuestion("What is the capital of France?");
        assertEquals("What is the capital of France?", quizItem.getQuestion());
    }

    @Test
    public void testSetAndGetAnswers() {
        QuizItem quizItem = new QuizItem();
        quizItem.setAnswers(Arrays.asList("A", "B", "C", "D"));
        assertEquals(Arrays.asList("A", "B", "C", "D"), quizItem.getAnswers());
    }

    @Test
    public void testSetAndGetCorrectAnswer() {
        QuizItem quizItem = new QuizItem();
        quizItem.setCorrectAnswer(2);
        assertEquals(2, quizItem.getCorrectAnswer());
    }


    @Test
    public void testUsePhoneAFriendLifeline() {
        QuizItem quizItem = new QuizItem();
        quizItem.setAnswers(Arrays.asList("A", "B", "C", "D"));
        quizItem.setCorrectAnswer(2);
        MillionaireGame game = new MillionaireGame(Arrays.asList(quizItem));
        game.usePhoneAFriendLifeline();
    }
    @Test
    public void testSaveGame() {
        List<QuizItem> quizItems = Arrays.asList(
                createMockQuestion("Question 1", "A", "B", "C", "D", "2")
        );

        GameSaver.saveGame(quizItems, 0, 1000, false, false, false);

        // Verify that the saved file exists
        File savedFile = new File("C:\\Users\\cbala\\Documents\\BME\\PROG 3\\HF_XZ6051\\src\\main\\java\\WWBM\\questions.json");
        assertTrue(savedFile.exists());
    }

    private QuizItem createMockQuestion(String question, String... answers) {
        QuizItem quizItem = new QuizItem();
        quizItem.setQuestion(question);
        quizItem.setAnswers(Arrays.asList(answers));
        quizItem.setCorrectAnswer(answers.length - 1);
        return quizItem;
    }
    private MainMenu mainMenu;

    @Before
    public void setUp() {
        mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }


    @org.junit.jupiter.api.Test
    public void testStartNewGame() {
        // Arrange
        MainMenu mainMenu = new MainMenu();
        File questionsFile = new File("C:\\Users\\cbala\\Documents\\BME\\PROG 3\\HF_XZ6051\\src\\main\\java\\WWBM\\questions.json");
        mainMenu.setFilePath(questionsFile.getAbsolutePath());

        // Act
        mainMenu.startNewGame();

        // Assert
        assertNotNull(mainMenu.getGame());
        assertEquals(MillionaireGame.class, mainMenu.getGame().getClass());
    }


    @org.junit.jupiter.api.Test
    public void testShowAnswerOptionsDialog() {
        // Arrange
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(false); // To avoid GUI-related issues during the test

        // Act
        int selectedAnswerIndex = mainMenu.showAnswerOptionsDialog(Arrays.asList("Option A", "Option B", "Option C"));

        // Assert
        assertEquals(0, selectedAnswerIndex); // Assuming the user selects the first option (Option A)
    }

    @Test
    public void testShowAddQuestionDialog() {
        // Arrange
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
        mainMenu.showAddQuestionDialog();
        Window[] windows = Frame.getWindows();
        boolean addQuestionDialogFound = false;
        for (Window window : windows) {
            if (window instanceof JDialog) {
                JDialog dialog = (JDialog) window;
                if (dialog.getTitle().equals("Add Question")) {
                    addQuestionDialogFound = true;
                    break;
                }
            }
        }
        assertTrue(addQuestionDialogFound);
    }



}



