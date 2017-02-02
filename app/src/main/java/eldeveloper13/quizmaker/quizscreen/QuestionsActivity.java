package eldeveloper13.quizmaker.quizscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eldeveloper13.quizmaker.QuizApplication;
import eldeveloper13.quizmaker.R;
import eldeveloper13.quizmaker.db.Question;
import eldeveloper13.quizmaker.newquizscreen.NewQuestionActivity;
import eldeveloper13.quizmaker.testScreen.TestActivity;
import rx.Subscriber;

public class QuestionsActivity extends AppCompatActivity implements QuestionsContract.View {

    @BindView(R.id.quiz_list_recycler_view)
    RecyclerView mQuizList;

    @Inject
    QuestionsContract.Presenter mPresenter;

    long mDeckId;
    QuestionListAdapter mQuestionListAdapter;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mQuizList.setLayoutManager(layoutManager);
        mQuestionListAdapter = new QuestionListAdapter(Collections.<Question>emptyList());
        mQuizList.setAdapter(mQuestionListAdapter);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        mQuizList.addItemDecoration(decoration);
        mQuestionListAdapter.getPositionClicks().subscribe(new Subscriber<Question>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Question question) {
                startActivity(NewQuestionActivity.getEditQuestionActivityIntent(QuestionsActivity.this, question.getId()));
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_questions_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_test) {
            startActivity(TestActivity.getStartActivityIntent(this, mDeckId));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.add_question_fab)
    public void addQuestionClicked() {
        startActivity(NewQuestionActivity.getNewQuestionActivityIntent(this, mDeckId));
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void showQuestions(List<Question> questions) {
        mQuestionListAdapter.updateQuestions(questions);
    }

    static class Extras {
        public static final String DECK_ID = "deck_id";
        public static final int NEW_QUESTION_REQUEST_CODE = 1000;
    }
}
