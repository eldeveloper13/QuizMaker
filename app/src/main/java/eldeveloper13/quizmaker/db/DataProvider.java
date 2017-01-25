package eldeveloper13.quizmaker.db;

import java.util.List;

public interface DataProvider {

    Long addQuizDeck(String title);
    QuizDeck getQuizDeckById(long id);
    List<QuizDeck> getAllQuizDecks();

    List<Question> getAllQuestionsByDeckId(long deckId);

    List<Question> getQuestionById(long id);
}
