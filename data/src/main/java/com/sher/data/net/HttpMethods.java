package com.sher.data.net;

import com.brooke.sher.app2.net.BasehttpMethods;
import com.brooke.sher.app2.net.HttpCallback;
import com.sher.data.MusicInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sher on 2018/3/30.
 */

public class HttpMethods extends BasehttpMethods{

    public static HttpMethods INSTANCE = null;
    private NetService service ;
    private List<List<MusicInfo>> musicInfos;

    private HttpMethods(){
        super();
        service = retrofit.create(NetService.class);
    }

    public  static HttpMethods getInstance(){
        if (INSTANCE == null){
            INSTANCE = new HttpMethods();
        }
        return INSTANCE;
    }

    public List<List<MusicInfo>> getMusic(final HttpCallback callback){

//        service.getMusics().enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String s = new String(response.body().bytes());
//                    Log.i("ssss","success"+s);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                callback.onComplete();
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                t.printStackTrace();
//                Log.i("ssss","success"+t);
//                callback.onError();
//            }
//        });
//
//    return null;
        service.getMusic().enqueue(new Callback<List<List<MusicInfo>>>() {
            @Override
            public void onResponse(Call<List<List<MusicInfo>>> call, Response<List<List<MusicInfo>>> response) {
                musicInfos =  response.body();
//                for (List<MusicInfo> infos : musicInfos){
//                    for (MusicInfo info :infos){
//                        Log.i("ssss",info.getName());
//                    }
//                 }
                callback.onComplete(response);
            }

            @Override
            public void onFailure(Call<List<List<MusicInfo>>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
        return musicInfos ;
    }


}
