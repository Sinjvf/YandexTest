package sinjvf.testfromsinjvf.AuxiliaryClasses;

import android.app.AlertDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import sinjvf.testfromsinjvf.DataStoreClasses.Artist;
import sinjvf.testfromsinjvf.DataStoreClasses.Const;
import sinjvf.testfromsinjvf.R;


/**
 * Класс для ассинхнного считывания данных из json.
 * Считывает данные в файл, затем читает из него.
 * В случае отсутствия интернет соединения получаем данные из предыдущего ответа сервера
 */
public class DataLoader extends AsyncTaskLoader<List<Artist> > {
    private Context context;
    private List <Artist> artists;
    private String resultJson;
    public DataLoader(Context context, Bundle args){
        super(context);
        this.context = context;
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();

    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();

     }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();

    }

    //return false if exception throws
    @Override
    public List<Artist> loadInBackground() {
        String exceptionMessage = null;
        String filename = context.getString(R.string.file_output);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            // get data from remote recourse
            //set URL
            URL url = new URL(context.getString(R.string.url_json));
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //read data
            inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            //write data to file

            fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            String line;
            while ((line = reader.readLine()) != null) {
                //buffer.append(line);
                fileOutputStream.write(line.getBytes());
            }
        }
        //trouble with connection
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //other troubles
        catch (IOException e){
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        FileInputStream fileIn = null;
        try{
            //Get the text file
            fileIn = context.openFileInput(filename);

            reader = new BufferedReader(new InputStreamReader(fileIn));
            //Read text from file
            StringBuffer buffer = new StringBuffer();

            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                reader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            resultJson = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileIn!=null) {
                try {
                    fileIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return putDataInList();
    }

    //read short data about all artists from json
    protected List <Artist> putDataInList() {
        artists = new ArrayList<>();
        JSONArray dataJson = null;
        try {
            dataJson = new JSONArray(resultJson);

        for (int i = 0; i < dataJson.length(); i++) {
            JSONObject item = dataJson.getJSONObject(i);
            artists.add(new Artist());
            artists.get(i).setId(item.getInt(Const.ID));
            artists.get(i).setName(item.getString(Const.NAME));
            artists.get(i).setImgUrlBig(item.getJSONObject(Const.COVER).getString(Const.C_BIG));
            artists.get(i).setImgUrlSmall(item.getJSONObject(Const.COVER).getString(Const.C_SMALL));
            artists.get(i).setAlbums(item.getInt(Const.ALBUMS));
            artists.get(i).setTracks(item.getInt(Const.TRACKS));
            JSONArray genres = item.getJSONArray(Const.GENRES);
            String[] styles = new String[genres.length()];
            for (int j = 0; j < genres.length(); j++) {
                styles[j] = genres.getString(j);
            }
            artists.get(i).setStyles(styles);
            artists.get(i).setDescription(item.getString(Const.DESCRIPTION));
            //often throws exception. Some actors haven't link in json file
            try {
                artists.get(i).setLink(item.getString(Const.LINK));
            } catch (Exception e) {
                Log.e(Const.LOG_TAG, artists.get(i).getName() + " haven't link");
            }
        }} catch (JSONException e) {
                e.printStackTrace();
            }

        return artists;
    }

}
