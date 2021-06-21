package org.example.moviesapp.ui.recycler;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;


import org.example.moviesapp.R;
import org.example.moviesapp.room.MovieEnty;
import org.example.moviesapp.utils.U;

import java.util.List;

public class EntyAdapter
        extends RecyclerView.Adapter<EntyViewHolder>
        implements EntyViewHolder.Listener {


    public interface Listener {
        void onClickRecycler(@Nullable MovieEnty enty);
    }

    @NonNull private final String LOG_TAG = getClass().getSimpleName();
    @Nullable private Resources resources;
    @Nullable private Listener listener;
    @Nullable private List<MovieEnty> items;
    @Nullable private EntyViewHolder myViewHolder;

    public EntyAdapter(@Nullable Resources resources){
        this.resources = resources;
    }

    public void setListener(@Nullable Listener l){ listener = l; }

    public void destroy() {
        if (myViewHolder != null) myViewHolder.destroy();
        myViewHolder = null;
        items = null;
        listener = null;
        resources = null;
    }

    @NonNull
    @Override
    public EntyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        U.l(LOG_TAG, "onCreateViewHolder");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        myViewHolder = new EntyViewHolder(view, resources, this);
        return myViewHolder;
    }
    @UiThread
    @Override public void onBindViewHolder(@NonNull EntyViewHolder holder, int position) {
        if (items != null) {
            MovieEnty enty = null;
            try {
                enty = items.get(position);
            } catch (Exception e) {
                U.l(LOG_TAG, "ERR " +e);
            }
            if (enty != null) {
                holder.bindDataList(enty);
            }
        }
    }

    @Override public int getItemCount() {
        if(items != null) {
            try {
                return items.size();
            } catch (Exception e) {
                U.l(LOG_TAG, "ERR " +e);
            }
        }
        return 0;
    }

    @Nullable private MovieEnty getMyItem(int position) {
        if(items != null) {
            try {
                return items.get(position);
            } catch (Exception e) {
                U.l(LOG_TAG, "ERR " +e);
            }
        }
        return null;
    }

    public void setMyItems(@Nullable List<MovieEnty> myItems) {
        U.l(LOG_TAG, "setMyItems");
        if(myItems != null) {
            this.items = myItems;
            notifyDataSetChanged();
        }
    }

//    public void clearRecyclerUi() { setMyItems(new ArrayList<>()); }

    @Override public void onClickRecycler(int position) {
        U.l(LOG_TAG, "onClickRecycler " + position);

        MovieEnty myItem = getMyItem(position);
        if (listener != null) listener.onClickRecycler(myItem);
    }

}