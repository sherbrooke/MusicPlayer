package com.sher.data.net;

import com.sher.data.MusicInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Sher on 2018/3/30.
 */

public interface NetService {

    @GET("/music")
    Observable<List<List<MusicInfo>>> getMusic();

}
