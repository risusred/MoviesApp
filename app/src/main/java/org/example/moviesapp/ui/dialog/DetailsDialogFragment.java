package org.example.moviesapp.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;

import org.example.moviesapp.R;
import org.example.moviesapp.utils.U;


@SuppressWarnings("ConstantConditions")
public class DetailsDialogFragment extends DialogFragment {

    @NonNull private static final String LOG_TAG_ST = DetailsDialogFragment.class.getSimpleName();


    protected static void showMyFragment(@Nullable AppCompatActivity a,
                                         @Nullable DialogFragment fragment) {
        U.l(LOG_TAG_ST, "showMyFragment");
        if (a == null || fragment == null) return;
        try {
            Class<? extends DialogFragment> aClass = fragment.getClass();
            if (aClass != null) {
                String simpleName = aClass.getSimpleName();
                if (simpleName != null) {

                    final String tag = simpleName + "_tag";
                    fragment.show(a.getSupportFragmentManager(), tag);
                }
            }
        } catch (IllegalStateException e) {//error developer
            U.le(LOG_TAG_ST, "ko " + e);
        } catch (Exception e) {
            U.le(LOG_TAG_ST, "ko " + e);
        }
    }
    
    public static void showInfoDialogFragment(@NonNull AppCompatActivity a,
                                              @NonNull String title,
                                              @NonNull String imgUrl,
                                              @NonNull String genre,
                                              @NonNull String overview,
                                              @NonNull String releaseDate,
                                              @NonNull String lang_original) {

        showMyFragment(a, DetailsDialogFragment.newInstance(title,
                imgUrl, genre, overview, releaseDate, lang_original));
    }

    private static DetailsDialogFragment newInstance(@NonNull String title,
                                                     @NonNull String imgUrl,
                                                     @NonNull String genre,
                                                     @NonNull String overview,
                                                     @NonNull String releaseDate,
                                                     @NonNull String lang_original) {
        DetailsDialogFragment frag = new DetailsDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_IMG_URL, imgUrl);
        args.putString(ARG_GENRE, genre);
        args.putString(ARG_OVERVIEW, overview);
        args.putString(ARG_RELEASE_DATE, releaseDate);
        args.putString(ARG_LANGUAGE_ORIGINAL, lang_original);
        frag.setArguments(args);
        return frag;
    }

    protected static final String ARG_TITLE = "ARG_TITLE";
    protected static final String ARG_IMG_URL = "ARG_IMG_URL";
    protected static final String ARG_GENRE = "ARG_GENRE";
    protected static final String ARG_OVERVIEW = "ARG_OVERVIEW";
    protected static final String ARG_RELEASE_DATE = "ARG_RELEASE_DATE";
    protected static final String ARG_LANGUAGE_ORIGINAL = "ARG_LANGUAGE_ORIGINAL";

    @NonNull private String title = "";
    @NonNull private String img_url = "";
    @NonNull private String genre = "";
    @NonNull private String overview = "";
    @NonNull private String releaseDate = "";
    @NonNull private String lang_original = "";

    private void initAllArguments() {
        final Bundle arguments = getArguments();
        if (arguments != null) {
            title = arguments.getString(ARG_TITLE);
            img_url = arguments.getString(ARG_IMG_URL);
            genre = arguments.getString(ARG_GENRE);
            overview = arguments.getString(ARG_OVERVIEW);
            releaseDate = arguments.getString(ARG_RELEASE_DATE);
            lang_original = arguments.getString(ARG_LANGUAGE_ORIGINAL);
        }
    }

    @MainThread
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        @SuppressLint("InflateParams") View view =
                inflater.inflate(R.layout.details_layout, null);
        initAllArguments();
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        ImageView imageView = view.findViewById(R.id.iv_item_detail);
        TextView textView = view.findViewById(R.id.tv_item_detail);

        String textUi = getFullText();
        if (textView != null) textView.setText(textUi);

        Glide.with(view.getContext()).load(img_url).into(imageView);
    }

    @Override
    public void onResume() {
        super.onResume();
        final Dialog dialog = getDialog();
        if(dialog != null) {
            Window window = dialog.getWindow();
            if(window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                window.setAttributes(params);
            }
        }
    }

    @NonNull private String getFullText() {
        final FragmentActivity activity = getActivity();
        if (activity == null) return "";
        final Resources resources = activity.getResources();

        StringBuilder sb = new StringBuilder();

        String textUi = resources.getString(R.string.original_title);
        sb.append(textUi).append(": ").append(title).append("\n\n");

        textUi = resources.getString(R.string.genre_ids);
        sb.append(textUi).append(": ").append(genre).append("\n");

        textUi = resources.getString(R.string.overview);
        sb.append(textUi).append(": ").append(overview).append("\n");

        textUi = resources.getString(R.string.release_date);
        sb.append(textUi).append(": ").append(releaseDate).append("\n");

        textUi = resources.getString(R.string.language_original);
        sb.append(textUi).append(": ").append(lang_original).append("\n");

        return sb.toString();
    }
}