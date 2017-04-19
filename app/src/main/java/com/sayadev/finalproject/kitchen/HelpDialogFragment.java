package com.sayadev.finalproject.kitchen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.webkit.WebView;

import com.sayadev.finalproject.R;


public class HelpDialogFragment extends DialogFragment {

    public static HelpDialogFragment newInstance() {
        return new HelpDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        WebView view = (WebView) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_webview, null);
        view.loadUrl("file:///android_asset/help.html");
        return new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(getString(R.string.kitchen_toolbar_menu_help))
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
