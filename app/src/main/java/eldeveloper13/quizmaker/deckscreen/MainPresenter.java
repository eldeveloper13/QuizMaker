package eldeveloper13.quizmaker.deckscreen;

import java.util.List;

import javax.inject.Inject;

import eldeveloper13.quizmaker.db.DataProvider;
import eldeveloper13.quizmaker.db.QuizDeck;

public class MainPresenter implements MainActivityContract.Presenter {

    DataProvider mDataProvider;

    MainActivityContract.View mView = null;

    @Inject
    public MainPresenter(DataProvider dataProvider) {
        mDataProvider = dataProvider;
    }

    @Override
    public void attachView(MainActivityContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void createDeck(String title) {
        mDataProvider.addQuizDeck(title);
        getQuizDecks();
    }

    @Override
    public void getQuizDecks() {
        List<QuizDeck> mDecks = mDataProvider.getAllQuizDecks();
        mView.populateDecks(mDecks);
    }
}
