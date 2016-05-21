package me.icxd.bookshelve.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
     * @param title 屏幕显示的文字
     * @return fragment
     */
    public static MyFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);

        MyFragment fragment = new MyFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("MyFragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("MyFragment", "onResume");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("MyFragment", "onViewCreated");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("MyFragment", "onAttach");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("MyFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("MyFragment", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("MyFragment", "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("MyFragment", "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("MyFragment", "onDetach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTitle = getArguments().getString(BUNDLE_TITLE);
        }
        Log.e("MyFragment", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(mTitle);
        textView.setGravity(Gravity.CENTER);

        Log.e("MyFragment", "onCreateView");
        return textView;
    }

}
