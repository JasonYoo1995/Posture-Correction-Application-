package com.example.bottomtab.etc;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomtab.R;
import com.example.bottomtab.ui.YouTubeAdapter;
import com.example.bottomtab.ui.YouTubeContent;
import com.google.android.youtube.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class ContentsFragment extends Fragment {
    YouTubePlayerView youTubePlayerView;
    View rootview;
    View textview;
    Context context;
    YouTubePlayer.OnInitializedListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.youtube_view, container, false);
        textview = inflater.inflate(R.layout.card_youtube_content,container,false);
        List<YouTubeContent> contents = new ArrayList<>();
        List<String> text = new ArrayList<>();
        context = container.getContext();
        //라운드숄더 교정루틴 A
        contents.add(new YouTubeContent("qMtyhDDmJ-U"));
        //거북목 교정법
        contents.add(new YouTubeContent("Io5NYpzfsEU"));
        //[후기]5분만에 라운드 숄더, 굽은등 펴기
        contents.add(new YouTubeContent("tzJbwflDPhY"));
//        TextView text1=(TextView)textview.findViewById(R.id.textView2);
//        text1.setText(text.get(2));
        //textView.setText(text.get(2));
//        CardView cardView= (CardView)rootview.findViewById(R.id.card_you_tube_content);

        RecyclerView recyclerView = (RecyclerView)rootview.findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true));
        recyclerView.scrollToPosition(contents.size()-1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new YouTubeAdapter(contents));
//textview로 시도해보면 어떨까?
//        View.OnClickListener listener=new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch(v.getId())
//                {
//                    case R.id.imageView:
//                        text.
//
//                }
//
//            }
//        };
//        ImageView button = (ImageView) imageview.findViewById(R.id.imageView);
//        button.setOnClickListener(listener);
        //유튜브 자동재생 방지
        //getViewLifecycleOwner().getLifecycle().addObserver(youTubePlayerView);
        return rootview;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        youTubePlayerView.release();
//    }
}