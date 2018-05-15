package com.streamstech.droid.mshtb.util;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Video;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Peli
 * @version 2009-07-03
 */
public class FileUtils {

    /**
     * TAG for log messages.
     */
    static final String TAG = "FileUtils";
    private static final int X_OK = 1;
    public static final String NOMEDIA_FILE_NAME = ".nomedia";

    private static boolean libLoadSuccess;

    static {
        try {
            System.loadLibrary("access");
            libLoadSuccess = true;
        } catch (UnsatisfiedLinkError e) {
            libLoadSuccess = false;
            Log.d(TAG, "libaccess.so failed to load.");
        }
    }

    /**
     * use it to calculate file count in the directory recursively
     */
    private static int fileCount = 0;

    /**
     * Whether the filename is a video file.
     *
     * @param filename
     * @return
     *//*
    public static boolean isVideo(String filename) {
		String mimeType = getMimeType(filename);
		if (mimeType != null && mimeType.startsWith("video/")) {
			return true;
		} else {
			return false;
		}
	}*/

    /**
     * Whether the URI is a local one.
     *
     * @param uri
     * @return
     */
    public static boolean isLocal(String uri) {
        if (uri != null && !uri.startsWith("http://")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param uri
     * @return Extension including the dot("."); "" if there is no extension;
     * null if uri was null.
     */
    public static String getExtension(String uri) {
        if (uri == null) {
            return null;
        }

        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot);
        } else {
            // No extension.
            return "";
        }
    }

    /**
     * Returns true if uri is a media uri.
     *
     * @param uri
     * @return
     */
    public static boolean isMediaUri(String uri) {
        if (uri.startsWith(Audio.Media.INTERNAL_CONTENT_URI.toString())
                || uri.startsWith(Audio.Media.EXTERNAL_CONTENT_URI.toString())
                || uri.startsWith(Video.Media.INTERNAL_CONTENT_URI.toString())
                || uri.startsWith(Video.Media.EXTERNAL_CONTENT_URI.toString())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Convert File into Uri.
     *
     * @param file
     * @return uri
     */
    public static Uri getUri(File file) {
        if (file != null) {
            return Uri.fromFile(file);
        }
        return null;
    }

    /**
     * Convert Uri into File.
     *
     * @param uri
     * @return file
     */
    public static File getFile(Uri uri) {
        if (uri != null) {
            String filepath = uri.getPath();
            if (filepath != null) {
                return new File(filepath);
            }
        }
        return null;
    }

    /**
     * Returns the path only (without file name).
     *
     * @param file
     * @return
     */
    public static File getPathWithoutFilename(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                // no file to be split off. Return everything
                return file;
            } else {
                String filename = file.getName();
                String filepath = file.getAbsolutePath();

                // Construct path without file name.
                String pathwithoutname = filepath.substring(0, filepath.length() - filename.length());
                if (pathwithoutname.endsWith("/")) {
                    pathwithoutname = pathwithoutname.substring(0, pathwithoutname.length() - 1);
                }
                return new File(pathwithoutname);
            }
        }
        return null;
    }

    /**
     * Constructs a file from a path and file name.
     *
     * @param curdir
     * @param file
     * @return
     */
    public static File getFile(String curdir, String file) {
        String separator = "/";
        if (curdir.endsWith("/")) {
            separator = "";
        }
        File clickedFile = new File(curdir + separator
                + file);
        return clickedFile;
    }

    public static File getFile(File curdir, String file) {
        return getFile(curdir.getAbsolutePath(), file);
    }

    public static String formatSize(Context context, long sizeInBytes) {
        return Formatter.formatFileSize(context, sizeInBytes);
    }

    public static long folderSize(File directory) {
        long length = 0;
        File[] files = directory.listFiles();
        if (files != null)
            for (File file : files)
                if (file.isFile())
                    length += file.length();
                else
                    length += folderSize(file);
        return length;
    }

    public static String formatDate(Context context, long dateTime) {
        return DateFormat.getDateFormat(context).format(new Date(dateTime));
    }

    public static int getFileCount(File file) {
        fileCount = 0;
        calculateFileCount(file);
        return fileCount;
    }

    /**
     * @param f - file which need be checked
     * @return if is archive - returns true othewise
     */
    public static boolean checkIfZipArchive(File f) {
        int l = f.getName().length();
        // TODO test
        if (f.isFile() && FileUtils.getExtension(f.getAbsolutePath()).equals(".zip"))
            return true;
        return false;

        // Old way. REALLY slow. Too slow for realtime action loading.
//        try {
//            new ZipFile(f);
//            return true;
//        } catch (Exception e){
//            return false;
//        }
    }

    /**
     * Recursively count all files in the <code>file</code>'s subtree.
     *
     * @param file The root of the tree to count.
     */
    private static void calculateFileCount(File file) {
        if (!file.isDirectory()) {
            fileCount++;
            return;
        }
        if (file.list() == null) {
            return;
        }
        for (String fileName : file.list()) {
            File f = new File(file.getAbsolutePath() + File.separator + fileName);
            calculateFileCount(f);
        }
    }

    public static String getNameWithoutExtension(File f) {
        return f.getName().substring(0, f.getName().length() - getExtension(getUri(f).toString()).length());
    }
}