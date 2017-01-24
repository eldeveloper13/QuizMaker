package eldeveloper13.quizmaker.mainscreen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eldeveloper13.quizmaker.QuizApplication;
import eldeveloper13.quizmaker.R;
import eldeveloper13.quizmaker.db.QuizDeck;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    @BindView(R.id.coordinatorLayout)
    View mRootView;

    @BindView(R.id.quiz_deck_list)
    RecyclerView mQuizDeckListView;

    @Inject
    MainActivityContract.Presenter mPresenter;

    //region AppCompatActivity Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((QuizApplication)getApplication()).getAppComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mQuizDeckListView.setLayoutManager(layoutManager);
        mQuizDeckListView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
        mPresenter.getQuizDecks();
    }

    @Override
    protected void onPause() {
        mPresenter.detachView();
        super.onPause();
    }

    //endregion

    @OnClick(R.id.fab)
    protected void onFabClickedListener() {
        showCreateDeckDialog();
    }

    //region MainActivitContract.View methods
    @Override
    public void showCreateDeckDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_single_text_input, null);
        final EditText editText = (EditText) view.findViewById(R.id.editText1);
        editText.setHint("Enter the title for your new deck");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.setTitle("Enter Title")
                .setView(view)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mPresenter.createDeck(editText.getEditableText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                })
                .create();

        dialog.show();
    }

    @Override
    public void navigateToDeck() {
        showError("No Deck to navigate to");
    }

    @Override
    public void showError(String error) {
        Snackbar snackbar = Snackbar.make(mRootView, error, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void populateDecks(List<QuizDeck> decks) {
        QuizDeckAdapter adapter = (QuizDeckAdapter) mQuizDeckListView.getAdapter();
        if (adapter == null) {
            adapter = new QuizDeckAdapter(decks);
            mQuizDeckListView.setAdapter(adapter);
        } else {
            adapter.updateDecks(decks);
        }
    }
    //endregion
}
