package WWBM;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class GameLoader {
    static String savedGameFilePath;
    public static GameData loadGame() {
        savedGameFilePath="savedGame.ser";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savedGameFilePath))) {
            List<QuizItem> quizItems = (List<QuizItem>) ois.readObject();
            int currentQuestionIndex = ois.readInt();
            int moneyWon = ois.readInt();
            boolean fiftyFiftyUsed = ois.readBoolean();
            boolean phoneAFriendUsed = ois.readBoolean();
            boolean askTheAudienceUsed = ois.readBoolean();

            System.out.println("Game loaded successfully!");
            return new GameData(quizItems, currentQuestionIndex, moneyWon,
                    fiftyFiftyUsed, phoneAFriendUsed, askTheAudienceUsed);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load game.");
            return null;
        }
    }

    public static void setSavedGameFilePath(String absolutePath) {
        savedGameFilePath = absolutePath;
    }
}
