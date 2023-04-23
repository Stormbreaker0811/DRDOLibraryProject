package com.example.drdolibraryproject.canteenstorage;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

public class CanteenRetrieve {
    Context context;
    String name,extension,directoryDownloads,url;
    public CanteenRetrieve(Context context,String name,String extension,String directoryDownloads,String url){
        this.context = context;
        this.name = name;
        this.extension = extension;
        this.directoryDownloads = directoryDownloads;
        this.url = url;
    }
    public void downloadFile(){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request req = new DownloadManager.Request(uri);
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        req.setDestinationInExternalPublicDir(directoryDownloads,name+extension);
        downloadManager.enqueue(req);
    }
}
