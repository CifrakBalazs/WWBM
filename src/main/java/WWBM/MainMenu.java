package WWBM;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class MainMenu extends JFrame {
    private static String FILE_PATH;
    MillionaireGame newGame;
    public MainMenu() {
        setTitle("Main Menu");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton newGameButton = createButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());
        add(newGameButton);

        JButton loadButton = createButton("Load Game");
        loadButton.addActionListener(e -> loadSavedGame());
        add(loadButton);

        JButton addQuestionButton = createButton("Add New Question");
        addQuestionButton.addActionListener(e -> showAddQuestionDialog());
        add(addQuestionButton);

        JButton quitButton = createButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    void startNewGame() {
        try {
            FILE_PATH = "C:\\Users\\cbala\\Documents\\BME\\PROG 3\\HF_XZ6051\\src\\main\\java\\WWBM\\questions.json";
            List<QuizItem> quizItems = QuizReader.readQuizFromFile(FILE_PATH);
            newGame = new MillionaireGame(quizItems);
            newGame.setVisible(true);
            dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadSavedGame() {
        GameData gameData = GameLoader.loadGame();
        if (gameData != null) {
            List<QuizItem> quizItems = gameData.getQuizItems();
            MillionaireGame game = new MillionaireGame(quizItems);
            game.setCurrentQuestionIndex(gameData.getCurrentQuestionIndex());
            game.setMoneyWon(gameData.getMoneyWon());
            game.setFiftyFiftyUsed(gameData.isFiftyFiftyUsed());
            game.setPhoneAFriendUsed(gameData.isPhoneAFriendUsed());
            game.setAskTheAudienceUsed(gameData.isAskTheAudienceUsed());

            game.setVisible(true);
            dispose();
        }
    }

    void showAddQuestionDialog() {
        String question = JOptionPane.showInputDialog(this, "Enter the new question:");
        if (question != null && !question.trim().isEmpty()) {
            AddQuestionDialog addQuestionDialog = new AddQuestionDialog(this);
            addQuestionDialog.setVisible(true);

            List<String> answers = addQuestionDialog.getAnswers();
            if (answers != null && !answers.isEmpty()) {
                int correctAnswer = showAnswerOptionsDialog(answers);
                if (correctAnswer != -1) {
                    addNewQuestionToDatabase(question, answers, correctAnswer);
                    JOptionPane.showMessageDialog(this, "New question added successfully!");
                }
            }
        }
    }


    int showAnswerOptionsDialog(List<String> answers) {
        Object[] options = answers.toArray();
        return JOptionPane.showOptionDialog(this,
                "Select the correct answer:",
                "Correct Answer",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

    void addNewQuestionToDatabase(String question, List<String> answers, int correctAnswer) {
        try {
            List<QuizItem> existingQuestions = QuizReader.readQuizFromFile(FILE_PATH);
            QuizItem newQuestion = new QuizItem();
            newQuestion.setQuestion(question);
            newQuestion.setAnswers(answers);
            newQuestion.setCorrectAnswer(correctAnswer);

            existingQuestions.add(newQuestion);

            QuizWriter.writeQuizToFile(existingQuestions, FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        });
    }


    public MillionaireGame getGame() {
        return newGame;
    }

    public void setFilePath(String absolutePath) {
        FILE_PATH=absolutePath;
    }

}
