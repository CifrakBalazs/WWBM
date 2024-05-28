package WWBM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddQuestionDialog extends JDialog {
    private List<JTextField> answerFields;

    public AddQuestionDialog(Frame parent) {
        super(parent, "Add New Question", true);
        answerFields = new ArrayList<>();

        JPanel panel = new JPanel(new GridLayout(0, 1));

        JLabel answerLabel = new JLabel("Enter exactly 4 answers:");
        panel.add(answerLabel);

        for (int i = 1; i <= 4; i++) {
            JTextField answerField = new JTextField();
            panel.add(answerField);
            answerFields.add(answerField);
        }

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (areAnswersValid()) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddQuestionDialog.this,
                            "Please enter exactly 4 answers.", "Invalid Answers", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panel.add(doneButton);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private boolean areAnswersValid() {
        for (JTextField answerField : answerFields) {
            if (answerField.getText().trim().isEmpty()) {
                return false;
            }
        }
        return answerFields.size() == 4;
    }

    public List<String> getAnswers() {
        List<String> answers = new ArrayList<>();
        for (JTextField answerField : answerFields) {
            answers.add(answerField.getText());
        }
        return answers;
    }
}
