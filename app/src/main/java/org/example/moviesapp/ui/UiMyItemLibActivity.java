package org.example.moviesapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import org.example.moviesapp.R;
import org.example.moviesapp.room.MovieEnty;
import org.example.moviesapp.ui.dialog.DetailsDialogFragment;
import org.example.moviesapp.ui.recycler.EntyAdapter;
import org.example.moviesapp.utils.KeyboardUtil;

import java.util.List;


public abstract class UiMyItemLibActivity extends BaseActivity
        implements EntyAdapter.Listener {

    @Nullable private TextView tvMsg;
    @Nullable private ProgressBar progressBar;
    @Nullable private EntyAdapter adapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        l("onCreate");
        setContentView(R.layout.items_layout);
        setTitle(getClass().getSimpleName());
        initUi();
    }

    @Override protected void onDestroy() {
        destroyUi();
        super.onDestroy();
    }

    private void initUi() {
        Button btnByTitle = findViewById(R.id.btn_search_by_title);
        if (btnByTitle != null) {
            btnByTitle.setOnClickListener(v -> {
                KeyboardUtil.hideKeyboard(this);
                EditText et = findViewById(R.id.et_search);
                String titleSearch = et.getText().toString();
                //noinspection ConstantConditions
                if(titleSearch != null ){
                    titleSearch = titleSearch.trim();
                    if(titleSearch.length() > 0 ){
                        fetchBySearch(titleSearch);
                        toast("Searching for: " +titleSearch);
                        return;
                    }
                }
                toast("Please enter a valid title search");
            });
        }

        Button btnTopRated = findViewById(R.id.btn_search_by_top_rated);
        if (btnTopRated != null) {
            btnTopRated.setOnClickListener(v -> fetchTopRated());
            btnTopRated.setOnLongClickListener(v -> {
                clearRecycler();
                return true;
            });
        }

        tvMsg  = findViewById(R.id.tv_msg_main);

        RecyclerView recycler = findViewById(R.id.recycler_view_main);
        if (recycler != null) {
            recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//            adapter = new MyAdapter(getResources());
            adapter = new EntyAdapter(getResources());
            //noinspection ConstantConditions
            if (adapter != null){
                adapter.setListener(this);
                recycler.setAdapter(adapter);
            }
        }
        progressBar = findViewById(R.id.progress_bar_main);
        SwipeRefreshLayout swipe =  findViewById(R.id.swipeRefreshLayout_main);
        if (swipe != null) {
            swipe.setOnRefreshListener(() -> {
                swipe.setRefreshing(false);
                fetchTopRated();
            });
        }
    }

    private void destroyUi() {
        if (adapter != null) adapter.destroy();
        adapter =  null;
    }

    //ON CLICK RECYCLER -- LISTENER ADAPTER
    @Override
    public void onClickRecycler(@Nullable MovieEnty myItem) {
        if(myItem == null) return;
        DetailsDialogFragment.showInfoDialogFragment(this,
                myItem.getOriginalTitle(),
                myItem.getPosterPath(),
                myItem.getGenre(),
                myItem.getOverview(),
                myItem.getReleaseDate(),
                myItem.getOriginalLanguage());
    }

    protected void updateUiList(@Nullable List<MovieEnty> myItems) {
        l("updateUiList");
        if (adapter != null) adapter.setMyItems(myItems);
    }

    protected void updateUiLoadingStart() {
        if (tvMsg != null) tvMsg.setText(R.string.loading_ui_state_);
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
    }
    protected void updateUiLoadingEnd() {
        if (tvMsg != null) tvMsg.setText("");
        if (progressBar != null) progressBar.setVisibility(View.GONE);
    }

    protected void updateUiError(@Nullable String msg) {
        String textUi = getResources().getString(R.string.error_loading_ui_state_)
                + ": "+  msg;
        if (tvMsg != null) tvMsg.setText(textUi);
        if (progressBar != null) progressBar.setVisibility(View.GONE);
    }


    protected abstract void fetchTopRated();

    protected abstract void fetchBySearch(String titleSearch);

    @SuppressWarnings("EmptyMethod")
    protected void clearRecycler() {
//        if (adapter != null) adapter.clearRecyclerUi();
    }
}