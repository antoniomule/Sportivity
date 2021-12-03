package antonio.david.sportivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import antonio.david.sportivity.Database.Actividad;

//Tengo que acordarme que si quiero mostrar una lista de las categorias de los deportes, tengo que hacer un adapter diferente


public class ActividadListAdapter extends RecyclerView.Adapter<ActividadListAdapter.ActividadViewHolder> {

    private final LayoutInflater mInflater;
    private List<Actividad> mActividades;
    private static ClickListener clickListener;

    public ActividadListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activities_pre, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadViewHolder holder, int position) {

        if (mActividades != null) {
            Actividad current = mActividades.get(position);
            holder.nombreActividad.setText(current.getNombreActividad());
            holder.descripcionActividad.setText(current.getNombreActividad());
            holder.zonaEntreno.setText(current.getZonaEntreno());
        } else {
            holder.nombreActividad.setText("Sin nombre");
            holder.descripcionActividad.setText("Sin descripcion");
            holder.zonaEntreno.setText("sin zona");
        }
    }
    void setmActividades(List<Actividad> actividad) {
        mActividades = actividad;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
       if (mActividades != null)
           return mActividades.size();
       else return 0;
    }

    public Actividad getActividadAtPosition(int position) {
        return mActividades.get(position);
    }

    public class ActividadViewHolder extends RecyclerView.ViewHolder {

        public final TextView nombreActividad;
        public final TextView descripcionActividad;
        public final TextView zonaEntreno;

        public ActividadViewHolder(@NonNull View itemView, TextView nombreActividad, TextView descripcionActividad, TextView zonaEntreno) {
            super(itemView);
            this.nombreActividad = nombreActividad;
            this.descripcionActividad = descripcionActividad;
            this.zonaEntreno = zonaEntreno;
        }

        private ActividadViewHolder(View itemView) {
            super(itemView);
            nombreActividad = itemView.findViewById(R.id.nombreActividad);
            descripcionActividad = itemView.findViewById(R.id.descripcion);
            zonaEntreno = itemView.findViewById(R.id.zonaEntreno);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        ActividadListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
