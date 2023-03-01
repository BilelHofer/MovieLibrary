package com.example.movielibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.movielibrary.APIMovie.BasicMovie;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ListViewHolder> {
    private ArrayList<BasicMovie> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView filmTitle;
        private final TextView filmAverage;

        public ListViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            filmTitle = (TextView) view.findViewById(R.id.item_movie_title);
            filmAverage = (TextView) view.findViewById(R.id.item_movie_average);
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

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
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
