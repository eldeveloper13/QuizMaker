package eldeveloper13.quizmaker.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Question")
public class Question extends Model {

    @Column(name = "Question")
    public String mQuestion;

    @Column(name = "Answer")
    public List<String> mAnswer;

    @Column(name = "Deck")
    public QuizDeck mDeck;
}
