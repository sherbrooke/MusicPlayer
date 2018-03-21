package com.sher.domain.usecase;

import android.support.annotation.NonNull;

import com.sher.data.source.MusicInfoRepository;
import com.sher.domain.UseCase;

/**
 * Created by Sher on 2018/3/21.
 */

public class MusicUsecase extends UseCase<MusicUsecase.RequestValues,MusicUsecase.ResponseValue>{

    private final MusicInfoRepository musicInfoRepository;

    public MusicUsecase(@NonNull MusicInfoRepository musicInfoRepository){

        this.musicInfoRepository = musicInfoRepository;
    }
    @Override
    protected void executeUseCase(RequestValues requestValues) {
        
    }

    public static class RequestValues implements UseCase.RequestValues{


    }

    public static class ResponseValue implements UseCase.ResponseValue{

    }


}
