package eldeveloper13.quizmaker.newquizscreen;

import java.util.Collections;
import java.util.List;

import eldeveloper13.quizmaker.db.DataProvider;
import eldeveloper13.quizmaker.db.Question;

public class NewQuestionPresenter implements NewQuestionContract.Presenter {

    Question mQuestion;
    DataProvider mDataProvider;
    NewQuestionContract.View mView;

    public NewQuestionPresenter(Long quizDeckId, Long questionId, DataProvider dataProvider) {
        mDataProvider = dataProvider;
        long id = questionId;
        if (questionId == null) {
            id = mDataProvider.addQuestionToQuiz("", Collections.<String>emptyList(), quizDeckId);
        }
        mQuestion = mDataProvider.getQuestionById(id).get(0);
    }

    @Override
    public void attachView(NewQuestionContract.View view) {
        mView = view;
        if (mQuestion != null) {
            mView.setView(mQuestion.mQuestion, Collections.singletonList(mQuestion.mAnswer));
        }
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void saveQuestion(String question, List<String> answer) {
        mQuestion.mQuestion = question;
        mQuestion.mAnswer = answer.get(0);
        mQuestion.save();
    }
}