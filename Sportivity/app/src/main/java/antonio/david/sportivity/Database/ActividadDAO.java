package antonio.david.sportivity.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import antonio.david.sportivity.Database.Actividad;

@Dao
public interface ActividadDAO {

    @Query("SELECT * from actividad_table ORDER BY nombreActividad ASC")
    LiveData<List<Actividad>> getAlphabetizedWords();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Actividad actividad);

    @Query("DELETE FROM actividad_table")
    void deleteAll();

    @Query("SELECT * from actividad_table LIMIT 1")
    Actividad[] getAnyWord();

    @Delete
    void deleteWord(Actividad actividad);

}
