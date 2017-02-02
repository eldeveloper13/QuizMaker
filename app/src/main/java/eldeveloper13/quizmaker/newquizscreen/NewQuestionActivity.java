package eldeveloper13.quizmaker.newquizscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eldeveloper13.quizmaker.QuizApplication;
import eldeveloper13.quizmaker.R;
import eldeveloper13.quizmaker.view.RemovableEditText;

public class NewQuestionActivity extends AppCompatActivity implements NewQuestionContract.View {

    @BindView(R.id.question_edittext)
    EditText mQuestionEditText;

    @BindView(R.id.answers_list_layout)
    LinearLayout mAnswerListLayout;

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

    @Override
    public void setView(String question, List<String> answers) {
        mQuestionEditText.setText(question);
        mAnswerListLayout.removeAllViews();
        RemovableEditText removableEditText = addRemovableEditText();
        removableEditText.setRemoveButtonVisible(false);
        if (answers.size() > 0) {
            removableEditText.setText(answers.get(0));
        }

        for (int i = 1; i < answers.size(); i++) {
            removableEditText = addRemovableEditText();
            removableEditText.setText(answers.get(i));
        }
    }

    @OnClick(R.id.add_answer_btn)
    public void addAnswerButtonClicked(){
        RemovableEditText removableEditText = addRemovableEditText();
        removableEditText.requestFocus();
    }

    private RemovableEditText addRemovableEditText() {
        final RemovableEditText removableEditText = new RemovableEditText(this);
        removableEditText.setRemoveButtonVisible(true);
        removableEditText.setHint("Enter answer");
        removableEditText.setOnRemoveButtonClickedListener(new RemovableEditText.OnRemoveButtonClickedListener() {
            @Override
            public void onClicked() {
                mAnswerListLayout.removeView(removableEditText);
            }
        });
        mAnswerListLayout.addView(removableEditText);
        return removableEditText;
    }

    private void saveQuestion() {
        String question = mQuestionEditText.getText().toString();
        List<String> answers = getAllAnswers();
        mPresenter.saveQuestion(question, answers);
    }

    private List<RemovableEditText> getAnswerEditTexts() {
        List<RemovableEditText> editTextList = new ArrayList<>();
        for (int i = 0; i < mAnswerListLayout.getChildCount(); i++) {
            View view = mAnswerListLayout.getChildAt(i);
            if (view instanceof RemovableEditText) {
                editTextList.add((RemovableEditText) view);
            }
        }
        return editTextList;
    }

    private List<String> getAllAnswers() {
        List<String> answers = new ArrayList<>();
        for (int i = 0; i < mAnswerListLayout.getChildCount(); i++) {
            View view = mAnswerListLayout.getChildAt(i);
            if (view instanceof RemovableEditText) {
                answers.add(((RemovableEditText) view).getText().toString());
            }
        }
        return answers;
    }

    class Extras {
        public static final String QUIZ_DECK_ID = "quiz_deck_id";
        public static final String QUESTION_ID = "question_id";
    }
}
