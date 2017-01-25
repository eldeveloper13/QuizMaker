package eldeveloper13.quizmaker.quizscreen;

import java.util.List;

import javax.inject.Inject;

import eldeveloper13.quizmaker.db.DataProvider;
import eldeveloper13.quizmaker.db.Question;
import eldeveloper13.quizmaker.db.QuizDeck;

public class QuestionsPresenter implements QuestionsContract.Presenter {

    DataProvider mDataProvider;
    QuestionsContract.View mView;


    @Inject
    public QuestionsPresenter(DataProvider dataProvider) {
        mDataProvider = dataProvider;
    }

    @Override
    public void attachView(QuestionsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void loadDeck(long deckId) {
        QuizDeck quizDeck = mDataProvider.getQuizDeckById(deckId);
        mView.setTitle(quizDeck.mName);
        mView.showQuestions(quizDeck.mQuestions());
    }

    @Override
    public void loadQuestions(long deckId) {
        List<Question> questions = mDataProvider.getAllQuestionsByDeckId(deckId);
        mView.showQuestions(questions);
    }
}
