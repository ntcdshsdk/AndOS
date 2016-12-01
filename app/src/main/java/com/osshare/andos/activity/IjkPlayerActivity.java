package com.osshare.andos.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.osshare.andos.R;
import com.osshare.framework.base.BaseActivity;

import tv.danmaku.ijk.media.example.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.example.widget.media.IMediaController;
import tv.danmaku.ijk.media.example.widget.media.IjkVideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by apple on 16/11/14.
 */
public class IjkPlayerActivity extends BaseActivity {
    private static final int REQUEST_CODE_FILE_SELECT=0X7113;
    private Button btnSelect;
    private IjkVideoView videoView;
    private TableLayout hudView;
//    String url = "http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8";
    String url = "http://o6wf52jln.bkt.clouddn.com/演员.mp3";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnSelect = (Button) findViewById(R.id.btn_select);
        videoView = (IjkVideoView) findViewById(R.id.ijk_video_view);
        hudView = (TableLayout) findViewById(R.id.tl_hud_view);

        init();
    }

    private void init() {
        btnSelect.setOnClickListener(clickListener);
        //加载IJK底层库
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        IMediaController mediaController = new AndroidMediaController(this, false);

        videoView.setMediaController(mediaController);
//        videoView.setHudView(hudView);

        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);

        videoView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_select:
                    try {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult( Intent.createChooser(intent, "请选择一个视频文件"), REQUEST_CODE_FILE_SELECT);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

            }
        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode) {
                case REQUEST_CODE_FILE_SELECT:
                    Uri uri = data.getData();
                    videoView.setVideoURI(uri);
                    videoView.start();
                    break;
            }
        }
    }
}
