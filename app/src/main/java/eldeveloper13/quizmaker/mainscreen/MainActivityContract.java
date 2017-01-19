package eldeveloper13.quizmaker.mainscreen;

public interface MainActivityContract {

    interface View {
        void showCreateDeckDialog();
        void navigateToDeck();
        void showError(String error);
    }

    interface Presenter {
        void attachView(MainActivityContract.View view);
        void detachView();
        void createDeck(String title);
    }

}
