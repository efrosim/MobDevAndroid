package ru.mirea.noskovaa.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Загрузка...");
        progressDialog.setMessage("Пожалуйста, подождите, Носков А.А.");
        progressDialog.setIndeterminate(true);
        return progressDialog;
    }
}