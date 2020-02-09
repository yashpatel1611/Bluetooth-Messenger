package com.yashpatel.bluetoothmessenger;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_NONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_SEND;
import static android.view.inputmethod.EditorInfo.IME_ACTION_UNSPECIFIED;
import static android.view.inputmethod.EditorInfo.IME_NULL;


public class everyone_fragment extends Fragment {

    public everyone_fragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_everyone, container, false);
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}



