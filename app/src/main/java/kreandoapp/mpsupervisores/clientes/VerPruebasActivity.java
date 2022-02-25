package kreandoapp.mpsupervisores.clientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    TextView tv_tiempo;
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


        refCajas = FirebaseDatabase.getInstance().getReference().child("Cajas");

        final String nombre_caja = getIntent().getExtras().getString("nombre_caja");

        final String fecha_1 = getIntent().getExtras().getString("fase1-fecha");
        final String hora_1 = getIntent().getExtras().getString("fase1-hora");
        final String foto_1 = getIntent().getExtras().getString("fase1-foto");

        final String fecha_2 = getIntent().getExtras().getString("fase2-fecha");
        final String hora_2 = getIntent().getExtras().getString("fase2-hora");
        final String foto_2 = getIntent().getExtras().getString("fase2-foto");
        final String tiempo = getIntent().getExtras().getString("tiempo");

        tv_fecha_1.setText(fecha_1);tv_hora_1.setText(hora_1);
        tv_fecha_2.setText(fecha_2);tv_hora_2.setText(hora_2);

        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Pruebas de caja: "+nombre_caja );

        tv_tiempo.setText(tiempo);

        ponerenvisto();

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


            }

            @Override
            public void onError() {

            }
        });
        Picasso.with(this).load(foto_2).into(img_foto_2, new Callback() {
            @Override
            public void onSuccess() {


            }

            @Override
            public void onError() {

            }
        });



    }//fin del oncreate!




    private void ponerenvisto() {
        final String id_caja = getIntent().getExtras().getString("id_caja");
        refCajas.child(id_caja).child("visto").setValue("si");
    }
}