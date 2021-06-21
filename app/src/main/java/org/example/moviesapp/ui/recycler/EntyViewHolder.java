package org.example.moviesapp.ui.recycler;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.example.moviesapp.R;
import org.example.moviesapp.room.MovieEnty;


class EntyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public interface Listener{
        void onClickRecycler(int position);
    }

    @Nullable private final TextView textView;
    @Nullable private Resources resources;
    @Nullable private Listener listener;
    @Nullable private final ImageView imageView;

    public EntyViewHolder(@NonNull View itemView, @Nullable Resources resources,
                          @Nullable Listener listener) {
        super(itemView);
        this.imageView = itemView.findViewById(R.id.iv_item);
        this.textView = itemView.findViewById(R.id.tv_item);
        this.resources = resources;
        this.listener = listener;
        itemView.setOnClickListener(this);
    }
    public void destroy() {
        resources = null;
        listener = null;
    }
    @Override public void onClick(@Nullable View v) {
        if (listener != null) listener.onClickRecycler(getAbsoluteAdapterPosition());
    }

    public void bindDataList(@NonNull MovieEnty enty) {

        if (textView != null && resources != null){
            String textUi = getFullText(enty, resources);
            textView.setText(textUi);
        }
        //noinspection ConstantConditions
        if (itemView != null && imageView != null) {
            String downloadUri = enty.getPosterPath();
            Glide.with(itemView.getContext()).load(downloadUri).into(imageView);
        }
    }

    @NonNull private String getFullText(@NonNull MovieEnty enty,
                                        @NonNull Resources resources) {

        StringBuilder sb = new StringBuilder();

        String textUi = resources.getString(R.string.original_title);
        sb.append(textUi).append(": ").append(enty.getOriginalTitle()).append("\n\n");

        textUi = resources.getString(R.string.genre_ids);
        sb.append(textUi).append(": ").append(enty.getGenre()).append("\n");

        textUi = resources.getString(R.string.release_date);
        sb.append(textUi).append(": ").append(enty.getReleaseDate()).append("\n");

        return sb.toString();
    }
}
