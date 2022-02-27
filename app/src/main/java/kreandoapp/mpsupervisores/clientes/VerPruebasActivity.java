package kreandoapp.mpsupervisores.clientes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import kreandoapp.mpsupervisores.DetalleNodoActivity;
import kreandoapp.mpsupervisores.R;

public class VerPruebasActivity extends AppCompatActivity {

    ImageButton botonvolver,btn_editar;
    TextView tv_titulo_toolbar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refCajas;
    TextView tv_fecha_1,tv_hora_1;
    TextView tv_fecha_2,tv_hora_2;
    ImageView img_foto_1,img_foto_2;
    TextView tv_tiempo,tv_Fase2;
    Button btn_fase1,btn_fase2 ;
    CardView cardtiempo,card_fase2,card_fotofase2;
    ProgressBar progres_foto_f1,progres_foto_f2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pruebas);

        tv_fecha_1 = findViewById(R.id.tv_fecha_1);
        tv_hora_1 = findViewById(R.id.tv_hora_1);
        img_foto_1 = findViewById(R.id.foto_1);

        tv_fecha_2 = findViewById(R.id.tv_fecha_2);
        tv_hora_2 = findViewById(R.id.tv_hora_2);
        img_foto_2 = findViewById(R.id.foto_2);

        tv_tiempo = findViewById(R.id.tv_tiempo);
        cardtiempo = findViewById(R.id.card_tiempo);
        card_fase2 = findViewById(R.id.card_fase2);
        card_fotofase2 = findViewById(R.id.card_fotofase2);
        btn_fase1 = findViewById(R.id.btn_mapa_f1);
        btn_fase2 = findViewById(R.id.btn_mapa_f2);
        progres_foto_f1 = findViewById(R.id.progres_foto_f1);
        progres_foto_f2 = findViewById(R.id.progres_foto_f2);

        refCajas = FirebaseDatabase.getInstance().getReference().child("Cajas");

        final String nombre_caja = getIntent().getExtras().getString("nombre_caja");

        final String fecha_1 = getIntent().getExtras().getString("fase1-fecha");
        final String hora_1 = getIntent().getExtras().getString("fase1-hora");
        final String foto_1 = getIntent().getExtras().getString("fase1-foto");
        final String latitud_1 = getIntent().getExtras().getString("fase1-latitud");
        final String longitud_1 = getIntent().getExtras().getString("fase1-longitud");

        final String fecha_2 = getIntent().getExtras().getString("fase2-fecha");
        final String hora_2 = getIntent().getExtras().getString("fase2-hora");
        final String foto_2 = getIntent().getExtras().getString("fase2-foto");
        final String tiempo = getIntent().getExtras().getString("tiempo");
        final String latitud_2 = getIntent().getExtras().getString("fase2-latitud");
        final String longitud_2 = getIntent().getExtras().getString("fase2-longitud");

        btn_fase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUri = "http://maps.google.com/maps?q=loc:" + latitud_1 + "," + longitud_1 + " (" + "TEST USUARIO" + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

        btn_fase2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUri = "http://maps.google.com/maps?q=loc:" + latitud_2 + "," + longitud_2 + " (" + "TEST USUARIO" + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });


        tv_Fase2 = findViewById(R.id.tv_Fase2);

        tv_fecha_1.setText(fecha_1);tv_hora_1.setText(hora_1);
        if(!fecha_2.equals("")){
            tv_fecha_2.setText(fecha_2);tv_hora_2.setText(hora_2);
            cardtiempo.setVisibility(View.VISIBLE);
            card_fase2.setVisibility(View.VISIBLE);
            tv_Fase2.setVisibility(View.VISIBLE);
            card_fotofase2.setVisibility(View.VISIBLE);
            btn_fase2.setVisibility(View.VISIBLE);
        }


        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Pruebas de caja: "+nombre_caja );

        tv_tiempo.setText(tiempo);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String destino = sh.getString("destino", "");

        if(destino.equals("confirmar")){
            ponerenvisto();
        }


        botonvolver = findViewById(R.id.btn_volverAtras);
        botonvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });

        Picasso.with(this).load(foto_1).into(img_foto_1, new Callback() {
            @Override
            public void onSuccess() {

                progres_foto_f1.setVisibility(View.GONE);
                img_foto_1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {

            }
        });
        if(!foto_2.equals("")){
            Picasso.with(this).load(foto_2).into(img_foto_2, new Callback() {
                @Override
                public void onSuccess() {
                progres_foto_f2.setVisibility(View.GONE);
                img_foto_2.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {

                }
            });
        }




    }//fin del oncreate!




    private void ponerenvisto() {
        final String id_caja = getIntent().getExtras().getString("id_caja");
        refCajas.child(id_caja).child("visto").setValue("si");
    }
}