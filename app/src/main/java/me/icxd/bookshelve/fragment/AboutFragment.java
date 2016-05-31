package me.icxd.bookshelve.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.view.View;

import me.icxd.bookshelve.R;


/**
 * Created by HaPBoy on 16/5/31.
 */
public class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private static final String INTRODUCTION = "introduction";
    private static final String CURRENT_VERSION = "currentVersion";
    private static final String AUTHOR = "author";
    private static final String EMAIL = "email";

    private Preference introduction;
    private Preference currentVersion;
    private Preference author;
    private Preference email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_about);

        introduction = findPreference(INTRODUCTION);
        currentVersion = findPreference(CURRENT_VERSION);
        author = findPreference(AUTHOR);
        email = findPreference(EMAIL);

        introduction.setOnPreferenceClickListener(this);
        currentVersion.setOnPreferenceClickListener(this);
        author.setOnPreferenceClickListener(this);
        email.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        copyToClipboard(getView(), preference.getSummary().toString());
        return false;
    }

    private void copyToClipboard(View view, String info) {
        ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("msg", info);
        manager.setPrimaryClip(clipData);
        Snackbar.make(view, "已经复制到剪切板", Snackbar.LENGTH_SHORT).show();
    }
}
