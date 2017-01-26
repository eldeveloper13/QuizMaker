package eldeveloper13.quizmaker.quizscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import eldeveloper13.quizmaker.R;

public class NewQuestionActivity extends AppCompatActivity {

    @BindViews({R.id.answer_edittext1,
            R.id.answer_edittext2,
            R.id.answer_edittext3,
            R.id.answer_edittext4,
            R.id.answer_edittext5
    })
    List<EditText> mAnswerEditTexts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        ButterKnife.bind(this);

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

    public static Intent getStartActivityIntent(Context context) {
        Intent intent = new Intent(context, NewQuestionActivity.class);
        return intent;
    }
}
