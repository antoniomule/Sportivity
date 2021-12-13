package antonio.david.sportivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import antonio.david.sportivity.Database.Actividad;
import antonio.david.sportivity.Database.ActividadViewModel;

public class ActivityListPre extends AppCompatActivity {

    private static final int TEXT_REQUEST = 1;
    private ActividadViewModel mActividadModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lisit_actividades);


        RecyclerView recyclerView = findViewById(R.id.reciclerViewPre);
        final ActividadListAdapter adapter = new ActividadListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mActividadModel = ViewModelProviders.of(this).get(ActividadViewModel.class);


        mActividadModel.getAllpre().observe(this, new Observer<List<Actividad>>() {
            @Override
            public void onChanged(@Nullable final List<Actividad> actividades) {
                adapter.setmActividades(actividades);
            }
        });



        adapter.setOnItemClickListener(new ActividadListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Actividad actividad = adapter.getActividadAtPosition(position);
                Intent intent = new Intent(ActivityListPre.this, Activity_Detail.class);
                intent.putExtra("Nombre", actividad.getNombreActividad());
                intent.putExtra("ZonaEntreno", actividad.getZonaEntreno());
                intent.putExtra("Tiempo", actividad.getTiempo());
                intent.putExtra("Repeticiones", actividad.getRepeticiones());
                startActivity(intent);

            }
        });

    }

    public void addActividad(View view) {
        Intent intent = new Intent(this, NewActivity.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }
}