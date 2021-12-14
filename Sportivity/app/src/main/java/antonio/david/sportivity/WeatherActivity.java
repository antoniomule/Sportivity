package antonio.david.sportivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class WeatherActivity extends AppCompatActivity {


    String CITY;
    String API = "275dd33817d21c3d0f9e8c2792f90b6a";
    ImageView search;
    EditText etCity;
    TextView city,country,time,temp,forecast,humidity,min_temp,max_temp,sunrises,sunsets,pressure,windSpeed;

    public WeatherActivity() throws CertificateException, KeyStoreException, FileNotFoundException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        {
            etCity =  findViewById(R.id.Your_city);
            search = findViewById(R.id.search);


            city =  findViewById(R.id.city);
            country = findViewById(R.id.country);
            time = findViewById(R.id.time);
            temp = findViewById(R.id.temp);
            forecast = findViewById(R.id.forecast);
            humidity = findViewById(R.id.humidity);
            min_temp = findViewById(R.id.min_temp);
            max_temp = findViewById(R.id.max_temp);
            sunrises = findViewById(R.id.sunrises);
            sunsets =  findViewById(R.id.sunsets);
            pressure = findViewById(R.id.pressure);
            windSpeed = findViewById(R.id.wind_speed);


            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CITY = "CADIZ";/*etCity.getText().toString();*/
                    new weatherTask().execute();
                }
            });
        }
        try {
            certificado();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }



    class weatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            return HttpRequest.excuteGet("http://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject sys = jsonObj.getJSONObject("sys");



                String city_name = jsonObj.getString("name");
                String countryname = sys.getString("country");
                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Last Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temperature = main.getString("temp");
                String cast = weather.getString("description");
                String humi_dity = main.getString("humidity");
                String temp_min = main.getString("temp_min");
                String temp_max = main.getString("temp_max");
                String pre = main.getString("pressure");
                String windspeed = wind.getString("speed");
                Long rise = sys.getLong("sunrise");
                String sunrise = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(rise * 1000));
                Long set = sys.getLong("sunset");
                String sunset = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(set * 1000));



                city.setText(city_name);
                country.setText(countryname);
                time.setText(updatedAtText);
                temp.setText(temperature + "°C");
                forecast.setText(cast);
                humidity.setText(humi_dity);
                min_temp.setText(temp_min);
                max_temp.setText(temp_max);
                sunrises.setText(sunrise);
                sunsets.setText(sunset);
                pressure.setText(pre);
                windSpeed.setText(windspeed);

            } catch (Exception e) {

                Toast.makeText(WeatherActivity.this, "Error:" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void certificado() throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // From https://www.washington.edu/itconnect/security/ca/load-der.crt
        InputStream caInput = new BufferedInputStream(new FileInputStream("SectigoRSADomainValidationSecureServerCA.crt"));
        Certificate ca;
        try {
            ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        } finally {
            caInput.close();
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

        // Tell the URLConnection to use a SocketFactory from our SSLContext
        URL url = new URL("https://api.openweathermap.org/");
        HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
        urlConnection.setSSLSocketFactory(context.getSocketFactory());
        InputStream in = urlConnection.getInputStream();
        //copyInputStreamToOutputStream(in, System.out);

    }
}
