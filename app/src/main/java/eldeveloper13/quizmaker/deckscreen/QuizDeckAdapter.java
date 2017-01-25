package eldeveloper13.quizmaker.deckscreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eldeveloper13.quizmaker.R;
import eldeveloper13.quizmaker.db.QuizDeck;
import rx.Observable;
import rx.subjects.PublishSubject;

public class QuizDeckAdapter extends RecyclerView.Adapter<QuizDeckAdapter.ViewHolder> {

    public List<QuizDeck> mQuizDecks;

    public QuizDeckAdapter(List<QuizDeck> decks) {
        mQuizDecks = decks;
    }

    private final PublishSubject<QuizDeck> onClickSubject = PublishSubject.create();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_deck_view_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String name = String.format("%d:\t%s", position+1, mQuizDecks.get(position).mName);
        holder.mDeckNameTextView.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubject.onNext(mQuizDecks.get(position));
            }
        });
    }

    public Observable<QuizDeck> getPositionClicks() {
        return onClickSubject.asObservable();
    }

    @Override
    public int getItemCount() {
        return mQuizDecks.size();
    }

    public void updateDecks(List<QuizDeck> decks) {
        mQuizDecks = decks;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.deck_name_textview)
        TextView mDeckNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
