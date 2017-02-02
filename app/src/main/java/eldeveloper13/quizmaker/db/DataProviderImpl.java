package eldeveloper13.quizmaker.db;

import com.activeandroid.query.Select;

import java.util.List;

public class DataProviderImpl implements DataProvider {

    @Override
    public Long addQuizDeck(String title) {
        QuizDeck newDeck= new QuizDeck();
        newDeck.mName = title;
        return newDeck.save();
    }

    @Override
    public QuizDeck getQuizDeckById(long id) {
        return new Select().from(QuizDeck.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    @Override
    public List<QuizDeck> getAllQuizDecks() {
        return new Select().from(QuizDeck.class)
                .orderBy("Name ASC")
                .execute();
    }

    @Override
    public List<Question> getAllQuestionsByDeckId(long deckId) {
        return new Select().from(Question.class)
                .where("Deck = ?", deckId)
                .execute();
    }

    @Override
    public List<Question> getQuestionById(long id) {
        return new Select().from(Question.class)
                .where("Id = ?", id)
                .execute();
    }

    @Override
    public long addQuestionToQuiz(String question, List<String> answers, long quizDeckId) {
        QuizDeck deck = getQuizDeckById(quizDeckId);
        Question newQuestion = new Question();
        newQuestion.mQuestion = question;
        newQuestion.mAnswer = answers;
        newQuestion.mDeck = deck;
        return newQuestion.save();
    }
}
