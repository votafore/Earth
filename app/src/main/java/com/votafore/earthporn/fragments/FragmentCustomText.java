package com.votafore.earthporn.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.votafore.earthporn.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FragmentCustomText extends Fragment {

    public static FragmentCustomText newInstance() {
        return new FragmentCustomText();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_custom_text, container, false);

        final TextView tv = root.findViewById(R.id.test_tv);

        EditText et = root.findViewById(R.id.test_text);

        et.addTextChangedListener(new TextWatcher() {

            private int index = 0;
            private boolean adding = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adding = count < after;
                index = start+count;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                addSpan(s);
                tv.setText(s);
            }

            /************* utils ****************/

            List<CustomSpanner> spans = new ArrayList<>();

            private void addSpan(Editable s){

                if (!adding){
                    spans.remove(index-1);
                } else {
                    spans.add(index, new CustomSpanner());
                }

                s.removeSpan(ForegroundColorSpan.class);

                for (ForegroundColorSpan span: spans){
                    s.setSpan(span, spans.indexOf(span), spans.indexOf(span)+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        });

        return root;
    }



    public static class CustomSpanner extends ForegroundColorSpan {

        static int getRandomColor(){
            Random gen = new Random(System.currentTimeMillis());
            return Color.rgb(gen.nextInt(255), gen.nextInt(255), gen.nextInt(255));
        }

        CustomSpanner(){
            super(getRandomColor());
        }
    }
}
