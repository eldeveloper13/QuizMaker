package eldeveloper13.quizmaker.newquizscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eldeveloper13.quizmaker.QuizApplication;
import eldeveloper13.quizmaker.R;

public class NewQuestionActivity extends AppCompatActivity implements NewQuestionContract.View {

    @BindView(R.id.question_edittext)
    EditText mQuestionEditText;

    @BindViews({R.id.answer_edittext1,
            R.id.answer_edittext2,
            R.id.answer_edittext3,
            R.id.answer_edittext4,
            R.id.answer_edittext5
    })
    List<EditText> mAnswerEditTexts;

    @Inject
    NewQuestionContract.PresenterFactory mFactory;

    NewQuestionContract.Presenter mPresenter;

    public static Intent getNewQuestionActivityIntent(Context context, long quizDeckId) {
        Intent intent = new Intent(context, NewQuestionActivity.class);
        intent.putExtra(Extras.QUIZ_DECK_ID, quizDeckId);
        return intent;
    }

    public static Intent getEditQuestionActivityIntent(Context context, long questionId) {
        Intent intent = new Intent(context, NewQuestionActivity.class);
        intent.putExtra(Extras.QUESTION_ID, questionId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        ButterKnife.bind(this);
        ((QuizApplication) getApplication()).getAppComponent().inject(this);
        for (final EditText answerEditText: mAnswerEditTexts) {
            answerEditText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (answerEditText.getCompoundDrawables()[DRAWABLE_RIGHT] != null && motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        if (motionEvent.getRawX() >= (answerEditText.getRight() - answerEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            answerEditText.setText("");
                            answerEditText.setVisibility(View.GONE);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }

        long questionDeckId = getIntent().getLongExtra(Extras.QUESTION_ID, -1L);
        if (questionDeckId != -1L) {
            mPresenter = mFactory.createInstance(null, questionDeckId);
        } else {
            long quizDeckId = getIntent().getLongExtra(Extras.QUIZ_DECK_ID, -1L);
            mPresenter = mFactory.createInstance(quizDeckId, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save_question) {
            saveQuestion();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    protected void onPause() {
        mPresenter.detachView();
        super.onPause();
    }

    @OnClick(R.id.add_answer_btn)
    public void addAnswerButtonClicked(){
        for (EditText answerEditText : mAnswerEditTexts) {
            if (answerEditText.getVisibility() == View.GONE) {
                answerEditText.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void saveQuestion() {
        String question = mQuestionEditText.getText().toString();
        List<String> answers = Collections.singletonList(mAnswerEditTexts.get(0).getText().toString());
        mPresenter.saveQuestion(question, answers);
    }

    @Override
    public void setView(String question, List<String> answers) {
        mQuestionEditText.setText(question);
        mAnswerEditTexts.get(0).setText(answers.get(0));
    }

    class Extras {
        public static final String QUIZ_DECK_ID = "quiz_deck_id";
        public static final String QUESTION_ID = "question_id";
    }
}
