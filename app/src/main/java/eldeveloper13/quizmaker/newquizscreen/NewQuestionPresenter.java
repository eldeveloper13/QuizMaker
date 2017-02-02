package eldeveloper13.quizmaker.newquizscreen;

import java.util.Collections;
import java.util.List;

import eldeveloper13.quizmaker.db.DataProvider;
import eldeveloper13.quizmaker.db.Question;
import eldeveloper13.quizmaker.db.QuizDeck;

public class NewQuestionPresenter implements NewQuestionContract.Presenter {

    Question mQuestion;
    DataProvider mDataProvider;
    NewQuestionContract.View mView;

    public NewQuestionPresenter(Long quizDeckId, Long questionId, DataProvider dataProvider) {
        mDataProvider = dataProvider;
        if (questionId != null) {
            mQuestion = mDataProvider.getQuestionById(questionId).get(0);
        } else {
            QuizDeck deck = mDataProvider.getQuizDeckById(quizDeckId);
            mQuestion = new Question();
            mQuestion.mQuestion = "";
            mQuestion.mAnswer = Collections.emptyList();
            mQuestion.mDeck = deck;
        }
    }

    @Override
    public void attachView(NewQuestionContract.View view) {
        mView = view;
        if (mQuestion != null) {
            mView.setView(mQuestion.mQuestion, mQuestion.mAnswer);
        }
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void saveQuestion(String question, List<String> answers) {
        mQuestion.mQuestion = question;
        mQuestion.mAnswer = answers;
        mQuestion.save();
    }
}