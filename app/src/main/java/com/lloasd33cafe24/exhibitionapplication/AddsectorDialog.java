package com.lloasd33cafe24.exhibitionapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddsectorDialog extends AppCompatDialogFragment {

    private EditText editTextSectorname;
    private EditText editTextNumber;
    private  AddsectorDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_sector, null);

        builder.setView(view).setTitle("구역 추가")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    String sectorname = editTextSectorname.getText().toString();
                    String numbertemp = editTextNumber.getText().toString();
                    int number = Integer.parseInt(numbertemp);

                    listener.applyTexts(sectorname, number);
                    }
                });

        editTextSectorname = view.findViewById(R.id.sectorName);
        editTextNumber = view.findViewById(R.id.numberofex);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddsectorDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "AddsectorDIalogListener가 없습니다");
        }
    }

    public interface AddsectorDialogListener{
        void applyTexts(String sectorname, int number);
    }
}
