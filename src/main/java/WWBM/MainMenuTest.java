package WWBM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

public class MainMenuTest {

    private MainMenu mainMenu;

    @BeforeEach
    void setUp() {
        mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }

    @Test
    public void testStartNewGameButton() {
        JButton newGameButton = findButtonByText("New Game");
        assertNotNull(newGameButton);

        // Simulate button click
        ActionEvent actionEvent = new ActionEvent(newGameButton, ActionEvent.ACTION_PERFORMED, "New Game");
        newGameButton.getActionListeners()[0].actionPerformed(actionEvent);

        // Ensure the new game instance is created
        assertNotNull(mainMenu.getGame());
    }





    @Test
    public void testQuitButton() {
        JButton quitButton = findButtonByText("Quit");
        assertNotNull(quitButton);

        // Simulate button click
        ActionEvent actionEvent = new ActionEvent(quitButton, ActionEvent.ACTION_PERFORMED, "Quit");
        quitButton.getActionListeners()[0].actionPerformed(actionEvent);

        // Ensure the application exits
        assertFalse(mainMenu.isVisible());
    }

    private JButton findButtonByText(String buttonText) {
        for (Component component : mainMenu.getContentPane().getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(buttonText)) {
                    return button;
                }
            }
        }
        return null;
    }
}

