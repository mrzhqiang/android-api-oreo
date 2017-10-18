package cn.mrzhqiang.android_api_oreo.ue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.mrzhqiang.android_api_oreo.R;


public class UEFragment extends Fragment {

    private Button btnNotification;

    public static UEFragment newInstance() {

        Bundle args = new Bundle();

        UEFragment fragment = new UEFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ue, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init
        init(view);
        setEvent();

        getActivity().setTitle("用户体验");
    }

    private void setEvent() {
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, NotificationActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void init(View view) {
        btnNotification = view.findViewById(R.id.btn_notification);

    }
}
