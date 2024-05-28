package WWBM;

import java.io.Serializable;
import java.util.List;

public class GameData implements Serializable {
    private List<QuizItem> quizItems;
    private int currentQuestionIndex;
    private int moneyWon;
    private boolean fiftyFiftyUsed;
    private boolean phoneAFriendUsed;
    private boolean askTheAudienceUsed;

    public GameData(List<QuizItem> quizItems, int currentQuestionIndex,
                    int moneyWon, boolean fiftyFiftyUsed,
                    boolean phoneAFriendUsed, boolean askTheAudienceUsed) {
        this.quizItems = quizItems;
        this.currentQuestionIndex = currentQuestionIndex;
        this.moneyWon = moneyWon;
        this.fiftyFiftyUsed = fiftyFiftyUsed;
        this.phoneAFriendUsed = phoneAFriendUsed;
        this.askTheAudienceUsed = askTheAudienceUsed;
    }

    public List<QuizItem> getQuizItems() {
        return quizItems;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getMoneyWon() {
        return moneyWon;
    }

    public boolean isFiftyFiftyUsed() {
        return fiftyFiftyUsed;
    }

    public boolean isPhoneAFriendUsed() {
        return phoneAFriendUsed;
    }

    public boolean isAskTheAudienceUsed() {
        return askTheAudienceUsed;
    }
}
