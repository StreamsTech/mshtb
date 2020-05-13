package com.streamstech.droid.mshtb.data;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileList {

    String state = Environment.getExternalStorageState();
    private List<FileData> data = new ArrayList<>();
    String fileName;

    public FileList(String fileName) {
        this.fileName = fileName;
    }

    public List<FileData> getFileList() {

        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            final File musicFilePath;
            if (fileName.compareTo("root") == 0)
                musicFilePath = Environment.getExternalStorageDirectory();
            else {
                musicFilePath = new File(fileName);
            }
            Log.i("CustomError", musicFilePath.toString());
            File[] fileList = musicFilePath.listFiles();
            if (fileList.length == 0) {
                Log.i("CusttomError", "Null");
            }

            data.clear();
            for (int i = 0; i < fileList.length; i++) {
                String baseName[] = fileList[i].toString().split("/");
                if (fileList[i].isDirectory()) {
                    data.add(new FileData(baseName[baseName.length - 1],
                            Integer.toString(fileList[i].listFiles().length) + " files", "folder"));

                } else {
                    if (fileList[i].toString().endsWith("sqlite")) {
                        data.add(new FileData(baseName[baseName.length - 1],
                                Integer.toString((int) fileList[i].length() / 1024) + " Kb", "sqlite"));
                    }
                }
            }

        } else {
            Log.i("CustomError", "SD card not inserted");
        }
        Collections.sort(data, new SortbyType());
        return data;
    }

    class SortbyType implements Comparator<FileData>
    {
        public int compare(FileData a, FileData b)
        {
            return b.getValueThree().compareTo(a.getValueThree());
        }
    }
}


