package eldeveloper13.quizmaker.dagger2;

import javax.inject.Singleton;

import dagger.Component;
import eldeveloper13.quizmaker.mainscreen.MainActivity;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(MainActivity activity);
}
