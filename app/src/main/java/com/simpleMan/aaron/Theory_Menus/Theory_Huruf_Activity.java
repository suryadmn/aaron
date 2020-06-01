package com.simpleMan.aaron.Theory_Menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.simpleMan.aaron.R;

import java.util.ArrayList;

public class Theory_Huruf_Activity extends AppCompatActivity {
    //Card View
    CardView cv_alif, cv_ba, cv_ta, cv_tsa, cv_jim, cv_ha;
    CardView cv_kho, cv_dal, cv_dzal, cv_ro, cv_zai, cv_sin;
    CardView cv_syin, cv_sod, cv_dod, cv_to, cv_zo, cv_ayn;
    CardView cv_ghoin, cv_fa, cv_qof, cv_kaf, cv_lam, cv_mim;
    CardView cv_nun, cv_haa, cv_waw, cv_lamalif, cv_hamzah, cv_ya;
    CardView cv_makhrajul;

    //initialize btn and txt
    Button close;
    TextView explanation;
    ImageView makhrajul;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory_huruf_);

        //Initialize card view
        cv_alif = findViewById(R.id.alif);        cv_ro = findViewById(R.id.ro);          cv_zo = findViewById(R.id.zo);
        cv_ba = findViewById(R.id.ba);            cv_zai = findViewById(R.id.zai);        cv_ayn = findViewById(R.id.ayn);
        cv_ta = findViewById(R.id.ta);            cv_sin = findViewById(R.id.sin);        cv_ghoin = findViewById(R.id.ghoin);
        cv_tsa = findViewById(R.id.tsa);          cv_syin = findViewById(R.id.syin);      cv_fa = findViewById(R.id.faa);
        cv_jim = findViewById(R.id.jim);          cv_sod = findViewById(R.id.sod);        cv_qof = findViewById(R.id.qof);
        cv_ha = findViewById(R.id.ha);            cv_dod = findViewById(R.id.dod);        cv_mim = findViewById(R.id.mim);
        cv_kho = findViewById(R.id.kho);          cv_to = findViewById(R.id.to);          cv_nun = findViewById(R.id.nun);
        cv_dal = findViewById(R.id.dal);          cv_kaf = findViewById(R.id.kaf);        cv_haa = findViewById(R.id.haa);
        cv_dzal = findViewById(R.id.dzal);        cv_lam = findViewById(R.id.lam);        cv_waw = findViewById(R.id.waw);
        cv_lamalif = findViewById(R.id.lamalif);  cv_hamzah = findViewById(R.id.hamzah);  cv_ya = findViewById(R.id.yaa);

        cv_makhrajul = findViewById(R.id.makhraj_huruf);

        //Toolbar
        getSupportActionBar().setTitle("Teori Huruf");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        switch (position){
            case 0:
                cv_alif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 0);
                    }
                });

            case 1:
                cv_ba.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 1);
                    }
                });
            case 2:
                cv_ta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 2);
                    }
                });

            case 3:
                cv_tsa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 3);
                    }
                });

            case 4:
                cv_jim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 4);
                    }
                });

            case 5:
                cv_ha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 5);
                    }
                });

            case 6:
                cv_kho.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 6);
                    }
                });

            case 7:
                cv_dal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 7);
                    }
                });

            case 8:
                cv_dzal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 8);
                    }
                });

            case 9:
                cv_ro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 9);
                    }
                });

            case 10:
                cv_zai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 10);
                    }
                });

            case 11:
                cv_sin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 11);
                    }
                });

            case 12:
                cv_syin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 12);
                    }
                });

            case 13:
                cv_sod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 13);
                    }
                });

            case 14:
                cv_dod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 14);
                    }
                });

            case 15:
                cv_to.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 15);
                    }
                });

            case 16:
                cv_zo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 16);
                    }
                });

            case 17:
                cv_ayn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 17);
                    }
                });

            case 18:
                cv_ghoin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 18);
                    }
                });

            case 19:
                cv_fa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 19);
                    }
                });
            case 20:
                cv_qof.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 20);
                    }
                });

            case 21:
                cv_kaf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 21);
                    }
                });

            case 22:
                cv_lam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 22);
                    }
                });

            case 23:
                cv_mim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 23);
                    }
                });

            case 24:
                cv_nun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 24);
                    }
                });

            case 25:
                cv_ha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 25);
                    }
                });

            case 26:
                cv_waw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 26);
                    }
                });

            case 27:
                cv_lamalif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 27);
                    }
                });

            case 28:
                cv_hamzah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 28);
                    }
                });

            case 29:
                cv_ya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 29);
                    }
                });

            case 30:
                cv_makhrajul.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue(R.layout.theory_huruf_popup, 30);
                    }
                });

            default:
        }

    }

    /**
     * Handle clicking at card view
     */
    public void showAlertDialogue(int layout, int position){
        //Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(layout, null);
        builder.setView(dialogView);
        //Set alert dialog
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Button and TextView
        close = dialogView.findViewById(R.id.btnClose);
        explanation = dialogView.findViewById(R.id.txtPenjelasan_);
        makhrajul = dialogView.findViewById(R.id.imgMakhrajul);

        if (position == 0){
            explanation.setText("Alif – pasti berbaris sukun, tidak pernah berharokat");
        }else if (position == 1){
            explanation.setText("Ba – melafalkannya dengan mempertemukan dua bibir atas dan bawah");
        }else if (position == 2){
            explanation.setText("Ta – cara melafalkannya ujung lidah bertemu dengan pangkal gigi seri atas");
        }else if (position == 3){
            explanation.setText("Tsa – cara melafalkannya tengah lidah bertemu dengan pangkal gigi seri atas");
        }else if (position == 4){
            explanation.setText("Jim – cara melafalkannya tengah lidah bertemu dengan langit langit mulut");
        }else if (position == 5){
            explanation.setText("Ha – tempat keluarnya huruf di tenggorokan");
        }else if (position == 6){
            explanation.setText("Kha – tempat keluarnya huruf di ujung tenggorokan sebelah luar");
        }else if (position == 7){
            explanation.setText("Dal – cara melafalkannya ujung lidah bertemu dengan pangkal gigi seri atas");
        }else if (position == 8){
            explanation.setText("Dzal – cara melafalkannya ujung lidah bertemu dengan pangkal gigi seri atas");
        }else if (position == 9){
            explanation.setText("Ro – tempat keluarnya huruf di ujung lidah dekat dengan langit langit mulut");
        }else if (position == 10){
            explanation.setText("Zain – tempat keluarnya huruf di antara ujung lidah dengan ujung gigi");
        }else if (position == 11){
            explanation.setText("Sin – tempat keluarnya huruf di antara ujung lidah dengan ujung gigi");
        }else if (position == 12){
            explanation.setText("Syin – cara melafalkannya dengan mempertemukan tengah lidah dan langit langit mulut sebelah atas");
        }else if (position == 13){
            explanation.setText("Shod – cara melafalkannya dengan mempertemukan ujung lidah dengan ujung gigi");
        }else if (position == 14){
            explanation.setText("Dhod – cara melafalkannya ujung lidah bertemu dengan gigi seri atas");
        }else if (position == 15){
            explanation.setText("Tho – cara melafalkannya ujung lidah bertemu dengan pangkal gigi seri atas");
        }else if (position == 16){
            explanation.setText("Dzha – cara melafalkannya ujung lidah bertemu dengan gigi seri atas");
        }else if (position == 17){
            explanation.setText("‘ain – tempat keluarnya huruf ada di tengah-tengah tenggorokan");
        }else if (position == 18){
            explanation.setText("Ghain – tempat keluarnya huruf ada di tenggorokan bagian atas");
        }else if (position == 19){
            explanation.setText("Fa – cara melafalkannya bibir bawah bertemu dengan ujung gigi seri atas");
        }else if (position == 20){
            explanation.setText("Qof – cara melafalkannya pangkal lidah bertemu dengan langit – langit mulut");
        }else if (position == 21){
            explanation.setText("Kaf – cara melafalkannya pangkal lidah bertemu dengan langit-langit mulut sebelah luar");
        }else if (position == 22){
            explanation.setText("Lam – cara melafalkannya ujung lidah bertemu dengan langit-langit mulut");
        }else if (position == 23){
            explanation.setText("Mim – cara melafalkannya dengan mempertemukan dua bibir");
        }else if (position == 24){
            explanation.setText("Nun – cara melafalkannya ujung lidah bertemu dengan langit-langit mulut");
        }else if (position == 25){
            explanation.setText("Ha – tempat keluarnya huruf di tenggorokan sebelah dalam");
        }else if (position == 26){
            explanation.setText("Wau – tempat keluarnya di rongga mulut");
        }else if (position == 27){
            explanation.setText("Lam Alif - Yaitu kombinasi dari huruf lam di ikuti dengan huruf alif.");
        }else if (position == 28){
            explanation.setText("Hamzah - Huruf nya berdiri sendiri, bisa berada di atas maupun di bawah huruf alif, dapat berada di atas huruf wawu juga, dapat berada di atas huruf ya tetapi ya tanpa titik.");
        }else if (position == 29){
            explanation.setText("Ya – tempat keluarnya di rongga mulut");
        }else if (position == 30){
            makhrajul.setImageResource(R.drawable.makharijul);
            makhrajul.getLayoutParams().width = 650;
            makhrajul.getLayoutParams().height = 600;
            makhrajul.requestLayout();
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
