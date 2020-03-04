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
        }else
        if (metrics >= 3.0){
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

            //When user OnLongPress on images
            //then do...
            slideImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onLongClick(View v) {

                    if (width >= 1080){
                        if (position == 0) {
                            alFatihahXhdpiVer2();
                        } else if (position == 1) {
                            Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                        } else if (position == 2) {
                            Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                        } else if (position == 3) {
                            Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                        } else if (position == 4) {
                            Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                        }
                    }else if ( width >= 720){
                        if (position == 0) {
                            alFatihahXhdpi();
                        } else if (position == 1) {
                            Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                        } else if (position == 2) {
                            Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                        } else if (position == 3) {
                            Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                        } else if (position == 4) {
                            Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
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
                //First, check Room database not empty ...
                if (contentViewModel.getAllContent().getValue() != null || contentViewModel.getAllContent().getValue().isEmpty()){
                    for (int i = 0; i < models.size(); i++){
                        Content content = new Content(contentArab[i], contentBahasa[i]);
                        //If duplicate content, then remove
                        if (contentViewModel.getAllContent().getValue().indexOf(content) == -1)
                        //else insert
                        contentViewModel.insert(content);
                    }
                }

                //Info
                Log.d("", "Info arab : " + Arrays.toString(contentArab));
                Log.d("", "Info Bahasa : " + Arrays.toString(contentBahasa));

            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                //Dismiss loading bar
                progressDialog.dismiss();

                Toast.makeText(context, "Mode Offline", Toast.LENGTH_SHORT).show();
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

                //Check, is  there  data on method getArrayList
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
                Request request = new Request.Builder().url("https://aaron-app.000webhostapp.com/loadAudio?list_audio").build();

                okhttp3.Response responses = null;
                try {
                    responses = okHttpClient.newCall(request).execute();
                    JSONArray array = new JSONArray(responses.body().string());

                    for (int i = 0; i < array.length(); i++){

                        String file_name = array.getString(i);

                        //If file name already exist, then remove item
                        if (audio_files.indexOf(file_name) == -1)
                            //if not exist, then insert item
                            audio_files.add(file_name);

                        //Save file name json to shared preferences
                        saveArrayList(audio_files, "Json audio file");
                        Log.d("info for ", "Json audio file : "+ getArrayList("Json audio file") );
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("Info","Audio Json ArrayList : "+ audio_files);
                        }
                    });

                } catch (IOException | JSONException e) {
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
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client  = new OkHttpClient();
                Request request = new Request.Builder().url("https://aaron-app.000webhostapp.com/assets/audio/"+selected_audio).build();

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
     */
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
            Toast.makeText(context, "Al-Fatihaa | Ayat : 3", Toast.LENGTH_SHORT).show();
        } else if (X <= 580 && Y <= 450) {
            showAlertDialog(R.layout.popup_layout, 2);
        } else if (X <= 430 && Y <= 460) {
            //Do nothing
        } else if (X <= 200 && Y <= 530) {
            //Do nothing
        } else if (X <= 429 && Y <= 530) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 4", Toast.LENGTH_SHORT).show();
        } else if (X <= 580 && Y <= 460) {
            //Do nothing
        } else if (X <= 445 && Y <= 530) {
            //Do nothing
        } else if (X <= 580 && Y <= 530) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 3", Toast.LENGTH_SHORT).show();

        } else if (X <= 200 && Y <= 560) {
            //Do nothing
        } else if (X <= 200 && Y <= 620) {
            //Do nothing
        } else if (X <= 580 && Y <= 620) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 5", Toast.LENGTH_SHORT).show();

        } else if (X <= 200 && Y <= 630) {
            //Do nothing
        } else if (X <= 200 && Y <= 710) {
            //Do nothing
        } else if (X <= 300 && Y <= 710) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 7", Toast.LENGTH_SHORT).show();
        } else if (X <= 580 && Y <= 710) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 6", Toast.LENGTH_SHORT).show();

        } else if (X <= 200 && Y <= 720) {
            //Do nothing
        } else if (X <= 200 && Y <= 780) {
            //Do nothing
        } else if (X <= 580 && Y <= 780) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 7", Toast.LENGTH_SHORT).show();

        } else if (X <= 275 && Y <= 790) {
            //Do nothing
        } else if (X <= 275 && Y <= 875) {
            //Do nothing
        } else if (X <= 500 && Y <= 875) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 7", Toast.LENGTH_SHORT).show();
        }
    }

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
            Toast.makeText(context, "Al-Fatihaa | Ayat : 3", Toast.LENGTH_SHORT).show();
        } else if (X <= 830 && Y <= 660) {
            showAlertDialog(R.layout.popup_layout, 2);
        } else if (X <= 620 && Y <= 790) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 4", Toast.LENGTH_SHORT).show();
        } else if (X <= 830 && Y <= 790) {
            Toast.makeText(context, "Al-Fatihah | Ayat : 3", Toast.LENGTH_SHORT).show();
        } else if (X <= 830 && Y <= 910) {
            Toast.makeText(context, "Al-Fatihah | Ayat : 5", Toast.LENGTH_SHORT).show();
        } else if (X <= 400 && Y <= 1025) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 7", Toast.LENGTH_SHORT).show();
        } else if (X <= 830 && Y <= 1025) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 6", Toast.LENGTH_SHORT).show();
        } else if (X <= 830 && Y <= 1160) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 7", Toast.LENGTH_SHORT).show();
        } else if (X <= 370 && Y <= 1275) {
            //Do nothing
        } else if (X <= 725 && Y <= 1275) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 7", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, "Al-Fatihaa | Ayat : 3", Toast.LENGTH_SHORT).show();
        } else if (X <= 830 && Y <= 660) {
            showAlertDialog(R.layout.popup_layout, 2);
        } else if (X <= 620 && Y <= 790) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 4", Toast.LENGTH_SHORT).show();
        } else if (X <= 830 && Y <= 790) {
            Toast.makeText(context, "Al-Fatihah | Ayat : 3", Toast.LENGTH_SHORT).show();
        } else if (X <= 830 && Y <= 910) {
            Toast.makeText(context, "Al-Fatihah | Ayat : 5", Toast.LENGTH_SHORT).show();
        } else if (X <= 400 && Y <= 1025) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 7", Toast.LENGTH_SHORT).show();
        } else if (X <= 830 && Y <= 1025) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 6", Toast.LENGTH_SHORT).show();
        } else if (X <= 830 && Y <= 1160) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 7", Toast.LENGTH_SHORT).show();
        } else if (X <= 370 && Y <= 1275) {
            //Do nothing
        } else if (X <= 725 && Y <= 1275) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 7", Toast.LENGTH_SHORT).show();
        }
    }
}
