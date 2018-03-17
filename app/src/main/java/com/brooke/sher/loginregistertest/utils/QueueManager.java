package com.brooke.sher.loginregistertest.utils;

import com.sher.data.MusicInfo;

import java.util.List;

/**
 * Created by Sher on 2017/9/17.
 */

public class QueueManager {

    private List<MusicInfo> musicInfos;
    private int position;

    public QueueManager(List<MusicInfo> musicInfos,int index){
        this.musicInfos = musicInfos;
        this.position = index;
    }

    //    获得当前播放的音乐
    public MusicInfo getCurrentMusic(){
        return musicInfos.get(position);
    }



//            判断是否同一个队列

//            通过音乐ID或者队列id设置队列
//    更新当前的音乐数据


}
