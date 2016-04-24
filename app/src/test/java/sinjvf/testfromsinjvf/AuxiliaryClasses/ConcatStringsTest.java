package sinjvf.testfromsinjvf.AuxiliaryClasses;

import android.content.Context;
import android.content.res.Resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import sinjvf.testfromsinjvf.R;

import static org.junit.Assert.*;

/**
 * Unit-тесты вспомогательного класса, объединяющего заданные строки в строки нужного формата
 */


//test of concat Array of string to string
@RunWith(MockitoJUnitRunner.class)
public class ConcatStringsTest {

    @Test
    public void testGetStyles() throws Exception {
        String res;
        res = ConcatStrings.getStyles(new String[] {"a", "b"});
        assertEquals("a, b", res);

        res = ConcatStrings.getStyles(new String[] {"a"});
        assertEquals("a", res);

        res = ConcatStrings.getStyles(new String[] {"a", "b", "c"});
        assertEquals("a, b, c", res);

        res = ConcatStrings.getStyles(new String[] {});
        assertEquals("", res);

    }

    @Mock
    Context context;
    @Mock
    Resources resources;
//test of concat data to progress string
    @Test
    public void testGetProgress() throws Exception {
        String res;
        Mockito.when(context.getResources()).thenReturn(resources);
        Mockito.when(resources.getQuantityString(R.plurals.albums, 0, 0)).thenReturn("no albums");
        Mockito.when(resources.getQuantityString(R.plurals.tracks, 0, 0)).thenReturn("no tracks");
        Mockito.when(resources.getQuantityString(R.plurals.albums, 5, 5)).thenReturn("5 albums");
        Mockito.when(resources.getQuantityString(R.plurals.tracks, 6, 6)).thenReturn("6 tracks");


        res = ConcatStrings.getProgress(0, 0, ", ", context);
        assertEquals("no albums, no tracks", res);

        res = ConcatStrings.getProgress(5, 6, " • ", context);
        assertEquals("5 albums • 6 tracks", res);

        res = ConcatStrings.getProgress(5, 0, "", context);
        assertEquals("5 albumsno tracks", res);

        res = ConcatStrings.getProgress(0, 6, null, context);
        assertEquals("no albumsnull6 tracks", res);

    }
}