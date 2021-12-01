package antonio.david.sportivity.Database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "actividad_table")
public class Actividad {

    @PrimaryKey (autoGenerate = true)
    private int id;

    private String nombreActividad;
    private String descripcionActividad;
    private String zonaEntreno;

    @Ignore
    public Actividad(String nombreActividad, String descripcionActividad, String zonaEntreno) {
        this.nombreActividad = nombreActividad;
        this.descripcionActividad = descripcionActividad;
        this.zonaEntreno = zonaEntreno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public String getZonaEntreno() {
        return zonaEntreno;
    }

    public void setZonaEntreno(String zonaEntreno) {
        this.zonaEntreno = zonaEntreno;
    }
}
