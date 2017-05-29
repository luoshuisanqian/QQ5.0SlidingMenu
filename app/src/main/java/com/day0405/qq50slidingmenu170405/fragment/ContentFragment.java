package com.day0405.qq50slidingmenu170405.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.day0405.qq50slidingmenu170405.FirstVersonActivity;
import com.day0405.qq50slidingmenu170405.FourVersionActivity;
import com.day0405.qq50slidingmenu170405.R;
import com.day0405.qq50slidingmenu170405.SecondVersionActivity;
import com.day0405.qq50slidingmenu170405.ThirdVersionActivity;

/**
 * Created by huangming on 2017/5/28.
 */

public class ContentFragment extends Fragment {
    private TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        textView = (TextView)view.findViewById(R.id.textView);
        /**
         * 接收activity传给fragment的参数
         */
        final String text = "点击跳转"+getArguments().getString("text");
        textView.setText(text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.contains("1")) {
                    startActivity(new Intent(getActivity(), FirstVersonActivity.class));
                } else if (text.contains("2")) {
                    startActivity(new Intent(getActivity(), SecondVersionActivity.class));
                } else if (text.contains("3")) {
                    startActivity(new Intent(getActivity(), ThirdVersionActivity.class));
                } else if (text.contains("4")) {
                    startActivity(new Intent(getActivity(), FourVersionActivity.class));
                }
            }
        });

        return view;
    }
}
