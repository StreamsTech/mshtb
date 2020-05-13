package com.streamstech.droid.mshtb.data;

import android.os.Environment;

import java.io.File;
import java.util.Stack;

/**
 * Created by mohit on 23/7/17.
 */
public class FolderHistory {

    Stack<String> folderHistory;

    public FolderHistory()
    {
        folderHistory = new Stack<>();
    }

    public Stack<String> getHistory()
    {
        File musicFilePath = Environment.getExternalStorageDirectory();//.getExternalStoragePublicDirectory(
//                Environment.);
        folderHistory.add(musicFilePath.toString());
        return folderHistory;
    }

}
