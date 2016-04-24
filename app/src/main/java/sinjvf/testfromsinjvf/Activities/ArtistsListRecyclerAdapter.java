package sinjvf.testfromsinjvf.Activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sinjvf.testfromsinjvf.DataStoreClasses.Artist;
import sinjvf.testfromsinjvf.R;

/**
 * Класс, отвечающий за формирование элементов списка из первого Activity
 */
public class ArtistsListRecyclerAdapter extends RecyclerView.Adapter<ArtistsListRecyclerAdapter.ViewHolder> {
    private List<Artist> artists;
    private ItemClickListener clickListener;
    private int height;
    private int padding;


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView image;
        public TextView nameView;
        public TextView styleView;
        public TextView progressView;
        public LinearLayout itemLayout;

        public ViewHolder(View v) {
            super(v);
            //link screen elements with variables
            image = (ImageView) v.findViewById(R.id.artist_image);
            nameView = (TextView) v.findViewById(R.id.artist_name);
            styleView = (TextView) v.findViewById(R.id.artist_style);
            progressView = (TextView) v.findViewById(R.id.artist_progress);
            itemLayout = (LinearLayout) v.findViewById(R.id.artist_item);

            //set size of item in list
            LinearLayout.LayoutParams params;
            params = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, (height+2*padding));
            itemLayout.setLayoutParams(params);
            //set size of image in item
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams
                    (height,height);
            image.setLayoutParams(imageParams);

            //set click listener on item
            itemLayout.setOnClickListener(this);

        }
        //intercepting touching
        @Override
        public void onClick(View v) {
            clickListener.onLLClick(v, getAdapterPosition());
        }

    }
    public void setData(List<Artist> artists, int height, int padding){
        this.artists = artists;
        this.height = height;
        this.padding = padding;
        notifyDataSetChanged();
    }
    //Constructor for our RecyclerAdapter
    public ArtistsListRecyclerAdapter(List<Artist> artists, int height, int padding) {
        this.artists = artists;
        this.height = height;
        this.padding = padding;
    }


    public void setMyClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    //interface for our clicklistener.
    public interface ItemClickListener {
        public void onLLClick(View view, int position);
    }


    // Generate new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artists_list, parent, false);
        return  new ViewHolder(v);
    }

    // Set item's content
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //use lib "Picasso" for downloading images
        Picasso.with(holder.itemView.getContext())
                .load(artists.get(position).getImgUrlSmall())
                .error(R.drawable.error_small)
                .placeholder(R.drawable.error_small)
                .into(holder.image);
        holder.nameView.setText(artists.get(position).getName());
        holder.styleView.setText(artists.get(position).getStyles());
        //get string with numbers of albums and tracks separating by ", "
        holder.progressView.setText(artists.get(position).getProgress(", ", holder.itemView.getContext()));
    }

    // Return number of items
    @Override
    public int getItemCount() {
        if (artists!=null) {
            return artists.size();
        }else{
            return 0;
        }

    }

}
