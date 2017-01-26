package felixserrano.example.org.gestordepermisosm;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String[] PERMISOS_INICIALES ={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };

    private static final String[] PERMISOS_CAMARA ={
            Manifest.permission.CAMERA
    };

    private static final String[] PERMISOS_CONTACTOS ={
            Manifest.permission.READ_CONTACTS
    };

    private static final String[] PERMISOS_LOCALIZACION ={
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static final int PETICION_INICIAL=123;
    private static final int PETICION_CAMARA =PETICION_INICIAL+1;
    private static final int PETICION_CONTACTOS =PETICION_INICIAL+2;
    private static final int PETICION_LOCALIZACION =PETICION_INICIAL+3;
    private TextView localizacion;
    private TextView camara;
    private TextView internet;
    private TextView contactos;
    private TextView almacenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localizacion = (TextView)findViewById(R.id.location_value);
        camara=(TextView)findViewById(R.id.camera_value);
        internet=(TextView) findViewById(R.id.internet_value);
        contactos=(TextView)findViewById(R.id.contacts_value);
        almacenamiento=(TextView)findViewById(R.id.storage_value);

        if(!hayPermisoLocalizacion() || !hayPermisoContactos()){
            ActivityCompat.requestPermissions(this,PERMISOS_INICIALES,PETICION_INICIAL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarTabla();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return(super.onCreateOptionsMenu(menu));
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.camera:
                if (hayPermisoCamara()) {
                    accionCamara();
                } else {
                    ActivityCompat.requestPermissions(this, PERMISOS_CAMARA, PETICION_CAMARA);
                } return(true);
            case R.id.contacts:
                if (hayPermisoContactos()) {
                    accionContactos();
                } else {
                    ActivityCompat .requestPermissions(this, PERMISOS_CONTACTOS, PETICION_CONTACTOS);
                } return(true);
            case R.id.location:
                if (hayPermisoLocalizacion()) { accionLocalizacion();
                } else {
                    ActivityCompat .requestPermissions(this, PERMISOS_LOCALIZACION, PETICION_LOCALIZACION);
                }
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        actualizarTabla();
        switch(requestCode) {
            case PETICION_CAMARA:
                if (hayPermisoCamara()) {
                    accionCamara();
                } else {
                    error();
                }
                break;
            case PETICION_CONTACTOS:
                if (hayPermisoContactos()) {
                    accionContactos();
                } else {
                    error();
                }
                break;
            case PETICION_LOCALIZACION:
                if (hayPermisoLocalizacion()) {
                    accionLocalizacion();
                } else {
                    error();
                }
                break;
        }
    }

    private boolean hayPermiso(String perm) {
        return (ContextCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hayPermisoLocalizacion() {
        return(hayPermiso(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean hayPermisoCamara() {
        return(hayPermiso(Manifest.permission.CAMERA));
    }

    private boolean hayPermisoContactos()
    { return(hayPermiso(Manifest.permission.READ_CONTACTS));
    }

    private void actualizarTabla() {
        localizacion.setText(String.valueOf(hayPermisoLocalizacion()));
        camara.setText(String.valueOf(hayPermisoCamara()));
        internet.setText(String.valueOf(hayPermiso( Manifest.permission.INTERNET)));
        contactos.setText(String.valueOf(hayPermisoContactos()));
        almacenamiento.setText(String.valueOf(hayPermiso( Manifest.permission.WRITE_EXTERNAL_STORAGE)));
    }

    private void error() {
        Toast.makeText(this, R.string.toast_error, Toast.LENGTH_LONG).show();
    }

    private void accionCamara() {
        Toast.makeText(this, R.string.toast_camara, Toast.LENGTH_SHORT).show();
    }

    private void accionContactos() {
        Toast.makeText(this, R.string.toast_contactos, Toast.LENGTH_LONG).show();
    }

    private void accionLocalizacion() {
        Toast.makeText(this, R.string.toast_localizacion, Toast.LENGTH_LONG).show();
    }
}
