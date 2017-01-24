package eldeveloper13.quizmaker.dagger2;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eldeveloper13.quizmaker.db.DataProvider;
import eldeveloper13.quizmaker.db.DataProviderImpl;
import eldeveloper13.quizmaker.mainscreen.MainActivityContract;
import eldeveloper13.quizmaker.mainscreen.MainPresenter;

@Module
public class AppModule {

    @Provides
    @Singleton
    MainActivityContract.Presenter provideMainActivityPresenter(MainPresenter presenter) {
        return presenter;
    }

    @Provides
    @Singleton
    DataProvider provideDataProvider() {
        return new DataProviderImpl();
    }
}
