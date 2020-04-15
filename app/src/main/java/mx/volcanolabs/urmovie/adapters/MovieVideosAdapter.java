package mx.volcanolabs.urmovie.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mx.volcanolabs.urmovie.R;
import mx.volcanolabs.urmovie.entities.MovieVideo;
import mx.volcanolabs.urmovie.listeners.MovieDetailClickListener;

import static mx.volcanolabs.urmovie.Constants.YOUTUBE_THUMB_PATH;

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.ViewHolder> {
    List<MovieVideo> videos;
    MovieDetailClickListener listener;

    public MovieVideosAdapter(MovieDetailClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<MovieVideo> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieVideo movieVideo = videos.get(position);
        holder.bind(movieVideo);
    }

    @Override
    public int getItemCount() {
        if (videos != null) {
            return videos.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivThumb;
        private TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.iv_movie_thumb);
            tvName = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);
        }

        public void bind(MovieVideo movieVideo) {
            Picasso.get().load(String.format(YOUTUBE_THUMB_PATH, movieVideo.getKey())).into(ivThumb);
            tvName.setText(movieVideo.getName());
        }

        @Override
        public void onClick(View view) {
            listener.onVideoClicked(videos.get(getAdapterPosition()));
        }
    }
}
