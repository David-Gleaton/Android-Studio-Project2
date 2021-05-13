package com.example.gleatonhw2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/*
David Gleaton - C88379585 - hgleato@clemson.edu
2/17/21
This Fragment prompts the user to either start a new game or quit, a bundle is passed to see if either the play won or lost
 */

public class WinLossFragment extends DialogFragment {

    //Interface for hosting activity to use
    public interface OnWinLossClickListener{
        void onWinLoss(int which);
    }

    private OnWinLossClickListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle
                                         savedInstanceState) {


        //Get winner
        int mWinner = getArguments().getInt("winner");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setTitle(R.string.Loss_Title);

        //If win, congratulate, if loss, make fun of
        if(mWinner == 1){
            builder.setTitle(R.string.Loss_Title);
        }else{
            builder.setTitle(R.string.Win_Title);
        }

        builder.setMessage(R.string.Continue_or_Quit);
        builder.setPositiveButton(R.string.New_Game, (dialog, which) -> {
            int pos = 1;
            mListener.onWinLoss(pos);

        });
        builder.setNegativeButton(R.string.Quit, (dialog, which) -> {
            int pos = 0;
            mListener.onWinLoss(pos);
        });
        return builder.create();
    }

    //newInstance class used to pass in information from .GameActivity to the Fragment
    public static WinLossFragment newInstance(int num) {
        WinLossFragment fragment = new WinLossFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("winner", num);
        fragment.setArguments(args);

        return fragment;
    }

    //Attach and Detach Classes
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (WinLossFragment.OnWinLossClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
