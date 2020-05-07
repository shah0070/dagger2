package com.ecommerce.myapplication.dialogs;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ecommerce.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThemedInfoDialog extends DialogFragment {

    private static final String KEY_MESSAGE = "message";
    private static final String KEY_TITLE = "title";
    private static final String KEY_POSITIVE_BUTTON_TEXT = "positive_button_text";
    private static final String KEY_NEGATIVE_BUTTON_TEXT = "negative_button_text";
    private static final String KEY_SHOW_CANCEL_BUTTON = "show_cancel_button";

    public View.OnClickListener okListener;

    TextView titleTv;

    TextView messageTv;

    Button okButton;

    Button cancelButton;
    boolean button;

    public View.OnClickListener getOkListener() {
        return okListener;
    }

    public void setOkListener(View.OnClickListener okListener) {
        this.okListener = okListener;
    }

    public static ThemedInfoDialog newInstance(String title, String message, String positiveText,
                                               String negativeText, boolean cancelButton) {
        Bundle bundle = createBundle(title, message, positiveText, negativeText, cancelButton);
        ThemedInfoDialog dialog = new ThemedInfoDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    protected static Bundle createBundle(String title, String message,
                                         String positiveButtonText, String negativeButtonText, boolean cancelButton) {
        Bundle args = new Bundle();
        args.putString(KEY_MESSAGE, message);
        args.putString(KEY_TITLE, title);
        args.putString(KEY_POSITIVE_BUTTON_TEXT, positiveButtonText);
        args.putString(KEY_NEGATIVE_BUTTON_TEXT, negativeButtonText);
        args.putBoolean(KEY_SHOW_CANCEL_BUTTON, cancelButton);

        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Applying the theme
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_themed_dialog, container, false);
        ButterKnife.bind(this, rootView);
        getDialog().setCanceledOnTouchOutside(true);
        Bundle args = getArguments();

        titleTv=rootView.findViewById(R.id.tv_title);
        messageTv=rootView.findViewById(R.id.tv_message);
        okButton=rootView.findViewById(R.id.btn_ok);
        cancelButton=rootView.findViewById(R.id.btn_no);


        messageTv.setText(args.getString(KEY_MESSAGE));
        titleTv.setText(args.getString(KEY_TITLE));
        cancelButton.setVisibility(View.GONE);
        button = args.getBoolean(KEY_SHOW_CANCEL_BUTTON);
        if (button) {
            cancelButton.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(args.getString(KEY_POSITIVE_BUTTON_TEXT))) {
            okButton.setText(getString(android.R.string.ok));
        } else {
            okButton.setText(args.getString(KEY_POSITIVE_BUTTON_TEXT));
        }

        return rootView;
    }

    @OnClick(R.id.btn_ok)
    void onClickOk(View view) {
        dismiss();
        if (okListener != null) {
            okListener.onClick(view);
        }
    }

    @OnClick(R.id.btn_no)
    public void onClickCancel() {
        dismiss();
    }
}