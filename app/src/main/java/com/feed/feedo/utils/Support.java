package com.feed.feedo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feed.feedo.R;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Support
{
    public static Context con;
    public static Toast mToast;

    public static boolean isNetworkOnline(Context con) {
        boolean status;
        try {

            ConnectivityManager cm = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);

            if (netInfo != null && netInfo.getState() == State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);

                if (netInfo != null && netInfo.getState() == State.CONNECTED) {
                    status = true;
                } else {
                    status = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return status;
    }

    public static void startActivity(Context con, Class<?> className) {
        con.startActivity(new Intent(con, className));
    }

    public static int getRandomNumber()
    {
        Random rand = new Random();
        int randomNumber = rand.nextInt(50) + 1;
        return randomNumber;
    }



    public static void showToast(Context con, String message)
    {
        if (mToast != null)
        {
            mToast.setText(message);
            mToast.show();
        }
        else
            {
            mToast = Toast.makeText(con, message, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }


    public static Bitmap getBitmapFromView(View v, int width, int height) {

//        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(b);
//        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
//        v.draw(c);
//        c.restore();
//        return b;

        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            //Log.e(TAG, "failed getViewBitmap(" + v + ")", new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    public static void showNoInternetAlertDialog(Context con) {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setTitle(" Alert");
        builder.setMessage("Please Check Internet Connectivity");
        builder.setIcon(R.drawable.logo);
        builder.setCancelable(false);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //Toast.makeText(con,"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public static void showNoInternetAlertDialog(Context con, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setTitle(" Alert");
        builder.setMessage("Please Check Internet Connectivity");
        builder.setIcon(R.drawable.logo);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", listener);
        builder.show();
    }


    public static void showAlertDialog(Context con, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setTitle(" Alert");
        builder.setMessage(message);
        builder.setIcon(R.drawable.logo);
        builder.setCancelable(false);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        //Toast.makeText(con,"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public static void setViewHeight(View v, float ht) {
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.height = (int) ht;
        v.setLayoutParams(lp);

    }

    public static void startFragment(FragmentManager fm, Fragment f) {
        fm.beginTransaction().replace(android.R.id.content, f).addToBackStack(null).commit();
    }


    public static boolean isResultValid(String result) {
        if (result != null && !result.trim().toString().equals("")) {
            return true;
        }
        return false;
    }

    public static String getJSON(Cursor c) {
        try {
            String[] names = c.getColumnNames();
            if (c.moveToFirst()) {
                JSONArray jso = new JSONArray();
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    JSONObject obj = new JSONObject();
                    for (String name : names) {
                        obj.put(name, c.getString(c.getColumnIndex(name)));
                    }
                    jso.put(obj);
                }

                return jso.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public static Bitmap getBitmap(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static String getDate(String date, String fromFormat, String toFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fromFormat, Locale.getDefault());
            Date d = sdf.parse(date);
            sdf = new SimpleDateFormat(toFormat, Locale.getDefault());
            return sdf.format(d);
        } catch (Exception ignore) {

        }
        return "";
    }

    public static void showMessageOKCancel(Context con, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(con, R.style.ThemeOverlay_MyDialogActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    public static boolean addPermission(Context con, List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(con, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) con, permission))
                return false;
        }
        return true;
    }

    public static void showSnackbar(Context con, String msg)
    {
        View v = ((Activity) con).findViewById(R.id.llParent);
        if (v != null) {
            Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show();
        } else {

            if (v != null) {
                Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show();
            } else {
                showToast(con, msg);
            }
        }
    }


    public static File getTempFile(Context con) {
        File mFileTemp;

        mFileTemp = new File(getBaseFolderPath(con), "temp.jpg");

        return mFileTemp;
    }

    public static File getTempFile(Context con, String fileName) {
        File mFileTemp;

        mFileTemp = new File(getBaseFolderPath(con), fileName + ".jpg");

        return mFileTemp;
    }


    public static String getBaseFolderPath(Context con)
    {
        String folderName = Constant.BASE_FOLDER_PATH;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            folderName = Environment.getExternalStorageDirectory() + "/"
                    + folderName;
        }
        else
        {
            folderName = con.getFilesDir() + "/" + folderName;
        }
        File f = new File(folderName);
        if (!f.exists()) {
            f.mkdirs();
            File nomedia = new File(folderName + "/" + ".NOMEDIA");
            try
            {
                nomedia.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return folderName;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int reqHeight, int reqWidth)
    {

        if (bm == null)
        {
            return bm;
        }

        int width = bm.getWidth();
        int height = bm.getHeight();

        /*
         * int finalHt = reqHeight; int finalWt = reqWidth;
         */

        if (reqWidth == 0) {
            reqWidth = (width * reqHeight) / height;
        } else {
            reqHeight = (height * reqWidth) / width;
        }

        float scaleWidth = ((float) reqWidth) / width;
        float scaleHeight = ((float) reqHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException
    {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1)
        {
            output.write(buffer, 0, bytesRead);
        }
    }


    public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            String regex = "^[0-9]+$";
            Matcher matcher = Pattern.compile(regex).matcher(phoneNumber);
            if (matcher.find()) {
                return true;
            }
        }
        return false;

    }


    public static boolean isValidateMobile(Context con, String mobileNumber)
    {
        if (mobileNumber.length() == 0)
        {
            Support.showToast(con, con.getString(R.string.msg_enter_mobile_number));
            return false;
        }
        if (!Support.isValidPhoneNumber(mobileNumber))
        {
            Support.showToast(con, con.getString(R.string.msg_invalid_mobile_number));
            return false;
        }
        if (mobileNumber.length() != 10)
        {
            Support.showToast(con, con.getString(R.string.msg_invalid_mobile_number));
            return false;
        }

        return true;
    }

    public static boolean isValidEmailId(String email)
    {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


    public static String getDeviceId(Context con)
    {
        String android_id = Settings.Secure.getString(con.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return android_id;
    }


    public static Spannable increaseFontSizeForPath(Spannable spannable, String path, float increaseTime)
    {
        int startIndexOfPath = spannable.toString().indexOf(path);
        spannable.setSpan(new RelativeSizeSpan(increaseTime), startIndexOfPath,
                startIndexOfPath + path.length(), 0);

        return spannable;
    }


    public static Configuration setDefaultLang(Context con, String lang)
    {
        Locale locale;
        if (!TextUtils.isEmpty(lang))
        {
            locale = new Locale(lang);
            Locale.setDefault(locale);
        }
        else
        {
            locale = Locale.getDefault();
        }
        Configuration config = new Configuration();
        config.locale = locale;
        con.getResources().updateConfiguration(config, con.getResources().getDisplayMetrics());
        return config;
    }
}