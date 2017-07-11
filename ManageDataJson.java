package com.luizzabuscka.fiap;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by luizzabuscka on 11/07/17.
 */

public class ManageDataJson {

  private static final int REQUEST_EXTERNAL_STORAGE = 1;
  private static String[] PERMISSIONS_STORAGE = {
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
  };

  private static String filePath;
  private static Activity activity;

  public static void initialize(String filePath, Activity activity) {
    ManageDataJson.filePath = filePath;
    ManageDataJson.activity = activity;
  }

  private static void verifyStoragePermissions() {
    int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    if (permission != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(
          activity,
          PERMISSIONS_STORAGE,
          REQUEST_EXTERNAL_STORAGE
      );
    }
  }

  public static void recordNewData(Object data) {
    Gson gson = new Gson();
    ArrayList<Object> objs = getData();
    if (objs == null) {
      objs = new ArrayList<>();
    }
    objs.add(data);
    recordFile(gson.toJson(objs));
  }

  public static ArrayList<Object> getData() {
    Gson gson = new Gson();
    ArrayList<Object> objs = null;
    String content = readFile();
    try {
      objs = gson.fromJson(content, new TypeToken<ArrayList<Object>>(){}.getType());
    } catch ( Exception e){
      e.printStackTrace();
    }
    return objs;
  }

  private static void recordFile(String content) {
    verifyStoragePermissions();
    try
    {
      File file = new File(filePath);
      if (!file.exists()) {
        file.createNewFile();
      }
      FileWriter writer = new FileWriter(file,false);
      writer.write(content);
      writer.flush();
      writer.close();
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  }

  private static String readFile() {
    File file = new File(filePath);
    StringBuilder text = new StringBuilder();
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String line;

      while ((line = br.readLine()) != null) {
        text.append(line);
      }
      br.close();
    }
    catch (IOException e) {
      return "";
    }
    return text.toString();
  }

}
