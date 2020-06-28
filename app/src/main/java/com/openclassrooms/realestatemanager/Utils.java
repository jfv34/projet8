package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.ui.main.MainFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.812);
    }

    public static int convertEuroToDollar(int euro){
        return (int) Math.round(euro / 0.812);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }

    public static String getDateinDayMonthYearFormat(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    public static Boolean isInternetAvailable(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null){return false;}else{
        return networkInfo.isAvailable();}

    }
    public static void toast(Context context, int message) {
        Toast toast = Toast.makeText(context, context.getString(message), Toast.LENGTH_LONG);
        toast.show();
    }

    public static void toast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public static Photo saveToInternalStorage(Bitmap bitmapImage, String description, Context context) {

        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String uniqueString = UUID.randomUUID().toString();
        String photoName = "photo_" + uniqueString;
        File mypath = new File(directory, photoName);
        Log.i("tag_mypath: ", mypath.toString());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Photo(directory.getAbsoluteFile().toString(), photoName, description);
    }

    public static Bitmap loadImageFromStorage(String path, String photoName) {
        Bitmap photo = null;
        try {
            File f = new File(path, photoName);
            photo = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return photo;
    }

    public static String convertDateToString(int year, int month, int dayOfMonth) {
        StringBuilder frenchDate = new StringBuilder();
        frenchDate.append(dayOfMonth >= 10 ? dayOfMonth : "0" + dayOfMonth);
        frenchDate.append("/");
        frenchDate.append(month + 1);
        frenchDate.append("/");
        frenchDate.append(year);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        Date dateSelected = null;
        try {
            dateSelected = simpleDateFormat.parse(frenchDate.toString());
        } catch (ParseException e) {
        }

        return simpleDateFormat.format(dateSelected);
    }
    public static Date convertStringToDate(String entryDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        Date date = null;
        try {
            date = dateFormat.parse(entryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static void addFragmentInDetailScreen(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        final boolean tabletSize = activity.getResources().getBoolean(R.bool.isTablet);
        if (tabletSize || activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.add(R.id.activity_main_frame_layout_detail_large_screen, fragment).addToBackStack(fragment.getClass().getName()).commit();
        } else {
            transaction.add(R.id.frame_layout_main, fragment).addToBackStack(fragment.getClass().getName()).commit();
        }
    }
    public static void replaceFragmentInDetailScreen(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        final boolean tabletSize = activity.getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            transaction.replace(R.id.activity_main_frame_layout_detail_large_screen, fragment).commit();
        } else {
            transaction.replace(R.id.frame_layout_main, fragment).commit();
        }
    }
    public static void replaceFragmentInMainScreen(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        final boolean tabletSize = activity.getResources().getBoolean(R.bool.isTablet);
            transaction.replace(R.id.frame_layout_main, fragment).commit();
        }
}