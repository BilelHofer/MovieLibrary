package com.example.movielibrary;

import android.content.ClipData;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.movielibrary.APIMovie.BasicMovie;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ListViewHolder> {
    private ArrayList<BasicMovie> localDataSet;

    int selected_position = 0;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView filmTitle;
        private final TextView filmAverage;
        private final LinearLayout container;
        private final RelativeLayout shapeLayout;
        public ListViewHolder(View view) {
            super(view);

            filmTitle = (TextView) view.findViewById(R.id.item_movie_title);
            filmAverage = (TextView) view.findViewById(R.id.item_movie_average);
            container = (LinearLayout) view.findViewById(R.id.item_movie_container);
            shapeLayout = (RelativeLayout) view.findViewById(R.id.item_movie_container_background);

            // Quand on clique sur le container sa change la couleur de fond
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Test au cas ou le holder est null
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                    // Updating old as well as new positions
                    notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    notifyItemChanged(selected_position);
                }
            });
        }

        public TextView getFilmTitle() {
            return filmTitle;
        }

        public TextView getfilmAverage() {
            return filmAverage;
        }
    }


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public MovieListAdapter(ArrayList<BasicMovie> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_list_item, viewGroup, false);

        return new ListViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ListViewHolder viewHolder, final int position) {
        if (selected_position == position) {
            viewHolder.shapeLayout.setBackground(viewHolder.shapeLayout.getContext().getDrawable(R.drawable.movie_list_item_background_selected));
        } else {
            viewHolder.shapeLayout.setBackground(viewHolder.shapeLayout.getContext().getDrawable(R.drawable.movie_list_item_background));
        }

        // Mise à jour des données affichées
        viewHolder.getFilmTitle().setText(localDataSet.get(position).getTitle());
        viewHolder.getfilmAverage().setText(localDataSet.get(position).getAverage());
    }

    public void updateData(ArrayList<BasicMovie> dataSet) {
        localDataSet = dataSet;
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
