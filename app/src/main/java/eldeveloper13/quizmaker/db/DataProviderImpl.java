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
}
