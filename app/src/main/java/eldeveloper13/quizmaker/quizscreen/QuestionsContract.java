package eldeveloper13.quizmaker.quizscreen;

import java.util.List;

import eldeveloper13.quizmaker.db.Question;
import eldeveloper13.quizmaker.db.QuizDeck;

public interface QuestionsContract {

    interface View {
        void setTitle(String title);
        void showQuestions(List<Question> questions);
    }

    interface Presenter {
        void attachView(QuestionsContract.View view);
        void detachView();
        void loadDeck(long deckId);
        void loadQuestions(long deckId);
    }
}
