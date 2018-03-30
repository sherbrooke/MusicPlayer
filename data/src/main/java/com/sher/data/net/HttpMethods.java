package com.sher.data.net;

import com.brooke.sher.app2.net.BasehttpMethods;
import com.sher.data.MusicInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Sher on 2018/3/30.
 */

public class HttpMethods extends BasehttpMethods{

    public static HttpMethods INSTANCE = null;
    private NetService service ;
    private HttpMethods(){
        super();
        service = retrofit.create(NetService.class);
    }

    public HttpMethods getInstance(){
        if (INSTANCE == null){
            INSTANCE = new HttpMethods();
        }
        return INSTANCE;
    }

    public Observable<List<List<MusicInfo>>> getMusic(){
        return service.getMusic();
    }


}
