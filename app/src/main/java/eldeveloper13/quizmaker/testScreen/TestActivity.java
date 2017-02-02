package eldeveloper13.quizmaker.testScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eldeveloper13.quizmaker.QuizApplication;
import eldeveloper13.quizmaker.R;

public class TestActivity extends AppCompatActivity implements TestContract.View {

    @Inject
    TestContract.Presenter mPresenter;
    @BindView(R.id.question_header)
    TextView mQuestionHeader;
    @BindView(R.id.question_textview)
    TextView mQuestionTextView;
    @BindView(R.id.answers_list_layout)
    LinearLayout mAnswerLayout;
    @BindView(R.id.submit_answer_button)
    Button mSubmitButton;

    public static Intent getStartActivityIntent(Context context, long quizDeckId) {
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra(Extras.QUIZ_DECK_ID, quizDeckId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ButterKnife.bind(this);
        ((QuizApplication) getApplication()).getAppComponent().inject(this);
        long quizDeckId = getIntent().getLongExtra(Extras.QUIZ_DECK_ID, -1L);

        mPresenter.loadQuizDeck(quizDeckId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
        mPresenter.loadQuestion();
    }

    @Override
    protected void onPause() {
        mPresenter.detachView();
        super.onPause();
    }

    @Override
    public void showQuestion(int questionNumber, String question, int answerCount) {
        mQuestionHeader.setText(String.format("Q%d:", questionNumber));
        mQuestionTextView.setText(question);
        mAnswerLayout.removeAllViews();
        for (int i = 0; i < answerCount; i++) {
            EditText answerEditText = new EditText(this);
            answerEditText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            answerEditText.setTextSize(20);
            answerEditText.setHint("Enter answer");
            mAnswerLayout.addView(answerEditText);
        }
        mSubmitButton.setText("Submit");
    }

    @Override
    public void showAnswerResult(boolean correct) {
        if (correct) {
            mSubmitButton.setText("CORRECT!");
        } else {
            mSubmitButton.setText("INCORRECT!");
        }
    }

    @Override
    public void showTestCompleted() {
        Toast.makeText(this, "You have completed all the questions.  Good Job!", Toast.LENGTH_LONG);
    }

    @OnClick(R.id.submit_answer_button)
    public void onSubmitAnswerClicked() {
        if (mSubmitButton.getText().toString().equalsIgnoreCase("Submit")) {
            List<String> answers = getAllAnswers();
            mPresenter.checkAnswer(answers);
            disableAllAnswerField();
        } else {
            mPresenter.loadNextQuestion();
        }
    }

    private void disableAllAnswerField() {
        for (int i = 0; i < mAnswerLayout.getChildCount(); i++) {
            mAnswerLayout.getChildAt(i).setEnabled(false);
        }
    }

    private List<String> getAllAnswers() {
        List<String> answers = new ArrayList<>();
        for (int i = 0; i < mAnswerLayout.getChildCount(); i++) {
            if (mAnswerLayout.getChildAt(i) instanceof EditText) {
                answers.add( ((EditText) mAnswerLayout.getChildAt(i)).getText().toString() );
            }
        }
        return answers;
    }

    public static class Extras {
        public static final String QUIZ_DECK_ID = "quiz_deck_id";
    }
}
