package eldeveloper13.quizmaker.newquizscreen;

import javax.inject.Inject;

import eldeveloper13.quizmaker.db.DataProvider;

public class NewQuestionPresenterFactory implements NewQuestionContract.PresenterFactory {

    DataProvider mDataProvider;

    @Inject
    public NewQuestionPresenterFactory(DataProvider dataProvider) {
        mDataProvider = dataProvider;
    }

    public NewQuestionPresenter createInstance(Long quizDeckId, Long questionId) {
        return new NewQuestionPresenter(quizDeckId, questionId, mDataProvider);
    }
}
