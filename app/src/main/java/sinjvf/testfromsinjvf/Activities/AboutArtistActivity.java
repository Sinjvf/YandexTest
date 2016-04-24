package sinjvf.testfromsinjvf.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sinjvf.testfromsinjvf.DataStoreClasses.Artist;
import sinjvf.testfromsinjvf.DataStoreClasses.Const;
import sinjvf.testfromsinjvf.R;

/**
 *  Класс отвечающий за вторую Activity с полным описанием для выбранного исполнителя.
 *   Данные передаются активити через intent и выводятся на экран
 */
public class AboutArtistActivity extends AppCompatActivity {
    //data
    private Artist artist;

    //screen elements
    private ImageView image;
    private TextView aboutView;
    private TextView styleView;
    private TextView progressView;
    private TextView linkView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_full_description);

        //get data form parent activity
        Intent intent = getIntent();
        artist = intent.getParcelableExtra(Const.ID);
        if (artist!=null) {
            //link screen elements with variables
            image = (ImageView) findViewById(R.id.artist_image);
            styleView = (TextView) findViewById(R.id.artist_style);
            progressView = (TextView) findViewById(R.id.artist_progress);
            aboutView = (TextView) findViewById(R.id.artist_about);
            linkView = (TextView) findViewById(R.id.artist_link);


            Animation anim = null;
            anim = AnimationUtils.loadAnimation(this, R.anim.scale_up);
            //start image animation after activity animation
            anim.setStartOffset(500);
            //show  information
            //print image
            Picasso.with(this)
                    .load(artist.getImgUrlBig())
                    .error(R.drawable.error_big)
                    .placeholder(R.drawable.error_big)
                    .into(image);
            //image animation
            image.startAnimation(anim);
            //print genres
            styleView.setText(artist.getStyles());
            //print string with numbers of albums and tracks separating by ", "
            progressView.setText(artist.getProgress(" • ", this));
            aboutView.setText(artist.getDescription());
            //print link if exist
            if (artist.getLink() != null) {
                linkView.setText(getString(R.string.link) + artist.getLink());
                linkView.setVisibility(View.VISIBLE);
            }
            //changing custom actionbar
            setupActionBar(artist.getName());
        }
    }

    //print our string and arrow for return in actionbar
    private void setupActionBar(String name) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setTitle(name);

           // actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    //home button(in action bar) listener. Need for animation
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    //back button listener
   @Override
    public void onBackPressed() {

        finish();
       //set animation
        overridePendingTransition( R.anim.alpha_up, R.anim.scale_down);

    }


}
