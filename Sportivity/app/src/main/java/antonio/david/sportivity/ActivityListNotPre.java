package antonio.david.sportivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import antonio.david.sportivity.Database.Actividad;
import antonio.david.sportivity.Database.ActividadViewModel;

public class ActivityListNotPre extends AppCompatActivity {


    private static final int TEXT_REQUEST = 1;
    private ActividadViewModel mActividadModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_not_pre);

        RecyclerView recyclerView = findViewById(R.id.reciclerViewNotPre);
        final ActividadListAdapter adapter = new ActividadListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mActividadModel = ViewModelProviders.of(this).get(ActividadViewModel.class);


        mActividadModel.getAllNotpre().observe(this, new Observer<List<Actividad>>() {
            @Override
            public void onChanged(@Nullable final List<Actividad> actividades) {
                adapter.setmActividades(actividades);
            }
        });



        adapter.setOnItemClickListener(new ActividadListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Actividad actividad = adapter.getActividadAtPosition(position);
                Intent intent = new Intent(ActivityListNotPre.this, Activity_Detail.class);
                intent.putExtra("Nombre", actividad.getNombreActividad());
                intent.putExtra("ZonaEntreno", actividad.getZonaEntreno());
                intent.putExtra("Tiempo", actividad.getTiempo());
                intent.putExtra("Repeticiones", actividad.getRepeticiones());
                startActivity(intent);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Actividad myActividad = adapter.getActividadAtPosition(position);
                        Toast.makeText(ActivityListNotPre.this, "Actividad Borrada " +
                                myActividad.getNombreActividad(), Toast.LENGTH_LONG).show();

                        mActividadModel.deleteWord(myActividad);
                    }
                });

        helper.attachToRecyclerView(recyclerView);
    }
}
