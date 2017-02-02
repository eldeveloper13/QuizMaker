package eldeveloper13.quizmaker.testScreen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import eldeveloper13.quizmaker.db.DataProvider;
import eldeveloper13.quizmaker.db.Question;
import eldeveloper13.quizmaker.db.QuizDeck;

public class TestPresenter implements TestContract.Presenter {

    DataProvider mDataProvider;
    TestContract.View mView;
    QuizDeck mQuizDeck;
    int mCurrentQuestionIndex;

    @Inject
    public TestPresenter(DataProvider dataProvider) {
        mDataProvider = dataProvider;
        mCurrentQuestionIndex = 0;
    }

    @Override
    public void attachView(TestContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void loadQuizDeck(long quizDeckId) {
        mQuizDeck = mDataProvider.getQuizDeckById(quizDeckId);
    }

    @Override
    public void checkAnswer(List<String> answers) {
        Set<String> answersSet = new HashSet<>(answers);
        Set<String> correctAnswerSet = new HashSet<>(mQuizDeck.mQuestions().get(mCurrentQuestionIndex).mAnswer);
        mView.showAnswerResult(answersSet.equals(correctAnswerSet));
    }

    @Override
    public void loadQuestion() {
        Question question = mQuizDeck.mQuestions().get(mCurrentQuestionIndex);
        mView.showQuestion(mCurrentQuestionIndex + 1, question.mQuestion, question.mAnswer.size());
    }

    @Override
    public void loadNextQuestion() {
        mCurrentQuestionIndex++;
        if (mCurrentQuestionIndex > mQuizDeck.mQuestions().size()) {
            mCurrentQuestionIndex = -1;
            mView.showTestCompleted();
        } else {
            loadQuestion();
        }
    }
}
