package dam.pmdm.spyrothedragon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding;
import dam.pmdm.spyrothedragon.databinding.GuiaColeccionableBinding;
import dam.pmdm.spyrothedragon.databinding.GuiaInformacionBinding;
import dam.pmdm.spyrothedragon.databinding.GuiaMundosBinding;
import dam.pmdm.spyrothedragon.databinding.GuiaPantallaFinalBinding;
import dam.pmdm.spyrothedragon.databinding.GuiaPersonajesBinding;
import dam.pmdm.spyrothedragon.databinding.Pantalla1Binding;
import dam.pmdm.spyrothedragon.ui.DrawView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    NavController navController = null;
    private Pantalla1Binding bindingPantalla;
    private GuiaPersonajesBinding guiaPersonajesBinding;
    private GuiaMundosBinding guiaMundosBinding;
    private GuiaPantallaFinalBinding guiaFinalBinding;
    private GuiaColeccionableBinding guiaColeccionesBinding;
    private GuiaInformacionBinding guiaInformacionBinding;

    private int contadorGuia = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        bindingPantalla = binding.includePantalla1;
        guiaPersonajesBinding = binding.includePersoneje;
        guiaMundosBinding = binding.guiaMundos;
        guiaColeccionesBinding = binding.includeColeciones;
        guiaFinalBinding = binding.includeFinal;
        guiaInformacionBinding = binding.guiaInformacion;
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.navView, navController);

        }

        binding.navView.setOnItemSelectedListener(this::selectedBottomMenu);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_characters ||
                    destination.getId() == R.id.navigation_worlds ||
                    destination.getId() == R.id.navigation_collectibles
                    || destination.getId() == R.id.videoFragment
            ) {
                // Para las pantallas de los tabs, no queremos que aparezca la flecha de atrás
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            } else {
                // Si se navega a una pantalla donde se desea mostrar la flecha de atrás, habilítala
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean GuiaActivada = sharedPreferences.getBoolean("primeraVez", true);
        if (GuiaActivada) {
            sharedPreferences.edit().putBoolean("primeraVez", false).apply();
            inicializarGuia();
        } else {
            bindingPantalla.pantalla1.setVisibility(View.INVISIBLE);
        }

        

    }

    private void inicializarGuia() {

        bindingPantalla.pantalla1.setVisibility(View.VISIBLE);
        bindingPantalla.btnPlay.setOnClickListener(view -> itemClicked());


    }

    private void navegacionGuia() {
        TextView texto;
        ImageView imagen;
        Button botonContinuar;
        Button botonSalir;

        if (contadorGuia == 1) {

            bindingPantalla.pantalla1.setVisibility(View.GONE);
            guiaPersonajesBinding.gPersonajes.setVisibility(View.VISIBLE);
            texto = guiaPersonajesBinding.textoPersonaje;
            imagen = guiaPersonajesBinding.marcador;
             botonContinuar = guiaPersonajesBinding.botonContinuar;
            botonSalir = guiaPersonajesBinding.botonSalir;
            animacion(imagen, texto, botonSalir, botonContinuar);
            guiaPersonajesBinding.botonContinuar.setOnClickListener(view -> itemClicked());
            guiaPersonajesBinding.botonSalir.setOnClickListener(view -> salirGuia());
        }
        if (contadorGuia == 2) {
            guiaPersonajesBinding.gPersonajes.setVisibility(View.GONE);
            navController.navigate(R.id.action_worlds_to_coleccion);
            guiaMundosBinding.gMundos.setVisibility(View.VISIBLE);
             texto= guiaMundosBinding.textoMundos;
             imagen = guiaMundosBinding.marcador;
             botonContinuar = guiaMundosBinding.botonContinuar;
           botonSalir = guiaMundosBinding.botonSalir;
            animacion(imagen, texto, botonContinuar, botonSalir);
            guiaMundosBinding.botonContinuar.setOnClickListener(view -> itemClicked());
            guiaMundosBinding.botonSalir.setOnClickListener(view -> salirGuia());

        }
        if (contadorGuia == 3) {
            guiaMundosBinding.gMundos.setVisibility(View.GONE);
            guiaColeccionesBinding.guiaColecciones.setVisibility(View.VISIBLE);
            navController.navigate(R.id.action_characters_to_coleccion);
           texto = guiaColeccionesBinding.textoColeccionables;
             imagen = guiaColeccionesBinding.marcador;
             botonContinuar = guiaColeccionesBinding.botonContinuar;
             botonSalir = guiaColeccionesBinding.botonSalir;
            animacion(imagen, texto, botonContinuar, botonSalir);
            guiaColeccionesBinding.botonContinuar.setOnClickListener(view -> itemClicked());
            guiaColeccionesBinding.botonSalir.setOnClickListener(view -> salirGuia());

        }
        if (contadorGuia == 4) {
            guiaColeccionesBinding.guiaColecciones.setVisibility(View.GONE);
            guiaInformacionBinding.gInformacion.setVisibility(View.VISIBLE);
             texto = guiaInformacionBinding.textoInformacion;
            imagen = guiaInformacionBinding.marcador;
            botonContinuar = guiaInformacionBinding.botonContinuar;
            botonSalir = guiaInformacionBinding.botonSalir;
            animacion(imagen, texto, botonContinuar, botonSalir);
            guiaInformacionBinding.botonContinuar.setOnClickListener(view -> itemClicked());
            guiaInformacionBinding.botonSalir.setOnClickListener(view -> salirGuia());

        }
        if (contadorGuia == 5) {
            guiaInformacionBinding.gInformacion.setVisibility(View.GONE);
            guiaFinalBinding.gPantallafinal.setVisibility(View.VISIBLE);
            guiaFinalBinding.botonFinal.setOnClickListener(view -> salirGuia());


        }
    }

    private void salirGuia() {
        guiaColeccionesBinding.guiaColecciones.setVisibility(View.GONE);
        guiaMundosBinding.gMundos.setVisibility(View.GONE);
        guiaPersonajesBinding.gPersonajes.setVisibility(View.GONE);
        bindingPantalla.pantalla1.setVisibility(View.GONE);
        guiaFinalBinding.gPantallafinal.setVisibility(View.GONE);
        guiaInformacionBinding.gInformacion.setVisibility(View.GONE);

        if (contadorGuia == 5) {
            SoundPool soundPool = new SoundPool.Builder().build();
            int soundId = soundPool.load(this, R.raw.gems_sound, 1);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    soundPool.play(sampleId, 1, 1, 0, 0, 1);
                }
            });


    }

    }


    private void itemClicked() {
        contadorGuia++;
        navegacionGuia();

    }

    public void playVideo() {
        navController.navigate(R.id.videoFragment);
    }






    private void animacion( ImageView imagen, TextView texto, Button botonContinuar, Button botonSalir) {

        botonContinuar.setVisibility(View.VISIBLE);
        botonSalir.setVisibility(View.VISIBLE);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(imagen, "scaleX", 0f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(imagen, "scaleY", 0f, 1f);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(imagen, "alpha", 0f, 1f);
            scaleX.setDuration(500);
            scaleY.setDuration(500);
            alpha.setDuration(500);
            scaleX.setRepeatMode(ObjectAnimator.REVERSE);
            scaleY.setRepeatMode(ObjectAnimator.REVERSE);
            alpha.setRepeatMode(ObjectAnimator.REVERSE);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY, alpha);
            animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                texto.setVisibility(View.VISIBLE);
                ObjectAnimator   alphaTexto = ObjectAnimator.ofFloat(texto, "alpha", 0f, 1f);
                alphaTexto.setDuration(500);
                alphaTexto.start();

                botonContinuar.setVisibility(View.VISIBLE);
                botonSalir.setVisibility(View.VISIBLE);
            }
        });

    }



    private boolean selectedBottomMenu(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_characters)
            navController.navigate(R.id.navigation_characters);
        else
        if (menuItem.getItemId() == R.id.nav_worlds)
            navController.navigate(R.id.navigation_worlds);
        else
            navController.navigate(R.id.navigation_collectibles);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gestiona el clic en el ítem de información
        if (item.getItemId() == R.id.action_info) {
            showInfoDialog();  // Muestra el diálogo
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInfoDialog() {
        // Crear un diálogo de información
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_about)
                .setMessage(R.string.text_about)
                .setPositiveButton(R.string.accept, null)
                .show();
    }





}
