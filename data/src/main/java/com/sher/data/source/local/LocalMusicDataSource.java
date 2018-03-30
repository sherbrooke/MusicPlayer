package com.sher.data.source.local;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.sher.data.MusicInfo;
import com.sher.data.R;
import com.sher.data.source.MusicInfoSource;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2017/9/14.
 */

public class LocalMusicDataSource implements MusicInfoSource {

    private MusicInfo musicInfo;
    private static final Uri sArtworkUri = Uri
            .parse("content://media/external/audio/albums");
    private static LocalMusicDataSource INSTANCE;

    private LocalMusicDataSource(){

    }

    public static LocalMusicDataSource getINSTANCE(){
        if (INSTANCE == null) {
            INSTANCE = new LocalMusicDataSource();
        }
        return INSTANCE;
    }


    @Override
    public List<MusicInfo> getMusic(Context context) {
        List<MusicInfo>   musicInfoList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,  new String[]{MediaStore.Audio.Media._ID,    //写入我想要获得的信息（列）
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID}, null, null, null);
        cursor.moveToFirst();
        do{
            //歌曲ID：MediaStore.Audio.Media._ID
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
//歌曲的名称 ：MediaStore.Audio.Media.TITLE
            String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//        歌曲的专辑名：MediaStore.Audio.Media.ALBUM
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
//歌曲的歌手名： MediaStore.Audio.Media.ARTIST
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//歌曲文件的路径 ：MediaStore.Audio.Media.DATA
            String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
            int  duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
//歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
            long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

//            int albumId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
            long albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
//            Bitmap bitmap = getMusicBitemp(context, id, albumId);
            String bitmap = getAlbumArt(context, albumId);
            musicInfo= new MusicInfo();
            musicInfo.setId(id);
            musicInfo.setTilte(tilte);
            musicInfo.setAlbum(album);
            musicInfo.setArtist(artist);
            musicInfo.setUrl(url);
            musicInfo.setDuration(duration);
            musicInfo.setSize(size);
            musicInfo.setBitmap(bitmap);
            musicInfo.setAlumbId(albumId);

            musicInfoList.add(musicInfo);

        }while (cursor.moveToNext());

        cursor.close();
        return musicInfoList;
    }


    private String getAlbumArt(Context context,long album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[] { "album_art" };
        Cursor cur = context.getContentResolver().query(
                Uri.parse(mUriAlbums + "/" + Long.toString(album_id)),
                projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        cur = null;
        return album_art;
    }



    public static Bitmap getMusicBitemp(Context context, long songid,
                                        long albumid) {
        Bitmap bm = null;
// 专辑id和歌曲id小于0说明没有专辑、歌曲，并抛出异常
        if (albumid < 0 && songid < 0) {
            throw new IllegalArgumentException(
                    "Must specify an album or a song id");
        }
        try {
            if (albumid < 0) {
                Uri uri = Uri.parse("content://media/external/audio/media/"
                        + songid + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver()
                        .openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            } else {
                Uri uri = ContentUris.withAppendedId(sArtworkUri, albumid);
                ParcelFileDescriptor pfd = context.getContentResolver()
                        .openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);


                } else {
                    return null;
                }
            }
        } catch (FileNotFoundException ex) {
        }
//如果获取的bitmap为空，则返回一个默认的bitmap
        if (bm == null) {
            Resources resources = context.getResources();
            Drawable drawable = resources.getDrawable(R.mipmap.ic_launcher);
            //Drawable 转 Bitmap
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            bm = bitmapDrawable.getBitmap();
        }

        return Bitmap.createScaledBitmap(bm, 150, 150, true);
    }
}
