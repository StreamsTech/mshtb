package com.streamstech.droid.mshtb.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.FileAdapter;
import com.streamstech.droid.mshtb.data.FileData;
import com.streamstech.droid.mshtb.data.FileList;
import com.streamstech.droid.mshtb.data.FilePOJO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FileBrowserActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FileAdapter adapter;
    private ArrayList<FilePOJO> listContentArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FileAdapter(this,this);
        populateRecyclerViewValues("root");

    }

    public void populateRecyclerViewValues(String fileName) {

        listContentArr = new ArrayList<>();
        FileList l = new FileList(fileName);
        List<FileData> data = l.getFileList();
        for (FileData fileData : data) {
            FilePOJO pojoObject = new FilePOJO();
            pojoObject.setFileName(fileData.getValueOne());
            pojoObject.setDetail(fileData.getValueTwo());
            pojoObject.setFileImage(fileData.getValueThree());
            listContentArr.add(pojoObject);
        }
        adapter.setListContent(listContentArr);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(adapter.goBack()) {
            moveTaskToBack(true);
        }
    }

    public void playFile(final String clickedFile, String type)
    {
        System.out.println(clickedFile);
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Restore Database")
                .setContentText("Are you sure you want to restore? All data in current database will be deleted")
                .setConfirmText("Yes!")
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        getApplicationContext().deleteDatabase("epharmacy-android-db");
                        restoreDB(clickedFile);
                        finishAffinity();
                    }
                })
                .show();



//        File file = new File(clickedFile);
//        Intent target = new Intent(Intent.ACTION_VIEW);
//        if(type.compareTo("pdf")==0)
//            target.setDataAndType(Uri.fromFile(file),"application/pdf");
//        else if(type.compareTo("sqlite")==0)
//            target.setDataAndType(Uri.fromFile(file),"audio/*");
//        else if(type.compareTo("txt")==0)
//            target.setDataAndType(Uri.fromFile(file),"text/plain");
//        else
//            target.setDataAndType(Uri.fromFile(file),"image/*");
//
//        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        Intent intent = Intent.createChooser(target, "Open File");
//        try {
//            startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            // Instruct the user to install a PDF reader here, or something
//        }
    }

    private boolean restoreDB (String source) {
        File exportFile =
                new File(Environment.getDataDirectory() +
                        "/data/" + "com.streamstech.droid.mshtb" +
                        "/databases/" + "epharmacy-android-db");
        File importFile = new File (source);
        try {
            exportFile.createNewFile();
            copyFile(importFile, exportFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }
}
