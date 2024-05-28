package WWBM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MillionaireGame extends JFrame {
    private List<QuizItem> quizItems;
    private int currentQuestionIndex;
    private int moneyWon;

    private boolean fiftyFiftyUsed;
    private boolean phoneAFriendUsed;
    private boolean askTheAudienceUsed;
    private JLabel questionLabel;
    JButton[] answerButtons;
    private JButton lifelineButton;
    private JButton phoneAFriendButton;
    private JButton askTheAudienceButton;

    public MillionaireGame(List<QuizItem> quizItems) {
        this.quizItems = quizItems;
        this.currentQuestionIndex = 0;
        this.moneyWon = 0;
        this.fiftyFiftyUsed = false;
        this.phoneAFriendUsed = false;
        this.askTheAudienceUsed = false;
        // Setters for game state properties
        Collections.shuffle(quizItems);
        initializeUI();
        loadQuestion();
    }
    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
        loadQuestion(); // Load the question when the index is set
    }

    public void setMoneyWon(int moneyWon) {
        this.moneyWon = moneyWon;
    }

    public void setFiftyFiftyUsed(boolean fiftyFiftyUsed) {
        this.fiftyFiftyUsed = fiftyFiftyUsed;
    }

    public void setPhoneAFriendUsed(boolean phoneAFriendUsed) {
        this.phoneAFriendUsed = phoneAFriendUsed;
    }

    public void setAskTheAudienceUsed(boolean askTheAudienceUsed) {
        this.askTheAudienceUsed = askTheAudienceUsed;
    }

    private void initializeUI() {
        setTitle("Who Wants to Be a Millionaire?");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Question Panel
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionLabel = new JLabel("Question goes here");
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        questionPanel.add(questionLabel, BorderLayout.NORTH);
        add(questionPanel, BorderLayout.CENTER);

        // Answer Buttons
        JPanel answerPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2x2 grid with some spacing
        answerButtons = new JButton[4];
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new JButton("Option " + (i + 1));
            answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            answerButtons[i].addActionListener(new AnswerButtonListener());
            answerPanel.add(answerButtons[i]);
        }
        add(answerPanel, BorderLayout.SOUTH);

        // Lifeline Buttons
        JPanel lifelinePanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 1x3 grid for lifelines
        lifelineButton = new JButton("50:50");
        lifelineButton.setFont(new Font("Arial", Font.PLAIN, 14));
        lifelineButton.setEnabled(true);
        lifelineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                useFiftyFiftyLifeline();
            }
        });
        lifelinePanel.add(lifelineButton);

        phoneAFriendButton = new JButton("Phone");
        phoneAFriendButton.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneAFriendButton.setEnabled(!phoneAFriendUsed);
        phoneAFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usePhoneAFriendLifeline();
            }
        });
        lifelinePanel.add(phoneAFriendButton);

        askTheAudienceButton = new JButton("Audience");
        askTheAudienceButton.setFont(new Font("Arial", Font.PLAIN, 14));
        askTheAudienceButton.setEnabled(!askTheAudienceUsed);
        askTheAudienceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                useAskTheAudienceLifeline();
            }
        });
        lifelinePanel.add(askTheAudienceButton);

        add(lifelinePanel, BorderLayout.NORTH);

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });
        add(saveButton, BorderLayout.WEST);

        // Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });
        add(quitButton, BorderLayout.EAST);
    }
    private void loadQuestion() {
        if (currentQuestionIndex < quizItems.size()) {
            QuizItem currentQuestion = quizItems.get(currentQuestionIndex);

            int correctAnswer = currentQuestion.getCorrectAnswer();

            for (JButton button : answerButtons) {
                button.setEnabled(true);
            }

            questionLabel.setText("<html>" + currentQuestion.getQuestion() + "</html>");

            List<String> options = currentQuestion.getAnswers();
            for (int i = 0; i < answerButtons.length; i++) {
                answerButtons[i].setText("<html>" + options.get(i) + "</html>");
            }

            if (fiftyFiftyUsed) {
                answerButtons[correctAnswer].setEnabled(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Congratulations! You won $" + moneyWon);
            returnToMainMenu();
        }
    }

    void useFiftyFiftyLifeline() {
        if (!fiftyFiftyUsed) {
            QuizItem currentQuestion = quizItems.get(currentQuestionIndex);

            int correctAnswer = currentQuestion.getCorrectAnswer();
            int incorrect1, incorrect2;

            do {
                incorrect1 = new Random().nextInt(4);
            } while (incorrect1 == correctAnswer);

            do {
                incorrect2 = new Random().nextInt(4);
            } while (incorrect2 == correctAnswer || incorrect2 == incorrect1);

            for (int i = 0; i < answerButtons.length; i++) {
                if (i == incorrect1 || i == incorrect2) {
                    answerButtons[i].setEnabled(false);
                }
            }

            lifelineButton.setEnabled(false);
            fiftyFiftyUsed = true;
        }
    }


    void usePhoneAFriendLifeline() {
        if (!phoneAFriendUsed) {
            QuizItem currentQuestion = quizItems.get(currentQuestionIndex);

            int friendAnswer = simulatePhoneAFriendAnswer();

            JOptionPane.showMessageDialog(this, "Your friend suggests: Option " + (friendAnswer + 1));

            phoneAFriendButton.setEnabled(false);
            phoneAFriendUsed = true;
        }
    }

    private int simulatePhoneAFriendAnswer() {
        Random random = new Random();
        if (random.nextInt(100) < 80) {
            return quizItems.get(currentQuestionIndex).getCorrectAnswer();
        } else {
            int correctAnswer = quizItems.get(currentQuestionIndex).getCorrectAnswer();
            int randomIncorrectAnswer;
            do {
                randomIncorrectAnswer = random.nextInt(4);
            } while (randomIncorrectAnswer == correctAnswer);
            return randomIncorrectAnswer;
        }
    }

    private void useAskTheAudienceLifeline() {
        if (!askTheAudienceUsed) {
            QuizItem currentQuestion = quizItems.get(currentQuestionIndex);

            int[] audienceAnswers = simulateAskTheAudienceAnswers();

            StringBuilder audienceResponse = new StringBuilder("Audience responses:\n");
            for (int i = 0; i < audienceAnswers.length; i++) {
                audienceResponse.append("Option ").append(i + 1).append(": ").append(audienceAnswers[i]).append("%\n");
            }
            JOptionPane.showMessageDialog(this, audienceResponse.toString());

            askTheAudienceButton.setEnabled(false);
            askTheAudienceUsed = true;
        }
    }

    int[] simulateAskTheAudienceAnswers() {
        int[] audienceAnswers = new int[4];
        Random random = new Random();

        int correctAnswer = quizItems.get(currentQuestionIndex).getCorrectAnswer();
        int highestPercentage = random.nextInt(50) + 30; // Random percentage between 30% and 80%
        audienceAnswers[correctAnswer] = highestPercentage;

        int remainingPercentage = 100 - highestPercentage;
        for (int i = 0; i < audienceAnswers.length; i++) {
            if (i != correctAnswer) {
                int randomPercentage = random.nextInt(remainingPercentage);
                audienceAnswers[i] = randomPercentage;
                remainingPercentage -= randomPercentage;
            }
        }

        audienceAnswers[correctAnswer] += remainingPercentage;

        return audienceAnswers;
    }

    private void returnToMainMenu() {
        MainMenu mainMenu = new MainMenu(); // Assuming you have a MainMenu class
        mainMenu.setVisible(true); // ShowMainMenu
        dispose(); // Close the MillionaireGame window
    }




    private class AnswerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            int selectedAnswer = Arrays.asList(answerButtons).indexOf(clickedButton);

            QuizItem currentQuestion = quizItems.get(currentQuestionIndex);
            int correctAnswer = currentQuestion.getCorrectAnswer();

            if (selectedAnswer == correctAnswer) {
                moneyWon += 1000;
                currentQuestionIndex++;
                loadQuestion();
            } else {
                JOptionPane.showMessageDialog(MillionaireGame.this, "Sorry, that's incorrect. You won $" + moneyWon);
                returnToMainMenu();
            }
        }
    }

    void saveGame() {
        GameSaver.saveGame(quizItems, currentQuestionIndex, moneyWon,
                fiftyFiftyUsed, phoneAFriendUsed, askTheAudienceUsed);
    }

    public boolean isFiftyFiftyUsed() {
        return fiftyFiftyUsed;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public boolean isPhoneAFriendUsed() {
        return phoneAFriendUsed;
    }

    public int getMoneyWon() {
        return moneyWon;
    }

    public boolean isAskTheAudienceUsed() {
        return askTheAudienceUsed;
    }

    public JButton getAskTheAudienceButton() {
        return askTheAudienceButton;
    }

    public JButton getLifelineButton() {
        return lifelineButton;
    }
    public JButton[] getAnswerButtons() {
        return answerButtons;
    }

    public List<QuizItem> getQuizItems() {
        return quizItems;
    }

    public JButton getPhoneAFriendButton() {
        return phoneAFriendButton;
    }



    public static void main(String[] args) {
        try {
            List<QuizItem> quizItems = QuizReader.readQuizFromFile("C:\\Users\\cbala\\Documents\\BME\\PROG 3\\HF_XZ6051\\src\\main\\java\\WWBM\\questions.json");
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    MillionaireGame game = new MillionaireGame(quizItems);
                    game.setVisible(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

