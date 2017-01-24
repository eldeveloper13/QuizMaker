package eldeveloper13.quizmaker.mainscreen;

import java.util.List;

import eldeveloper13.quizmaker.db.QuizDeck;

public interface MainActivityContract {

    interface View {
        void showCreateDeckDialog();
        void navigateToDeck();
        void showError(String error);
        void populateDecks(List<QuizDeck> decks);
    }

    interface Presenter {
        void attachView(MainActivityContract.View view);
        void detachView();
        void createDeck(String title);
        void getQuizDecks();
    }

}
