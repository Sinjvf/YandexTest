package sinjvf.testfromsinjvf.Activities;

        import android.app.AlertDialog;
        import android.app.LoaderManager;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.Loader;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.DisplayMetrics;
        import android.util.Log;
        import android.view.Display;
        import android.view.View;
        import android.widget.LinearLayout;

        import java.util.List;

        import sinjvf.testfromsinjvf.AuxiliaryClasses.DataLoader;
        import sinjvf.testfromsinjvf.DataStoreClasses.Artist;

        import sinjvf.testfromsinjvf.DataStoreClasses.Const;
        import sinjvf.testfromsinjvf.AuxiliaryClasses.DividerItemDecoration;
        import sinjvf.testfromsinjvf.R;

/**
 *  Класс отвечающий за первую Activity со списком исполнителей.
 *   Считываются даннные из сети и выводятся на экран
 */

public class ArtistsListActivity extends AppCompatActivity implements ArtistsListRecyclerAdapter.ItemClickListener,
                                                                        LoaderManager.LoaderCallbacks<List<Artist> > {
    private static final String TAG = "mainnn";
    //using RecyclerView for scrolling artists list
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<Artist> artists=null; //put all data to List of Parcelable objects "Artist"

    private static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artists_list);

        //generate scrolled list
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //generate recycler adapter
        mAdapter = new ArtistsListRecyclerAdapter(artists, 0, 0);
        mRecyclerView.setAdapter(mAdapter);
        //set click listener for starting second Activity
        ((ArtistsListRecyclerAdapter) mAdapter).setMyClickListener(this);
        getLoaderManager().initLoader(LOADER_ID, new Bundle(), this);
    }
    @Override
    public Loader <List<Artist> > onCreateLoader(int id, Bundle args) {
        Loader<List<Artist>>  loader = null;
        if (id == LOADER_ID) {
            loader = new DataLoader(this, args);

        }
        return loader;
    }

    @Override
    public void onLoaderReset(Loader <List<Artist>> loader){}

    @Override
    public void onLoadFinished(Loader<List<Artist> > loader, List<Artist> result) {
        try {
            artists = result;
            if (artists!=null) {
                //generate scrolling list
                generateRecyclerAdapter();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //print our string in actionbar
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setTitle(getString(R.string.artists));
        }
    }


    //ClickListener. Activated by pressing on artist's short description
    @Override
    public void onLLClick(View view, int position){
        //Choose next Activity
        Intent intent = new Intent(this, AboutArtistActivity.class);
        //put data for next Activity
        Artist artist = null;
        try{
            artist = artists.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        intent.putExtra(Const.ID, artist);
        startActivity(intent);

        //set animation
        overridePendingTransition(R.anim.scale_up, R.anim.alpha_down);

    }

    private void generateRecyclerAdapter(){
        //get padding and  height of item
        int padding = (int)(getResources().getDimension(R.dimen.activity_vertical_margin));
        //get width of window
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        int itemHeight =metricsB.widthPixels/3;
        //set data to adapter
        ((ArtistsListRecyclerAdapter)mAdapter).setData(artists, itemHeight, padding);
        //changing custom actionbar
        setupActionBar();
    }


    /*//if connection failed - show dialog
    protected void showMyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.require_int))
                .setCancelable(false)
                .setNegativeButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                // finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }*/

}
