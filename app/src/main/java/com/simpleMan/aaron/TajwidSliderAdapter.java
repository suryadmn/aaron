package com.simpleMan.aaron;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TajwidSliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private OnTajwidClickListener mTajwidListener;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private ContentViewModel contentViewModel;
    private ArrayList<Content> contents = null;

    //audio
    private ImageView imgAudio;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private int currentTime;
    private ArrayList<String> audio_files = new ArrayList<>();

    private int a, b;
    private int[] id;
    private String[] contentArab;
    private String[] contentBahasa;
    String selected_audio;
    String audioPath;

    public TajwidSliderAdapter(Context context) {
        this.context = context;
    }

    //Array images
    public int[] slider_Images = {
            R.drawable.tajwid1,
            R.drawable.tajwid2,
            R.drawable.tajwid3,
            R.drawable.tajwid4,
            R.drawable.tajwid5
    };

    @Override
    public int getCount() {
        return slider_Images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    public interface OnTajwidClickListener {
        void onTajwid(int position);
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_tajwid_layout, container, false);

        final ImageView slideImageView = view.findViewById(R.id.slideTajwidImages);
        slideImageView.setImageResource(slider_Images[position]);
        slideImageView.setRotationY(180);
        container.addView(view);

        //Download All Json String audio in the background
        AudioJson();

        //Get coordinate
        slideImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                a = (int) event.getX();
                b = (int) event.getY();
                Log.d("X,Y",a+","+b);
                return false;
            }
        });

        //Screen size in pixels
        Display display = view.getDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        int height = size.y;
        Log.d("Info ","width in pixels : "+width);
        Log.d("Info ","height in pixels : "+height);

        //Determine screen size
        float metrics = view.getResources().getDisplayMetrics().density;
        if (metrics >= 4.0){
            Toast.makeText(context, "xxxhdpi", Toast.LENGTH_SHORT).show();
        }else if (metrics >= 3.0){
            slideImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onLongClick(View v) {
                    if (position == 0) {
                        alFatihahXxhdpi();
                    } else if (position == 1) {
                        Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                    } else if (position == 2) {
                        Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                    } else if (position == 3) {
                        Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                    } else if (position == 4) {
                        Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                    }

                    return false;
                }
            });

            Toast.makeText(context, "xxhdpi", Toast.LENGTH_SHORT).show();
        }else if (metrics >= 2.0){

            //Set The image size for responsive mapping
            if (width >= 1080){
                //Set image size
                slideImageView.getLayoutParams().height = 1677;
                slideImageView.getLayoutParams().width = 1080;
                slideImageView.requestLayout();
            }else if (width >= 720){
                //Set image size
                slideImageView.getLayoutParams().height = 1120;
                slideImageView.getLayoutParams().width = 720;
                slideImageView.requestLayout();
            }

            //When user OnLongPress on mapping
            //then do...
            slideImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onLongClick(View v) {
                    //when resolution 1080+
                    if (width >= 1080){
                        if (position == 0) {
                            alFatihahXhdpiVer2();
                        } else if (position == 1) {
                            alBaqarahPage2XhdpiVer2();
                        } else if (position == 2) {
                            alBaqarahPage3XhdpiVer2();
                        } else if (position == 3) {
                            alBaqarahPage4XhdpiVer2();
                        } else if (position == 4) {
                            alBaqarahPage5XhdpiVer2();
                        }
                        //when resolution 720+
                    }else if ( width >= 720){
                        if (position == 0) {
                            alFatihahXhdpi();
                        } else if (position == 1) {
                            alBaqarahPage2Xhdpi();
                        } else if (position == 2) {
                            alBaqarahPage3Xhdpi();
                        } else if (position == 3) {
                            alBaqarahPage4Xhdpi();
                        } else if (position == 4) {
                            alBaqarahPage5Xhdpi();
                        }
                    }
                    return false;
                }
            });
            Toast.makeText(context, "xhdpi", Toast.LENGTH_SHORT).show();
        }else
        if (metrics >= 1.5){
            Toast.makeText(context, "hdpi", Toast.LENGTH_SHORT).show();
        }else
        if (metrics >= 1.0){
            Toast.makeText(context, "mdpi", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "ldpi", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    /**
     * Function handle popup
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showAlertDialog(int layout, final int condition) {
        dialogBuilder = new AlertDialog.Builder(context);
        final View layoutView = layoutInflater.inflate(layout, null);
        dialogBuilder.setView(layoutView);
        dialogBuilder.create();
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //set progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Memuat...");

        //Show progress dialog
        progressDialog.show();

        //get data from mysql with Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Model>> call = api.getModel();

        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                progressDialog.dismiss();

                try {

                    //Model API retorift2
                    List<Model> models = response.body();
                    id = new int[models.size()];
                    contentArab = new String[models.size()];
                    contentBahasa = new String[models.size()];

                    for (int i = 0; i < models.size(); i++) {
                        id[i] = models.get(i).getId();
                        contentArab[i] = models.get(i).getArabtajwid();
                        contentBahasa[i] = models.get(i).getPenjelasantajwid();
                    }

                    /**Add all data to Room*/
                    //Content content = new Content("", "");

                    //First, check Room database is not empty ...
                    if (contentViewModel.getAllContent().getValue() != null || contentViewModel.getAllContent().getValue().isEmpty()){
                        //Create dummy data to database
                        for (int i = 0; i < 255; i++){
                            Content content = new Content("", "");

                            //If duplicate content, remove
                            if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                                //then insert
                                contentViewModel.insert(content);
                        }

                        if (condition == 1){
                            for (int i = 0; i < 4; i++){

                                Content content = new Content(contentArab[i], contentBahasa[i]);
                                content.setId(i+1);
                                //If duplicate content, remove
                                if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                                    //then insert
                                    contentViewModel.update(content);
                            }
                        }else if (condition == 2){
                            for (int i = 4; i < 7; i++){
                                Content content = new Content(contentArab[i], contentBahasa[i]);
                                content.setId(i+1);
                                //If duplicate content, remove
                                if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                                    //then insert
                                    contentViewModel.update(content);
                            }
                        }else if (condition == 3){
                            for (int i = 7; i < 11; i++){
                                Content content = new Content(contentArab[i], contentBahasa[i]);
                                content.setId(i+1);
                                //If duplicate content, remove
                                if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                                    //then insert
                                    contentViewModel.update(content);
                            }
                        }else if (condition == 4){
                            for (int i = 11; i < 13; i++){
                                Content content = new Content(contentArab[i], contentBahasa[i]);
                                content.setId(i+1);
                                //If duplicate content, remove
                                if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                                    //then insert
                                    contentViewModel.update(content);
                            }
                        }else if (condition == 5){
                            for (int i = 13; i < 14; i++){
                                Content content = new Content(contentArab[i], contentBahasa[i]);
                                content.setId(i+1);
                                //If duplicate content, remove
                                if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                                    //then insert
                                    contentViewModel.update(content);
                            }
                        }else if (condition == 6){
                            for (int i = 14; i < 17; i++){
                                Content content = new Content(contentArab[i], contentBahasa[i]);
                                content.setId(i+1);
                                //If duplicate content, remove
                                if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                                    //then insert
                                    contentViewModel.update(content);
                            }
                        }else if (condition == 7){
                            for (int i = 17; i < 21; i++){
                                Content content = new Content(contentArab[i], contentBahasa[i]);
                                content.setId(i+1);
                                //If duplicate content, remove
                                if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                                    //then insert
                                    contentViewModel.update(content);
                            }
                        }else if (condition == 8){
                            for (int i = 0; i < 4; i++){
                                Content content = new Content(contentArab[i], contentBahasa[i]);
                                content.setId(i+1);
                                //If duplicate content, remove
                                if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                                    //then insert
                                    contentViewModel.update(content);
                            }
                        }else if (condition == 9){
                            for (int i = 21; i < 22; i++){
                                Content content = new Content(contentArab[i], contentBahasa[i]);
                                content.setId(i+1);
                                //If duplicate content, remove
                                if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                                    //then insert
                                    contentViewModel.update(content);
                            }
                        }else if (condition == 10){
                            for (int i = 22; i < 25; i++){
                                Content content = new Content(contentArab[i], contentBahasa[i]);
                                content.setId(i+1);
                                //If duplicate content, remove
                                if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                                    //then insert
                                    contentViewModel.update(content);
                            }
                        }else if (condition == 11){
                            for (int i = 25; i < 29; i++){
                                Content content = new Content(contentArab[i], contentBahasa[i]);
                                content.setId(i+1);
                                //If duplicate content, remove
                                if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                                    //then insert
                                    contentViewModel.update(content);
                            }
                        }
                    }

                    //Info
                    Log.d("", "Info id : " + Arrays.toString(id));
                    Log.d("", "Info arab : " + Arrays.toString(contentArab));
                    Log.d("", "Info Bahasa : " + Arrays.toString(contentBahasa));

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(context, "Kesalahan, Silahkan coba lagi", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                //Dismiss loading bar
                progressDialog.dismiss();

                Toast.makeText(context, "Kesalahan pada jaringan", Toast.LENGTH_SHORT).show();
            }
        });

        //Recycler view
        RecyclerView recyclerView = layoutView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);

        final ContentAdapter contentAdapter = new ContentAdapter();
        recyclerView.setAdapter(contentAdapter);

        contentViewModel = ViewModelProviders.of((FragmentActivity) context).get(ContentViewModel.class);
        contentViewModel.getAllContent().observe((LifecycleOwner) context, new Observer<List<Content>>() {
            @Override
            public void onChanged(List<Content> contents) {
                //show content as condition
                //First check List contents empty or not
                if (!contents.isEmpty()){
                    //if not empty check the index List
                    try {
                        if (condition == 1){
                            contentAdapter.setContents(contents.subList(0, 4));
                        }else if (condition == 2){
                            contentAdapter.setContents(contents.subList(4, 7));
                        }else if (condition == 3){
                            contentAdapter.setContents(contents.subList(7, 11));
                        }else if (condition == 4){
                            contentAdapter.setContents(contents.subList(11, 13));
                        }else if (condition == 5){
                            contentAdapter.setContents(contents.subList(13, 14));
                        }else if (condition == 6){
                            contentAdapter.setContents(contents.subList(14, 17));
                        }else if (condition == 7){
                            contentAdapter.setContents(contents.subList(17, 21));
                        }else if (condition == 8){
                            contentAdapter.setContents(contents.subList(0, 4));
                        }else if (condition == 9){
                            contentAdapter.setContents(contents.subList(21, 22));
                        }else if (condition == 10){
                            contentAdapter.setContents(contents.subList(22, 25));
                        }else if (condition == 11){
                            contentAdapter.setContents(contents.subList(25, 29));
                        }
                    }catch (IndexOutOfBoundsException e){
                       e.printStackTrace();
                    }
                }else{
                    Toast.makeText(context, "Hidupkan data untuk download konten tajwid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Audio initialize
        try{
            //--Set String Path--
            if (condition == 1){
                audioPath = Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(0);
            }else if (condition == 2) {
                audioPath = Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(1);
            }else if (condition == 3) {
                audioPath = Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(2);
            }else if (condition == 4) {
                audioPath = Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(3);
            }else if (condition == 5) {
                audioPath = Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(4);
            }else if (condition == 6) {
                audioPath = Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(5);
            }else if (condition == 7) {
                audioPath = Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(6);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //--Set DataResource to MediaPlayer
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepare();
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(context, "Kesalahan, Audio tidak dapat ditemukan", Toast.LENGTH_SHORT).show();
        }

        imgAudio = layoutView.findViewById(R.id.imgAudio);
        seekBar = layoutView.findViewById(R.id.seekBarAudio);
        currentTime = mediaPlayer.getDuration();
        seekBar.setMax(currentTime);

        //Seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null){
                    try {
                        Message msg = new Message();
                        msg.what = mediaPlayer.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //When play button clicked
        imgAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Download all String json
                AudioJson();

                //Check, is there data in method getArrayList
                //If exist, do this
                try{
                    if (condition == 1){
                        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(0));
                        //if file exists on storage
                        if (!file.exists()){
                            //Cal method AudioJsonArray
                            DownloadAudio(condition);
                        }else {
                            Log.i("File audio","Exists");
                        }
                    }else if (condition == 2){
                        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(1));
                        //if file exists on storage
                        if (!file.exists()){
                            //Cal method AudioJsonArray
                            DownloadAudio(condition);
                        }else {
                            Log.i("File audio","Exists");
                        }
                    }else if (condition == 3){
                        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(2));
                        //if file exists on storage
                        if (!file.exists()){
                            //Cal method AudioJsonArray
                            DownloadAudio(condition);
                        }else {
                            Log.i("File audio","Exists");
                        }
                    }else if (condition == 4){
                        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(3));
                        //if file exists on storage
                        if (!file.exists()){
                            //Cal method AudioJsonArray
                            DownloadAudio(condition);
                        }else {
                            Log.i("File audio","Exists");
                        }
                    }else if (condition == 5){
                        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(4));
                        //if file exists on storage
                        if (!file.exists()){
                            //Cal method AudioJsonArray
                            DownloadAudio(condition);
                        }else {
                            Log.i("File audio","Exists");
                        }
                    }else if (condition == 6){
                        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(5));
                        //if file exists on storage
                        if (!file.exists()){
                            //Cal method AudioJsonArray
                            DownloadAudio(condition);
                        }else {
                            Log.i("File audio","Exists");
                        }
                    }else if (condition == 7){
                        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Aaron/Data/Audio/"+getArrayList("Json audio file").get(6));
                        //if file exists on storage
                        if (!file.exists()){
                            //Cal method AudioJsonArray
                            DownloadAudio(condition);
                        }else {
                            Log.i("File audio","Exists");
                        }
                    }
                    //then if not exits, do this
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(context, "Mohon hidupkan koneksi internet untuk melanjutkan", Toast.LENGTH_SHORT).show();
                }

                //Do the code for audio
                imgAudio.setImageResource(R.drawable.ic_pause_circle_filled);

                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgAudio.setImageResource(R.drawable.ic_play_circle_filled);
                }else{
                    mediaPlayer.start();
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                imgAudio.setImageResource(R.drawable.ic_play_circle_filled);
            }
        });
        //End For audio code---

    }

    /**
     * Handler for audio Seekbar
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            int currentPosition = msg.what;
            //update seekbar
            seekBar.setProgress(currentPosition);
        }
    };

    /**
     * Get API audio Json from online Database
     */
    public void AudioJson(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url("https://aaron-qurantajwid.000webhostapp.com/loadAudio").build();

                okhttp3.Response responses = null;
                try {
                    responses = okHttpClient.newCall(request).execute();
                    JSONArray array = new JSONArray(responses.body().string());

                    for (int i = 0; i < array.length(); i++){

                        String file_name = array.getString(i);
                        //JSONObject obj = array.getJSONObject(i);
                        //String file_name = obj.getString("file_name");

                        //If file name already exist, then remove item
                        if (audio_files.indexOf(file_name) == -1)
                            //if not exist, then insert item
                            audio_files.add(file_name);

                        //Save file name json to shared preferences
                        try {
                            saveArrayList(audio_files, "Json audio file");
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        Log.d("info for ", "Json audio file : "+ getArrayList("Json audio file") );
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("Info","Audio Json ArrayList : "+ audio_files);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        thread.start();
    }

    /**
     *Download audio from databases
     */
    public void DownloadAudio(int condition){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Mengunduh audio...");
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        if (condition == 1){
            selected_audio = audio_files.get(0);
        }else if (condition == 2){
            selected_audio = audio_files.get(1);
        }else if (condition == 3){
            selected_audio = audio_files.get(2);
        }else if (condition == 4){
            selected_audio = audio_files.get(3);
        }else if (condition == 5){
            selected_audio = audio_files.get(4);
        }else if (condition == 6){
            selected_audio = audio_files.get(5);
        }else if (condition == 7){
            selected_audio = audio_files.get(6);
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client  = new OkHttpClient();
                Request request = new Request.Builder().url("https://aaron-qurantajwid.000webhostapp.com/assets/audio/"+selected_audio).build();

                okhttp3.Response response = null;
                try{
                    response = client.newCall(request).execute();
                    float file_size = response.body().contentLength();

                    BufferedInputStream inputStream = new BufferedInputStream(response.body().byteStream());
                    OutputStream stream = new FileOutputStream(Environment.getExternalStorageDirectory()+"/Aaron/Data/Audio/"+selected_audio);

                    byte[] data = new byte[8192];
                    float total = 0;
                    int read_bytes = 0;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.show();
                        }
                    });

                    while ((read_bytes = inputStream.read(data)) != -1){
                        total = total+read_bytes;
                        stream.write(data, 0, read_bytes);
                        progressDialog.setProgress((int) ((total/file_size)*100));
                    }

                    progressDialog.dismiss();

                    stream.flush();
                    stream.close();
                    response.body().close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.start();

    }

    /**
     * Save arrayList into Shared Preferences
     */
    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    /**
     * Load arrayList from Shared Preferences
     */
    public ArrayList<String> getArrayList(String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        return gson.fromJson(json, type);
    }

    /**
     * Setup coordinate
     *
     *Density 2.0 (XHDPI)*/
        //For 720p+
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void alFatihahXhdpi() {
        //Get coordinate X and Y
        int X = a, Y = b;

        //If user click at coordinate
        if (X <= 551 && Y <= 275) {
            //Do nothing
        } else if (X <= 200 && Y <= 350) {
            //Do nothing
        } else if (X <= 551 && Y <= 350) {
            showAlertDialog(R.layout.popup_layout, 1);
        } else if (X <= 551 && Y <= 380) {
            //Do nothing
        } else if (X <= 200 && Y <= 450) {
            //Do nothing
        } else if (X <= 270 && Y <= 450) {
            showAlertDialog(R.layout.popup_layout, 3);
        } else if (X <= 580 && Y <= 450) {
            showAlertDialog(R.layout.popup_layout, 2);
        } else if (X <= 430 && Y <= 460) {
            //Do nothing
        } else if (X <= 200 && Y <= 530) {
            //Do nothing
        } else if (X <= 429 && Y <= 530) {
            showAlertDialog(R.layout.popup_layout, 4);
        } else if (X <= 580 && Y <= 460) {
            //Do nothing
        } else if (X <= 445 && Y <= 530) {
            //Do nothing
        } else if (X <= 580 && Y <= 530) {
            showAlertDialog(R.layout.popup_layout, 3);
        } else if (X <= 200 && Y <= 560) {
            //Do nothing
        } else if (X <= 200 && Y <= 620) {
            //Do nothing
        } else if (X <= 580 && Y <= 620) {
            showAlertDialog(R.layout.popup_layout, 5);
        } else if (X <= 200 && Y <= 630) {
            //Do nothing
        } else if (X <= 200 && Y <= 710) {
            //Do nothing
        } else if (X <= 300 && Y <= 710) {
            showAlertDialog(R.layout.popup_layout, 7);
        } else if (X <= 580 && Y <= 710) {
            showAlertDialog(R.layout.popup_layout, 6);
        } else if (X <= 200 && Y <= 720) {
            //Do nothing
        } else if (X <= 200 && Y <= 780) {
            //Do nothing
        } else if (X <= 580 && Y <= 780) {
            showAlertDialog(R.layout.popup_layout, 7);
        } else if (X <= 275 && Y <= 790) {
            //Do nothing
        } else if (X <= 275 && Y <= 875) {
            //Do nothing
        } else if (X <= 500 && Y <= 875) {
            showAlertDialog(R.layout.popup_layout, 7);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void alBaqarahPage2Xhdpi(){
        int X = a, Y = b;

        //If user click at coordinate
        if (X <= 170 && Y <= 1105){
            //Do nothing
        }else if (X <= 700 && Y <= 280){
            //Do nothing
        }else if (X <= 575 && Y <= 355 ){
            showAlertDialog(R.layout.popup_layout, 8);
        }else if (X <= 480 && Y <= 450){
            showAlertDialog(R.layout.popup_layout, 10);
        }else if (X <= 575 && Y <= 450){
            showAlertDialog(R.layout.popup_layout, 9);
        }else if (X <= 455 && Y <= 520){
            showAlertDialog(R.layout.popup_layout, 11);
        }else if (X <= 575 && Y <= 520){
            showAlertDialog(R.layout.popup_layout, 10);
        }else if (X <= 575 && Y <= 600){
            showAlertDialog(R.layout.popup_layout, 11);//--
        }else if (X <= 575 && Y <= 700){
            showAlertDialog(R.layout.popup_layout, 4);
        }else if (X <= 290 && Y <= 775){
            showAlertDialog(R.layout.popup_layout, 5);
        }else if (X <= 575 && Y <= 775){
            showAlertDialog(R.layout.popup_layout, 4);
        }else if (X <= 575 && Y <= 870){
            showAlertDialog(R.layout.popup_layout, 5);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void alBaqarahPage3Xhdpi(){
        int X = a, Y = b;

        if (X <= 700 && Y <= 35){
            //Do nothing
        }else if (X <= 45 && Y <= 1090){
            //Do nothing
        }else if (X <= 690 && Y <= 105){
            showAlertDialog(R.layout.popup_layout, 6);
        }else if (X <= 520 && Y <= 180){
            showAlertDialog(R.layout.popup_layout, 7);
        }else if (X <= 690 && Y <= 180){
            showAlertDialog(R.layout.popup_layout, 6);
        }else if (X <= 200 && Y <= 240){
            showAlertDialog(R.layout.popup_layout, 8);
        }else if (X <= 690 && Y <= 240){
            showAlertDialog(R.layout.popup_layout, 7);
        }else if (X <= 690 && Y <= 318){
            showAlertDialog(R.layout.popup_layout, 8);
        }else if (X <= 690 && Y <= 375){
            showAlertDialog(R.layout.popup_layout, 9);
        }else if (X <= 500 && Y <= 455){
            showAlertDialog(R.layout.popup_layout, 10);
        }else if (X <= 690 && Y <= 455){
            showAlertDialog(R.layout.popup_layout, 9);
        }else if (X <= 225 && Y <= 535){
            showAlertDialog(R.layout.popup_layout, 11);
        }else if (X <= 690 && Y <= 535){
            showAlertDialog(R.layout.popup_layout, 10);
        }else if (X <= 690 && Y <= 580){
            showAlertDialog(R.layout.popup_layout, 11);
        }else if (X <= 175 && Y <= 660){
            showAlertDialog(R.layout.popup_layout, 13);
        }else if (X <= 690 && Y <= 660){
            showAlertDialog(R.layout.popup_layout, 12);
        }else if (X <= 690 && Y <= 710){
            showAlertDialog(R.layout.popup_layout, 13);
        }else if (X <= 200 && Y <= 790){
            showAlertDialog(R.layout.popup_layout, 14);
        }else if (X <= 690 && Y <= 790){
            showAlertDialog(R.layout.popup_layout, 13);
        }else if (X <= 690 &&  Y <= 850){
            showAlertDialog(R.layout.popup_layout, 14);
        }else if (X <= 335 && Y <= 920){
            showAlertDialog(R.layout.popup_layout, 15);
        }else if (X <= 690 && Y <= 920){
            showAlertDialog(R.layout.popup_layout, 14);
        }else if (X <= 410 && Y <= 980){
            showAlertDialog(R.layout.popup_layout, 16);
        }else if (X <= 690 && Y <= 980){
            showAlertDialog(R.layout.popup_layout, 15);
        }else if (X <= 690 && Y <= 1070){
            showAlertDialog(R.layout.popup_layout, 16);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void alBaqarahPage4Xhdpi(){
        int X = a, Y = b;

        if(X <= 40 && Y <= 1060){
            //Do nothing
        }else if (X <= 690 && Y <= 30){
            //Do nothing
        }else if (X <= 690 && Y <= 110){
            showAlertDialog(R.layout.popup_layout, 17);
        }else if (X <= 110 && Y <= 180){
            showAlertDialog(R.layout.popup_layout, 18);
        }else if (X <= 690 && Y <= 190){
            showAlertDialog(R.layout.popup_layout, 17);
        }else if (X <= 350 && Y <= 240){
            showAlertDialog(R.layout.popup_layout, 19);
        }else if (X <= 690 && Y <= 240){
            showAlertDialog(R.layout.popup_layout, 18);
        }else if (X <= 690 && Y <= 300){
            showAlertDialog(R.layout.popup_layout, 19);
        }else if (X <= 250 && Y <= 370){
            showAlertDialog(R.layout.popup_layout, 20);
        }else if (X <= 690 && Y <= 375){
            showAlertDialog(R.layout.popup_layout, 19);
        }else if (X <= 690 && Y <= 450){
            showAlertDialog(R.layout.popup_layout, 20);
        }else if (X <= 690 && Y <= 525){
            showAlertDialog(R.layout.popup_layout, 20);
        }else if (X <= 510 && Y <= 575){
            showAlertDialog(R.layout.popup_layout, 21);
        }else if (X <= 690 && Y <= 575){
            showAlertDialog(R.layout.popup_layout, 20);
        }else if (X <= 250 && Y <= 640){
            showAlertDialog(R.layout.popup_layout, 22);
        }else if (X <= 690 && Y <= 640){
            showAlertDialog(R.layout.popup_layout, 21);
        }else if (X <= 690 && Y <= 720){
            showAlertDialog(R.layout.popup_layout, 22);
        }else if (X <= 690 && Y <= 780){
            showAlertDialog(R.layout.popup_layout, 22);
        }else if (X <= 500 && Y <= 860){
            showAlertDialog(R.layout.popup_layout, 23);
        }else if (X <= 690 && Y <= 860){
            showAlertDialog(R.layout.popup_layout, 22);
        }else if (X <= 690 && Y <= 920){
            showAlertDialog(R.layout.popup_layout, 23);
        }else if (X <= 440 && Y <= 980){
            showAlertDialog(R.layout.popup_layout, 24);
        }else if (X <= 690 && Y <= 980){
            showAlertDialog(R.layout.popup_layout, 23);
        }else if (X <= 690 && Y <= 1055){
            showAlertDialog(R.layout.popup_layout, 24);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void alBaqarahPage5Xhdpi(){
        int X = a, Y = b;

        if (X <= 40 && Y <= 1060){
            //Do nothing
        }else if (X <= 690 && Y <= 30){
            //Do nothing
        }else if (X <= 690 && Y <= 110){
            showAlertDialog(R.layout.popup_layout, 25);
        }else if (X <= 690 && Y <= 170){
            showAlertDialog(R.layout.popup_layout, 25);
        }else if (X <= 690 && Y <= 245){
            showAlertDialog(R.layout.popup_layout, 25);
        }else if (X <= 690 && Y <= 315){
            showAlertDialog(R.layout.popup_layout, 25);
        }else if (X <= 690 && Y <= 380){
            showAlertDialog(R.layout.popup_layout, 26);
        }else if (X <= 690 && Y <= 450){
            showAlertDialog(R.layout.popup_layout, 26);
        }else if (X <= 690 && Y <= 530){
            showAlertDialog(R.layout.popup_layout, 26);
        }else if (X <= 690 && Y <= 580){
            showAlertDialog(R.layout.popup_layout, 26);
        }else if (X <= 300 && Y <= 645){
            showAlertDialog(R.layout.popup_layout, 27);
        }else if (X <= 690 && Y <= 645){
            showAlertDialog(R.layout.popup_layout, 26);
        }else if (X <= 690 && Y <= 710){
            showAlertDialog(R.layout.popup_layout, 27);
        }else if (X <= 690 && Y <= 780){
            showAlertDialog(R.layout.popup_layout, 27);
        }else if (X <= 690 && Y <= 855){
            showAlertDialog(R.layout.popup_layout, 28);
        }else if (X <= 125 && Y <= 925){
            showAlertDialog(R.layout.popup_layout, 29);
        }else if (X <= 690 && Y <= 920){
            showAlertDialog(R.layout.popup_layout, 28);
        }else if (X <= 690 && Y <= 990){
            showAlertDialog(R.layout.popup_layout, 29);
        }else if (X <= 690 && Y <= 1065){
            showAlertDialog(R.layout.popup_layout, 29);
        }
    }

        //For 1080p+
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void alFatihahXhdpiVer2(){
        //Get coordinate X and Y
        int X = a, Y = b;

        //If user click at coordinate
        if (X <= 551 && Y <= 275) {
            //Do nothing
        } else if (X <= 1060 && Y <= 380) {
            //Do nothing
        } else if (X <= 280 && Y <= 1275) {
            //Do nothing
        } else if (X <= 790 && Y <= 540) {
            showAlertDialog(R.layout.popup_layout, 1);
        } else if (X <= 380 && Y <= 660) {
            showAlertDialog(R.layout.popup_layout, 3);
        } else if (X <= 830 && Y <= 660) {
            showAlertDialog(R.layout.popup_layout, 2);
        } else if (X <= 620 && Y <= 790) {
            showAlertDialog(R.layout.popup_layout, 4);
        } else if (X <= 830 && Y <= 790) {
            showAlertDialog(R.layout.popup_layout, 3);
        } else if (X <= 830 && Y <= 910) {
            showAlertDialog(R.layout.popup_layout, 5);
        } else if (X <= 400 && Y <= 1025) {
            showAlertDialog(R.layout.popup_layout, 7);
        } else if (X <= 830 && Y <= 1025) {
            showAlertDialog(R.layout.popup_layout, 6);
        } else if (X <= 830 && Y <= 1160) {
            showAlertDialog(R.layout.popup_layout, 7);
        } else if (X <= 370 && Y <= 1275) {
            //Do nothing
        } else if (X <= 725 && Y <= 1275) {
            showAlertDialog(R.layout.popup_layout, 7);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void alFatihahXxhdpi() {
        int X = a, Y = b;

        //If user click at coordinate
        if (X <= 551 && Y <= 275) {
            //Do nothing
        } else if (X <= 1060 && Y <= 380) {
            //Do nothing
        } else if (X <= 280 && Y <= 1275) {
            //Do nothing
        } else if (X <= 790 && Y <= 540) {
            showAlertDialog(R.layout.popup_layout, 1);
        } else if (X <= 380 && Y <= 660) {
            showAlertDialog(R.layout.popup_layout, 3);
        } else if (X <= 830 && Y <= 660) {
            showAlertDialog(R.layout.popup_layout, 2);
        } else if (X <= 620 && Y <= 790) {
            showAlertDialog(R.layout.popup_layout, 4);
        } else if (X <= 830 && Y <= 790) {
            showAlertDialog(R.layout.popup_layout, 3);
        } else if (X <= 830 && Y <= 910) {
            showAlertDialog(R.layout.popup_layout, 5);
        } else if (X <= 400 && Y <= 1025) {
            showAlertDialog(R.layout.popup_layout, 7);
        } else if (X <= 830 && Y <= 1025) {
            showAlertDialog(R.layout.popup_layout, 6);
        } else if (X <= 830 && Y <= 1160) {
            showAlertDialog(R.layout.popup_layout, 7);
        } else if (X <= 370 && Y <= 1275) {
            //Do nothing
        } else if (X <= 725 && Y <= 1275) {
            showAlertDialog(R.layout.popup_layout, 7);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void alBaqarahPage2XhdpiVer2(){
        int X = a, Y = b;

        if (X <= 1075 && Y <= 400){
            //Do nothing
        }else if (X <= 235 && Y <= 1670){
            //Do nothing
        }else if (X <= 1015 && Y <= 540){
            showAlertDialog(R.layout.popup_layout, 0);
        } else if (X <= 695 && Y <= 665){
            showAlertDialog(R.layout.popup_layout, 2);
        }else if (X <= 827 && Y <= 675){
            showAlertDialog(R.layout.popup_layout, 1);
        }else if (X <= 650 && Y <= 790){
            showAlertDialog(R.layout.popup_layout, 3);
        }else if (X <= 827 && Y <= 790){
            showAlertDialog(R.layout.popup_layout, 2);
        }else if (X <= 827 && Y <= 920){
            showAlertDialog(R.layout.popup_layout, 3);
        }else if (X <= 827 && Y <= 1050){
            showAlertDialog(R.layout.popup_layout, 4);
        }else if (X <= 400 && Y <= 1170){
            showAlertDialog(R.layout.popup_layout, 5);
        }else if (X <= 827 && Y <= 1170){
            showAlertDialog(R.layout.popup_layout, 4);
        }else if (X <= 827 && Y <= 1300){
            showAlertDialog(R.layout.popup_layout, 5);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void alBaqarahPage3XhdpiVer2(){
        int X = a, Y = b;

        if(X <= 1070 && Y <= 60){
            //Do nothing
        }else if (X <= 57 && Y <= 1670){
            //Do nothing
        }else if (X <= 1015 && Y <= 165){
            showAlertDialog(R.layout.popup_layout, 6);
        }else if (X <= 740 && Y <= 270){
            showAlertDialog(R.layout.popup_layout, 7);
        }else if (X <= 1015 && Y <= 270){
            showAlertDialog(R.layout.popup_layout, 6);
        }else if (X <= 270 && Y <= 370){
            showAlertDialog(R.layout.popup_layout, 8);
        }else if (X <= 1015 && Y <= 370){
            showAlertDialog(R.layout.popup_layout, 7);
        }else if (X <= 1015 && Y <= 470){
            showAlertDialog(R.layout.popup_layout, 8);
        }else if (X <= 1015 && Y <= 570){
            showAlertDialog(R.layout.popup_layout, 9);
        }else if (X <= 705 && Y <= 675){
            showAlertDialog(R.layout.popup_layout, 10);
        }else if (X <= 1015 && Y <= 675){
            showAlertDialog(R.layout.popup_layout, 9);
        }else if (X <= 300 && Y <= 775){
            showAlertDialog(R.layout.popup_layout, 11);
        }else if (X <= 1015 && Y <= 775){
            showAlertDialog(R.layout.popup_layout, 10);
        }else if (X <= 1015 && Y <= 880){
            showAlertDialog(R.layout.popup_layout, 11);
        }else if (X <= 215 && Y <= 980){
            showAlertDialog(R.layout.popup_layout, 13);
        }else if (X <= 1015 && Y <= 980){
            showAlertDialog(R.layout.popup_layout, 12);
        }else if (X <= 1015 && Y <= 1070){
            showAlertDialog(R.layout.popup_layout, 13);
        }else if (X <= 255 && Y <= 1180){
            showAlertDialog(R.layout.popup_layout, 14);
        }else if (X <= 1015 && Y <= 1180){
            showAlertDialog(R.layout.popup_layout, 13);
        }else if (X <= 1015 && Y <= 1285){
            showAlertDialog(R.layout.popup_layout, 14);
        }else if (X <= 470 && Y <= 1385){
            showAlertDialog(R.layout.popup_layout, 15);
        }else if (X <= 1015 && Y <= 1385){
            showAlertDialog(R.layout.popup_layout, 14);
        }else if (X <= 580 && Y <= 1490){
            showAlertDialog(R.layout.popup_layout, 16);
        }else if (X <= 1015 && Y <= 1490){
            showAlertDialog(R.layout.popup_layout, 15);
        }else if (X <= 1015 && Y <= 1600){
            showAlertDialog(R.layout.popup_layout, 16);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void alBaqarahPage4XhdpiVer2(){
        int X = a, Y = b;

        if (X <= 1070  && Y <= 60){
            //Do nothing
        }else if (X <= 57 && Y <= 1670){
            //Do nothing
        }else if (X <= 1015 && Y <= 165){
            showAlertDialog(R.layout.popup_layout, 17);
        }else if (X <= 140 && Y <= 275){
            showAlertDialog(R.layout.popup_layout, 18);
        }else if (X <= 1015 && Y <= 275){
            showAlertDialog(R.layout.popup_layout, 17);
        }else if (X <= 495 && Y <= 370){
            showAlertDialog(R.layout.popup_layout, 19);
        }else if (X <= 1015 && Y <= 370){
            showAlertDialog(R.layout.popup_layout, 18);
        }else if (X <= 1015 && Y <= 475){
            showAlertDialog(R.layout.popup_layout, 19);
        }else if (X <= 350 && Y <= 575){
            showAlertDialog(R.layout.popup_layout, 20);
        }else if (X <= 1015 && Y <= 575){
            showAlertDialog(R.layout.popup_layout, 19);
        }else if (X <= 1015 && Y <= 670){
            showAlertDialog(R.layout.popup_layout, 20);
        }else if (X <= 1015 && Y <= 780){
            showAlertDialog(R.layout.popup_layout, 20);
        }else if (X <= 730 && Y <= 870){
            showAlertDialog(R.layout.popup_layout, 21);
        }else if (X <= 1015 && Y <= 870){
            showAlertDialog(R.layout.popup_layout, 20);
        }else if (X <= 350 && Y <= 980){
            showAlertDialog(R.layout.popup_layout, 22);
        }else if (X <= 1015 && Y <= 980){
            showAlertDialog(R.layout.popup_layout, 21);
        }else if (X <= 1015 && Y <= 1070){
            showAlertDialog(R.layout.popup_layout, 22);
        }else if (X <= 1015 && Y <= 1170){
            showAlertDialog(R.layout.popup_layout, 22);
        }else if (X <= 725 && Y <= 1290){
            showAlertDialog(R.layout.popup_layout, 23);
        }else if (X <= 1015 && Y <= 1290){
            showAlertDialog(R.layout.popup_layout, 22);
        }else if (X <= 1015 && Y <= 1385){
            showAlertDialog(R.layout.popup_layout, 23);
        }else if (X <= 620 && Y <= 1485){
            showAlertDialog(R.layout.popup_layout, 24);
        }else if (X <= 1015 && Y <= 1485){
            showAlertDialog(R.layout.popup_layout, 23);
        }else if (X <= 1015 && Y <= 1590){
            showAlertDialog(R.layout.popup_layout, 24);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void alBaqarahPage5XhdpiVer2(){
        int X = a, Y = b;

        if (X <= 1070 && Y <= 60){
            //Do nothing
        }else if (X <= 57 && Y <= 1670){
            //Do nothing
        }else if (X <= 1015 && Y <= 165){
            showAlertDialog(R.layout.popup_layout, 25);
        }else if (X <= 1015 && Y <= 275){
            showAlertDialog(R.layout.popup_layout, 25);
        }else if (X <= 1015 && Y <= 360){
            showAlertDialog(R.layout.popup_layout, 25);
        }else if (X <= 1015 && Y <= 470){
            showAlertDialog(R.layout.popup_layout, 25);
        }else if (X <= 1015 && Y <= 565){
            showAlertDialog(R.layout.popup_layout, 26);
        }else if (X <= 1015 && Y <= 670){
            showAlertDialog(R.layout.popup_layout, 26);
        }else if (X <= 1015 && Y <= 780){
            showAlertDialog(R.layout.popup_layout, 26);
        }else if (X <= 1015 && Y <= 875){
            showAlertDialog(R.layout.popup_layout, 26);
        }else if (X <= 420 && Y <= 970){
            showAlertDialog(R.layout.popup_layout, 27);
        }else if (X <= 1015 && Y <= 970){
            showAlertDialog(R.layout.popup_layout, 26);
        }else if (X <= 1015 && Y <= 1080){
            showAlertDialog(R.layout.popup_layout, 27);
        }else if (X <= 1015 && Y <= 1180){
            showAlertDialog(R.layout.popup_layout, 27);
        }else if (X <= 1015 && Y <= 1290){
            showAlertDialog(R.layout.popup_layout, 28);
        }else if (X <= 135 && Y <= 1380){
            showAlertDialog(R.layout.popup_layout, 29);
        }else if (X <= 1015 && Y <= 1380){
            showAlertDialog(R.layout.popup_layout, 28);
        }else if (X <= 1015 && Y <= 1480){
            showAlertDialog(R.layout.popup_layout, 29);
        }else if (X <= 1015 && Y <= 1600){
            showAlertDialog(R.layout.popup_layout, 29);
        }
    }
}
