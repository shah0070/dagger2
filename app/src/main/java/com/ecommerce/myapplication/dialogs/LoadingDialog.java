package com.ecommerce.myapplication.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ecommerce.myapplication.R;


public class LoadingDialog extends DialogFragment {

    public static final String TITLE = "title";

    public static LoadingDialog newInstance(String title) {
        final LoadingDialog loadingDialog = new LoadingDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        loadingDialog.setArguments(bundle);
        return loadingDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loading_dialog, container, true);

        TextView tvTitleDesc = view.findViewById(R.id.tv_step_name);
        tvTitleDesc.setText(getArguments().getString(TITLE));
        setCancelable(false);

        return view;
    }
}