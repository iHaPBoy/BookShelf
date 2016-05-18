package me.icxd.bookshelve.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by HaPBoy on 5/10/16.
 */
public class MyFragment extends Fragment {
    private String mTitle; // 传过来的title
    private static final String BUNDLE_TITLE = "title"; //设置bundle的key

    /**
     * fragment一般使用newInstance方法new出实例
     *
     * @param title
     * @return
     */
    public static MyFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);

        MyFragment fragment = new MyFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTitle = getArguments().getString(BUNDLE_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(mTitle);
        textView.setGravity(Gravity.CENTER);

        return textView;
    }

}
