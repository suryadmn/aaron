package com.simpleMan.aaron;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
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


    private int a, b;
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

    public int getPosition(int position) {
        mTajwidListener.onTajwid(position);
        return position;
    }

    public void setOnTawjidClickListener(OnTajwidClickListener listener) {
        mTajwidListener = listener;
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

                Log.i("Info Coordinate", "X : " + a + " Y : " + b);
                return false;
            }
        });

        slideImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onLongClick(View v) {
                if (position == 0) {
                    doCoordinate();
                } else if (position == 1) {
                    Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                } else if (position == 2) {
                    Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                } else if (position == 3) {
                    Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                } else if (position == 4) {
                    Toast.makeText(context, "Content not available yet ", Toast.LENGTH_LONG).show();
                }

                //Get position image with Listener
                getPosition(position);
                return false;
            }
        });

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
    private void showAlertDialog(int layout) {
        dialogBuilder = new AlertDialog.Builder(context);
        final View layoutView = layoutInflater.inflate(layout, null);
        Button dialogButton = layoutView.findViewById(R.id.btnLanjut);
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

        //get data from mysql with Retorfit
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

                contentArab = new String[models.size()];
                contentBahasa = new String[models.size()];

                for (int i = 0; i < models.size(); i++) {
                    contentArab[i] = models.get(i).getArabtajwid();
                    contentBahasa[i] = models.get(i).getPenjelasantajwid();
                }

                //add data to Room
                Log.d("", "Info contentViewModel" + contentViewModel.getAllContent().getValue());
                Log.d("", "Info content : ");

                if (contentArab.length <= 5 || contentBahasa.length <= 5) {

                    //Do get data from retrofit and send into local database
                    for (int i = 0; i < contentArab.length && i < contentBahasa.length; i++) {

                        Content content = new Content(contentArab[i], contentBahasa[i]);
                        contentViewModel.insert(content);

                    }
                }

                Log.d("", "Info arab : " + Arrays.toString(contentArab));
                Log.d("", "Info Bahasa : " + Arrays.toString(contentBahasa));

            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                progressDialog.dismiss();

                /*contentArab = new String[0];
                contentBahasa = new String[0];*/

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


        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                //Do delete all contents first
                /** contentViewModel.deleteAllContents(); */

                //alertDialog.dismiss();

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                contentViewModel.delete(contentAdapter.getContentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(context, "Content delete", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }


    /**
     * Setup coordinate
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void doCoordinate() {
        int X = a, Y = b;

        //If user click at coordinate
        if (X <= 551 && Y <= 275) {
            //Do nothing
        } else if (X <= 200 && Y <= 350) {
            //Do nothing
        } else if (X <= 551 && Y <= 350) {
            showAlertDialog(R.layout.popup_layout);
            //Toast.makeText(context, "Al-Fatihaa | Ayat : 1", Toast.LENGTH_SHORT).show();

        } else if (X <= 551 && Y <= 380) {
            //Do nothing
        } else if (X <= 200 && Y <= 450) {
            //Do nothing
        } else if (X <= 270 && Y <= 450) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 3", Toast.LENGTH_SHORT).show();
        } else if (X <= 580 && Y <= 450) {
            Toast.makeText(context, "Al-Fatihaa | Ayat : 2", Toast.LENGTH_SHORT).show();

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

}
