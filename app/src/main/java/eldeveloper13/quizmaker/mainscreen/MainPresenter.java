package eldeveloper13.quizmaker.mainscreen;

import javax.inject.Inject;

public class MainPresenter implements MainActivityContract.Presenter {

    MainActivityContract.View mView = null;

    @Inject
    public MainPresenter() {
    }

    @Override
    public void attachView(MainActivityContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void createDeck(String title) {
        mView.showError("Not implemented yet");
    }
}
