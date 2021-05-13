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
2/17/21
This Fragment prompts the user to enter a number, max size 3, to use as the target score. Default value is 100, if input is 0 it defaults to 1
 */


public class TargetDialogFragment extends DialogFragment {
    //Interface for hosting activity to use
    public interface OnTargetClickListener{
        void onTargetValue(int value);
    }

    //Listener for data passing
    private OnTargetClickListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle
                                         savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setTitle(R.string.TargetScore);
        builder.setMessage(R.string.pick_target_score);
        final View view = inflater.inflate(R.layout.dialog_target,null);
        builder.setView(view);
        builder.setPositiveButton(R.string.confirm_dialog, (dialog, which) -> {
            //Grab input number from Edittext and pass to host activity
            EditText editText = view.findViewById(R.id.TargetScore_Dialog);
            //Default is 100
            int target = 100;
            if(!TextUtils.isEmpty(editText.getText())){
                target = Integer.parseInt(editText.getText().toString());
            }
            //If input is 0, default is 1
            if(target == 0) { target = 1; }
            mListener.onTargetValue(target);
        });
        return builder.create();
    }

    //Attach and Detach Classes
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnTargetClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
