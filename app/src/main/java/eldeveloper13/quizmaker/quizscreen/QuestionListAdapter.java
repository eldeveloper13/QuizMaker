package eldeveloper13.quizmaker.quizscreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eldeveloper13.quizmaker.R;
import eldeveloper13.quizmaker.db.Question;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by ericl on 1/31/2017.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {

    List<Question> mQuestions;

    private final PublishSubject<Question> mOnClickedSubject = PublishSubject.create();

    public QuestionListAdapter(List<Question> questions) {
        mQuestions = questions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_question_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String text = String.format("%d:\t%s", position+1, mQuestions.get(position).mQuestion);
        holder.mTextView.setText(text);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedSubject.onNext(mQuestions.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public Observable<Question> getPositionClicks() {
        return mOnClickedSubject.asObservable();
    }

    public void updateQuestions(List<Question> questions) {
        mQuestions = questions;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text1)
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
