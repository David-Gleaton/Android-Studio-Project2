package com.example.gleatonhw2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/*
David Gleaton - C88379585 - hgleato@clemson.edu
This fragment prompts the user to enter their username, max of 20 characters
 */


public class GetNameFragment extends DialogFragment {
    //Interface for hosting activity to use
    public interface OnNameClickListener{
        void onNameValue(String value);
    }

    //Listener for data passing
    private OnNameClickListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle
                                         savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setTitle(R.string.Name_Title);
        builder.setMessage(R.string.Username_Title);
        final View view = inflater.inflate(R.layout.dialog_name,null);
        builder.setView(view);
        builder.setPositiveButton(R.string.confirm_dialog, (dialog, which) -> {
            //Grab input number from Edittext and pass to host activity
            EditText editText = view.findViewById(R.id.Name_Dialog);
            //Set default name to Player
            String name = "Player";
            if(!TextUtils.isEmpty(editText.getText())){
                name = editText.getText().toString();
            }
            name += ": ";
            mListener.onNameValue(name);
        });
        return builder.create();
    }

    //Attach and Detach Classes
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnNameClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
