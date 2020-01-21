package com.simpleMan.aaron;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;


import java.util.Arrays;
import java.util.List;

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

    //audio
    ImageView imgAudio;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private int currentTime;

    private int a, b;
    private int[] id;
    private String[] contentArab;
    private String[] contentBahasa;

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

        ImageView slideImageView = view.findViewById(R.id.slideTajwidImages);
        slideImageView.setImageResource(slider_Images[position]);
        slideImageView.setRotationY(180);
        container.addView(view);

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
        }else
        if (metrics >= 2.0){
            slideImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onLongClick(View v) {
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

                //Delete all data from Room first
                contentViewModel.deleteAllContents();

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

                //Add all data to Room
                if (condition == 1){
                    for (int i = 0; i < 4; i++){
                        Content content = new Content(contentArab[i], contentBahasa[i]);
                        contentViewModel.insert(content);
                    }
                }else if (condition == 2){
                    for (int i = 4; i < 7; i++){
                        Content content = new Content(contentArab[i], contentBahasa[i]);
                        contentViewModel.insert(content);
                    }
                }

                if (alertDialog.isShowing()){
                    //Do nothing
                }else {
                    //Delete all data from Room first
                    contentViewModel.deleteAllContents();

                    for (int i = 0; i < id.length; i++){
                        Content content = new Content(contentArab[i], contentBahasa[i]);
                        contentViewModel.insert(content);
                    }
                }

                //Info
                Log.d("", "Info contentViewModel : " + contentViewModel.getAllContent().getValue());
                Log.d("", "Info arab : " + Arrays.toString(contentArab));
                Log.d("", "Info Bahasa : " + Arrays.toString(contentBahasa));

            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                //Dismiss loading bar
                progressDialog.dismiss();

                //Delete all data from Room first
                contentViewModel.deleteAllContents();

                //Check array null or not
                if (contentViewModel.getAllContent().getValue() == null || contentViewModel.getAllContent().getValue().isEmpty()) {
                    Toast.makeText(context, "Hidupkan data untuk download konten tajwid", Toast.LENGTH_LONG).show();
                }else if (condition == 1){
                    for (int i = 0; i < 4; i++){
                        Content content = new Content(contentArab[i], contentBahasa[i]);
                        contentViewModel.insert(content);
                    }
                }else if (condition == 2){
                    for (int i = 4; i < 7; i++){
                        Content content = new Content(contentArab[i], contentBahasa[i]);
                        contentViewModel.insert(content);
                    }
                }

                Toast.makeText(context, "Mode Offline", Toast.LENGTH_SHORT).show();
            }
        });

        //Room database and Recycler view
        RecyclerView recyclerView = layoutView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);

        final ContentAdapter contentAdapter = new ContentAdapter();
        recyclerView.setAdapter(contentAdapter);

        contentViewModel = ViewModelProviders.of((FragmentActivity) context).get(ContentViewModel.class);
        contentViewModel.getAllContent().observe((LifecycleOwner) context, new Observer<List<Content>>() {
            @Override
            public void onChanged(List<Content> contents) {
                contentAdapter.setContent(contents);
            }
        });

        //---------
        //For audio
        imgAudio = layoutView.findViewById(R.id.imgAudio);
        mediaPlayer = MediaPlayer.create(context, R.raw.bismillah);
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
        //End For audio---

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
     * Setup coordinate
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void alFatihahXhdpi() {
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
