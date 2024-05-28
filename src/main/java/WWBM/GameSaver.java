package WWBM;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class GameSaver {
    public static void saveGame(List<QuizItem> quizItems, int currentQuestionIndex,
                                int moneyWon, boolean fiftyFiftyUsed,
                                boolean phoneAFriendUsed, boolean askTheAudienceUsed) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savedGame.ser"))) {
            oos.writeObject(quizItems);
            oos.writeInt(currentQuestionIndex);
            oos.writeInt(moneyWon);
            oos.writeBoolean(fiftyFiftyUsed);
            oos.writeBoolean(phoneAFriendUsed);
            oos.writeBoolean(askTheAudienceUsed);
            oos.flush();
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


