package eldeveloper13.quizmaker.dagger2;

import javax.inject.Singleton;

import dagger.Component;
import eldeveloper13.quizmaker.deckscreen.MainActivity;
import eldeveloper13.quizmaker.quizscreen.QuestionsActivity;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(MainActivity activity);
    void inject(QuestionsActivity activity);
}
