package eldeveloper13.quizmaker.quizscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eldeveloper13.quizmaker.QuizApplication;
import eldeveloper13.quizmaker.R;
import eldeveloper13.quizmaker.db.Question;
import eldeveloper13.quizmaker.db.QuizDeck;

public class QuestionsActivity extends AppCompatActivity implements QuestionsContract.View {

    @BindView(R.id.quiz_list_recycler_view)
    RecyclerView mQuizList;

    @Inject
    QuestionsContract.Presenter mPresenter;

    long mDeckId;

    public static Intent getStartActivityIntent(Context context, long deckId) {
        Intent intent = new Intent(context, QuestionsActivity.class);
        intent.putExtra(Extras.DECK_ID, deckId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);
        ((QuizApplication) getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);

        mDeckId = getIntent().getLongExtra(Extras.DECK_ID, -1);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mQuizList.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
        mPresenter.loadDeck(mDeckId);
    }

    @Override
    protected void onPause() {
        mPresenter.detachView();
        super.onPause();
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void showQuestions(List<Question> questions) {

    }

    static class Extras {
        public static final String DECK_ID = "deck_id";
    }
}
