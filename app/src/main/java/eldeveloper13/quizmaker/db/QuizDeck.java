package eldeveloper13.quizmaker.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Deck")
public class QuizDeck extends Model{

    @Column(name = "Name")
    public String mName;

    public List<Question> mQuestions() {
        return getMany(Question.class, "Deck");
    }
}
