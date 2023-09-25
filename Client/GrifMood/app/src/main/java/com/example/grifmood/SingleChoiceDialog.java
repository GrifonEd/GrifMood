package com.example.grifmood;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class SingleChoiceDialog extends androidx.fragment.app.DialogFragment {
    int position = 0; //позиция,отмеченная по умолчанию
    public interface SingleChoiceListener{
        void onPositiveButtonClicked(String [] list, int position);
        void onNegativeButtonClicked();
    }

    SingleChoiceListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (SingleChoiceListener)  context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+" SingleChoice...");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] list = getActivity().getResources().getStringArray(R.array.choice_items);
        builder.setTitle("Обратитесь за бесплатной психологической помощью:")
                .setSingleChoiceItems(list, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        position = which;
                    }
                })
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onPositiveButtonClicked(list, position);

                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onNegativeButtonClicked();
                        dialog.dismiss();
                    }
                });
                return builder.create();
    }
}
