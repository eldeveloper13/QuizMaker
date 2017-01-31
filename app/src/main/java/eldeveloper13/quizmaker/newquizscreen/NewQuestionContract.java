package eldeveloper13.quizmaker.newquizscreen;

import java.util.List;

/**
 * Created by ericl on 1/31/2017.
 */

public interface NewQuestionContract {

    interface View {
        void setView(String question, List<String> answers);
    }

    interface Presenter {
        void attachView(NewQuestionContract.View view);
        void detachView();
        void saveQuestion(String question, List<String> answer);
    }

    interface PresenterFactory {
        Presenter createInstance(Long quizDeckId, Long questionId);
    }

}
