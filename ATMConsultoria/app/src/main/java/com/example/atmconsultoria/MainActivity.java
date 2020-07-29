package com.example.atmconsultoria;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                enviarEmail();

            }

        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_principal,
                R.id.nav_servico,
                R.id.nav_cliente,
                R.id.nav_contato,
                R.id.nav_sobre
        )
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void enviarEmail(){

        String celular = "74999998750";
        String endereco = "https://www.google.com.br/maps/place/R.+Baixa+Grande,+Irec%C3%AA+-+BA,+44900-000/@-11.3061731,-41.8744236,17z/data=!3m1!4b1!4m5!3m4!1s0x76ed4d2f6c7bd79:0x6187bc375e6bd538!8m2!3d-11.3061784!4d-41.8722349";
        String imagem = "https://www.google.com/search?q=praia&client=ubuntu&hs=eVx&channel=fs&sxsrf=ALeKk01zwcxcTIap_iq9Z1_qaM0QRWy1fg:1595980405039&tbm=isch&source=iu&ictx=1&fir=YbLqGu7_1T2xaM%252CHIL_1JzjdkwI-M%252C_&vet=1&usg=AI4_-kSrL0K_Sm8RMrH3g0PJVXrFMbVlCw&sa=X&ved=2ahUKEwibjPGokvHqAhUyHLkGHUGVB4AQ9QEwAHoECAgQMA&biw=1474&bih=671#imgrc=YbLqGu7_1T2xaM";
        String email = "";

        //Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(celular));
        //Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(imagem));
        //Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(endereco));
        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"itaperam@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Contato do app");
        intent.putExtra(Intent.EXTRA_TEXT, "Mensagem automática!");

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "compartilhar"));

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}