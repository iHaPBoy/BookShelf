package me.icxd.bookshelve.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;
import me.icxd.bookshelve.R;

public class BookCoverFragment extends Fragment {
    private String imageUrl = "";
    private static final String ARG_IMAGE_URL = "image_url";
    private List<Map<String, Object>> data;

    public static BookCoverFragment newInstance(String imageUrl) {
        BookCoverFragment fragment = new BookCoverFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(ARG_IMAGE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_cover, container, false);

        ImageView ivBookCover = (ImageView) view.findViewById(R.id.book_cover);
        ImageView ivBookCoverBg = (ImageView) view.findViewById(R.id.book_cover_bg);


        Glide.with(ivBookCover.getContext())
                .load(imageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(getContext()).icon(GoogleMaterial.Icon.gmd_book).color(Color.GRAY))
                .error(new IconicsDrawable(getContext()).icon(GoogleMaterial.Icon.gmd_book).color(Color.GRAY))
                .into(ivBookCover);

        Glide.with(ivBookCoverBg.getContext())
                .load(imageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new BlurTransformation(ivBookCoverBg.getContext(), 25, 3))
                .into(ivBookCoverBg);

        // 动画
        Animation an = AnimationUtils.loadAnimation(getContext(), R.anim.book_cover_anim);
        ivBookCover.startAnimation(an);

        return view;
    }
}
