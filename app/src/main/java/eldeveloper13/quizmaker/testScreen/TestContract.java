package eldeveloper13.quizmaker.testScreen;

import java.util.List;

public interface TestContract {

    interface View {
        void showQuestion(int questionNumber, String question, int answerCount);
        void showAnswerResult(boolean correct);
        void showTestCompleted();
    }

    interface Presenter {
        void attachView(TestContract.View view);
        void detachView();
        void loadQuizDeck(long quizDeckId);
        void checkAnswer(List<String> answers);
        void loadQuestion();
        void loadNextQuestion();
    }
}
