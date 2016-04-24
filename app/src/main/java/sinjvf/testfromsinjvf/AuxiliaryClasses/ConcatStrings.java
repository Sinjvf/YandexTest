package sinjvf.testfromsinjvf.AuxiliaryClasses;

import android.content.Context;
import android.content.res.Resources;

import sinjvf.testfromsinjvf.R;

/**
 * Класс, в который вынесена работа со строками:
 * объединение массива строк для создания строки "стили"
 * объединение в одну строку информации о количестве альбомов и песен исполнителя
 */
public class ConcatStrings {
    public static String getStyles(String [] s){
        StringBuilder sb = new StringBuilder();
        if (s!=null) {
            for (int i = 0; i < s.length - 1; i++) {
                sb.append(s[i]).append(", ");
            }
            if (s.length > 0)
                sb.append(s[s.length - 1]);
        }
        return sb.toString();
    }


    public static String getProgress(int albums, int tracks, String substring,  Context context){
        //generate progress string
        Resources res = context.getResources();
        //get right case of word "album"
        String albumsString = res.getQuantityString(R.plurals.albums,
                albums, albums);
        //get right case of word "track"
        String trackString = res.getQuantityString(R.plurals.tracks,
                tracks, tracks);
        return  albumsString+substring+trackString;
    }

}
