package antonio.david.sportivity.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ActividadViewModel extends AndroidViewModel {

    private ActividadRepository mRepository;
    private LiveData<List<Actividad>> mAllActivities;


    public ActividadViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ActividadRepository(application);
        mAllActivities = mRepository.getmAllActivities();
    }

    LiveData<List<Actividad>> getAllActivities() { return mAllActivities; }

    public void insert(Actividad actividad) { mRepository.insert(actividad); }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteWord(Actividad actividad) {mRepository.deleteWord(actividad);}

}
