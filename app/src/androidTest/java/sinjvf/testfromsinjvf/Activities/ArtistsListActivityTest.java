package sinjvf.testfromsinjvf.Activities;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sinjvf.testfromsinjvf.AuxiliaryClasses.ConcatStrings;
import sinjvf.testfromsinjvf.R;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.espresso.contrib.RecyclerViewActions;

import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static sinjvf.testfromsinjvf.AuxiliaryClasses.CustomMatcher.withResourceName;

/**
 * Тестирование поведения главной Activity:
 * Способность перейти на вторую Activity
 * Правильность данных во второй Activity при переходе на нее
 * ошибка при попытке перейти на Activity с полным описанием исполнителя с номером превышающем заданный массив данных
 *
 * Тесты ориентированы на данные из json по адресу :
 * https://cache-default06g.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json
 * Актуально на момент 18.04.2016
 */
@RunWith(AndroidJUnit4.class)
public class ArtistsListActivityTest {
    public static String NAME = "Ne-Yo";

    public static String NAME2 = "Jason Derulo";
    public static String STYLES = "rnb, pop, rap";
    public static String ABOUT = "обладатель трёх премии Грэмми, американский певец, автор песен, продюсер, актёр, филантроп. В 2009 году журнал Billboard поставил Ни-Йо на 57 место в рейтинге «Артисты десятилетия».";
    public static String LINK ="http://www.neyothegentleman.com/";
    public static int alb = 152;
    public static int track = 256;

    @Rule
    public ActivityTestRule<ArtistsListActivity> rule = new ActivityTestRule<>(ArtistsListActivity.class);

    /**
     * Tests of first activity
     * */


    //check starting of second activity
    @Test
    public void testClick() {
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    //check that second activity starts with right data
    @Test
    public void checkSecondArtistFullData(){
        //go to second activity with data of second artist
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        //check genres
        onView(withId(R.id.artist_style))
                .check(matches(withText(STYLES)));
        //check biography
        onView(withId(R.id.artist_about))
                .check(matches(withText(ABOUT)));
        //check link
        String  link = rule.getActivity().getString(R.string.link)+LINK;
        onView(withId(R.id.artist_link))
                .check(matches(withText(link)));
        //check progress
        String progress = ConcatStrings.getProgress(alb, track, " • ", rule.getActivity());
        onView(withId(R.id.artist_progress))
                .check(matches(withText(progress)));
        //check name in action bar
        onView(allOf(isDescendantOfA(withResourceName("android:id/action_bar_container")), withText(NAME)));
    }



    /**
     * Tests of second activity
     * */
//if return to 1 activity from 2 activity with second actor's data, then we can see second actor's name
    @Test
    public void testOnBackPressed_1() {

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Espresso.pressBack();
        onView(withText(NAME)).check(matches(isDisplayed()));
    }
    //if return to 1 activity from 2 activity with 316 actor's data, then we can see to 316 actor's name
    @Test
    public void testOnBackPressed_2() {

        //starting of second activity
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(316, click()));
        Espresso.pressBack();
        onView(withText(NAME2)).check(matches(isDisplayed()));
    }

}