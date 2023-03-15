package com.example.movielibrary;


import android.content.ClipData;
import android.graphics.Color;
import android.media.Image;
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

import com.example.movielibrary.APIMovie.Actor;
import com.example.movielibrary.APIMovie.BasicMovie;
import com.example.movielibrary.APIMovie.MovieAPIView;
import com.squareup.picasso.Picasso;

public class ActorListAdapter extends RecyclerView.Adapter<ActorListAdapter.ListViewHolder> {
    private ArrayList<Actor> localDataSet;
    private PageViewModel pageViewModel;


    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView actorName;
        private final TextView actorName2;
        private final ImageView actorImage;
        public ListViewHolder(View view) {
            super(view);

            actorName = (TextView) view.findViewById(R.id.item_actor_name);
            actorName2 = (TextView) view.findViewById(R.id.item_actor_name2);
            actorImage = (ImageView) view.findViewById(R.id.item_actor_poster);

            actorImage.setOnClickListener(v -> {
                MovieAPIView.getActor(localDataSet.get(getAdapterPosition()).getId(), pageViewModel);
            });
        }

        public TextView getActorName() {
            return actorName;
        }

        public ImageView getActorImage() {
            return actorImage;
        }

        public TextView getActorName2() {
            return actorName2;
        }
    }


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public ActorListAdapter(ArrayList<Actor> dataSet, PageViewModel pageViewModel) {
        this.pageViewModel = pageViewModel;
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.actor_list_item, viewGroup, false);

        return new ListViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ListViewHolder viewHolder, final int position) {

        // Mise à jour des données affichées
        String name = localDataSet.get(position).getName();

        // Si le nom est trop long, sépars le nom et le prenom
        if (name.length() > 15) {
            String[] splitName = name.split(" ");
            String firstName = splitName[0];
            String lastName = splitName[1];

            // Si le prenom est trop long, on le coupe
            if (firstName.length() > 12) {
                firstName = firstName.substring(0, 12) + "..";
            }

            // Si le nom est trop long, on le coupe
            if (lastName.length() > 18) {
                lastName = lastName.substring(0, 12) + "..";
            }

            // On affiche le prenom et le nom
            viewHolder.getActorName().setText(firstName);
            viewHolder.getActorName2().setText(lastName);
        } else {
            viewHolder.getActorName().setText(localDataSet.get(position).getName());
            viewHolder.getActorName2().setText("");
        }

        if (localDataSet.get(position).getProfile_path() != null) {
            Picasso.get().load("https://image.tmdb.org/t/p/w300" + localDataSet.get(position).getProfile_path())
                    .into(viewHolder.getActorImage());
        } else {
            viewHolder.getActorImage().setImageResource(R.drawable.ic_baseline_person);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void updateActor(ArrayList<Actor> actors) {
        localDataSet = actors;
        notifyDataSetChanged();
    }
}
