package eldeveloper13.quizmaker;

import android.app.Application;

import eldeveloper13.quizmaker.dagger2.AppComponent;
import eldeveloper13.quizmaker.dagger2.AppModule;
import eldeveloper13.quizmaker.dagger2.DaggerAppComponent;

public class QuizApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
