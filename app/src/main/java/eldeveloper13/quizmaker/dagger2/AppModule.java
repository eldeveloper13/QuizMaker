package eldeveloper13.quizmaker.dagger2;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eldeveloper13.quizmaker.db.DataProvider;
import eldeveloper13.quizmaker.db.DataProviderImpl;
import eldeveloper13.quizmaker.deckscreen.MainActivityContract;
import eldeveloper13.quizmaker.deckscreen.MainPresenter;
import eldeveloper13.quizmaker.quizscreen.QuestionsContract;
import eldeveloper13.quizmaker.quizscreen.QuestionsPresenter;

@Module
public class AppModule {

    @Provides
    MainActivityContract.Presenter provideMainActivityPresenter(MainPresenter presenter) {
        return presenter;
    }

    @Provides
    QuestionsContract.Presenter provideQuestionsPresenter(QuestionsPresenter presenter) {
        return presenter;
    }

    @Provides
    @Singleton
    DataProvider provideDataProvider() {
        return new DataProviderImpl();
    }
}
