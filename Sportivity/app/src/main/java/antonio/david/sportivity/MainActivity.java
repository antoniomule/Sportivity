package antonio.david.sportivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import antonio.david.sportivity.Database.ActividadViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int TEXT_REQUEST = 1;
    private AppBarConfiguration appBarConfiguration;
    private ActividadViewModel mActividadModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mActividadModel = ViewModelProviders.of(this).get(ActividadViewModel.class);

        final Button buttonActPre = findViewById(R.id.buttonActivitiesPre);
        buttonActPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityListPre.class);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        final Button buttonActNotPre = findViewById(R.id.buttonYourActivities);
        buttonActNotPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityListNotPre.class);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Reset_Activities) {
            Toast.makeText(this, "Tu lista de actividades ha sido borrada", Toast.LENGTH_SHORT).show();
            mActividadModel.deleteAllNotPreActivities();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_controller_view_tag);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}