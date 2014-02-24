package com.github.techisfun.android.utils.cache;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.*;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
public class FileCache {

    private static final long MAX_CACHE_SIZE = 500 * 1024 * 1024; // 500k

    private static final String TAG = FileCache.class.getSimpleName();

    private File cacheDir;

    private static FileCache instance;

    private FileCache(Context context){
        //Find the dir to save cached images
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            cacheDir = context.getExternalCacheDir();
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();

        Log.d(TAG, "cache dir: " + cacheDir.getAbsolutePath());

    }

    public static synchronized FileCache getInstance(Context context) {
        if (instance == null) {
            instance = new FileCache(context);
        }

        return instance;
    }

    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;
    }

    public void clearAll(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }

    public void clear() {
        File[] files = cacheDir.listFiles();

        long cacheSize = 0;

        for (File file : files) {
            cacheSize += file.length();
        }

        if (cacheSize <= MAX_CACHE_SIZE) {
            // ok, do nothing
            return;
        }

        // go on and delete older files

        List<File> fileList = new ArrayList<File>(files.length);
        Collections.addAll(fileList, files);

        // sort by date
        Collections.sort(fileList, new Comparator<File>() {

            @Override
            public int compare(File f1, File f2) {
                if (f1 == f2) {
                    return 0;
                } else if (f1 == null) {
                    return -1;
                } else if (f2 == null) {
                    return 1;
                } else {
                    return Long.valueOf(f1.lastModified())
                            .compareTo(Long.valueOf(f2.lastModified()));
                }
            }
        });

        while (cacheSize > MAX_CACHE_SIZE) {
            File target = fileList.get(fileList.size() - 1);
            cacheSize -= target.length();
            fileList.remove(target);
            target.delete();
        }
    }
}
