package com.sher.data.net;

import com.sher.data.MusicInfo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Sher on 2018/3/30.
 */

public interface NetService {

    @GET("Music/music")
//    Observable<List<List<MusicInfo>>> getMusic();
    Call<List<List<MusicInfo>>> getMusic();

    @GET("Music/music")
//    Observable<List<List<MusicInfo>>> getMusic();
    Call<ResponseBody> getMusics();

}
