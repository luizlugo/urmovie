package mx.volcanolabs.urmovie.adapters;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mx.volcanolabs.urmovie.R;
import mx.volcanolabs.urmovie.entities.MovieReview;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder> {
    List<MovieReview> reviews;

    public void setData(List<MovieReview> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        if (reviews != null) {
            return reviews.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvComment;
        TextView tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvComment = itemView.findViewById(R.id.tv_comment);
            tvAuthor = itemView.findViewById(R.id.tv_author);
        }

        public void bind(MovieReview movieReview) {
            tvAuthor.setText(movieReview.getAuthor());

            if (movieReview.getContent() != null && movieReview.getContent().length() > 50) {
                String comment = movieReview.getContent().substring(0, 49);
                String readMore = " Read more.";
                comment += "." + readMore;
                int readMoreStart = comment.indexOf(readMore);
                SpannableStringBuilder spannable = new SpannableStringBuilder(comment);
                spannable.setSpan(
                        new URLSpan(movieReview.getUrl()),
                        readMoreStart,
                        readMoreStart + readMore.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvComment.setText(spannable);
                tvComment.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                tvComment.setText(movieReview.getContent());
            }
        }
    }
}
