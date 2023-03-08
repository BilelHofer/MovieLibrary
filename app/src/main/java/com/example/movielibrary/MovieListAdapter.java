package com.example.movielibrary;

import android.content.ClipData;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.movielibrary.APIMovie.BasicMovie;
import com.example.movielibrary.APIMovie.MovieAPIView;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ListViewHolder> {
    private ArrayList<BasicMovie> localDataSet;

    int selected_position = -1;
    private PageViewModel pageViewModel;

    private DatabaseHelper dbHelper;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView filmTitle;
        private final LinearLayout container;
        private final RelativeLayout shapeLayout;
        private final ImageView like;
        public ListViewHolder(View view) {
            super(view);

            filmTitle = (TextView) view.findViewById(R.id.item_movie_title);
            container = (LinearLayout) view.findViewById(R.id.item_movie_container);
            shapeLayout = (RelativeLayout) view.findViewById(R.id.item_movie_container_background);
            like = (ImageView) view.findViewById(R.id.item_like);

            // Quand on clique sur le container sa change la couleur de fond
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Test au cas ou le holder est null
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                    updateSelected();

                    // Permet de fermer le clavier
                    pageViewModel.setNeedCloseKeyboard(true);
                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Test au cas ou le holder est null
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                    updateLike(localDataSet.get(getAdapterPosition()).getId());
                }
            });
        }

        private void updateSelected() {
            // Updating old as well as new positions
            notifyItemChanged(selected_position);
            selected_position = getAdapterPosition();
            notifyItemChanged(selected_position);

            // On envoie l'id du film selectionné
            MovieAPIView.getMovie(localDataSet.get(selected_position).getId(), pageViewModel);
        }

        public TextView getFilmTitle() {
            return filmTitle;
        }

        public ImageView getLike() {
            return like;
        }
    }


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public MovieListAdapter(ArrayList<BasicMovie> dataSet, PageViewModel pageViewModel, DatabaseHelper db) {
        localDataSet = dataSet;
        this.pageViewModel = pageViewModel;
        this.dbHelper = db;
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
        //TODO: La selection ne dois pas se faire seulement avec la position mais aussi vérifier si l'id du film sélectionné est le même que celui de la position
        if (selected_position == position && pageViewModel.getScreenSize() == PageViewModel.ScreenSize.LARGE) {
            viewHolder.shapeLayout.setBackground(viewHolder.shapeLayout.getContext().getDrawable(R.drawable.movie_list_item_background_selected));
        } else {
            viewHolder.shapeLayout.setBackground(viewHolder.shapeLayout.getContext().getDrawable(R.drawable.movie_list_item_background));
        }

        // Mise à jour des données affichées
        viewHolder.getFilmTitle().setText(localDataSet.get(position).getTitle());

        if (dbHelper.isMovieLiked(localDataSet.get(position).getId())) {
            viewHolder.getLike().setImageResource(R.drawable.like_full);
        } else {
            viewHolder.getLike().setImageResource(R.drawable.like_null);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void addAll(ArrayList<BasicMovie> movies) {
        localDataSet.addAll(movies);

        notifyDataSetChanged();
    }

    protected void updateLike(int movieId) {
        dbHelper.updateLike(movieId);
        notifyDataSetChanged();
    }

    public void clear() {
        localDataSet.clear();
        notifyDataSetChanged();
    }

    public void updateSelectedAtNull() {
        notifyItemChanged(selected_position);
        selected_position = -1;
        notifyItemChanged(selected_position);

        pageViewModel.setMovie(null);
    }
}
