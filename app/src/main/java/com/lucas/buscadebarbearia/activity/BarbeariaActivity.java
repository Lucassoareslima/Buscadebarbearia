package com.lucas.buscadebarbearia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.lucas.buscadebarbearia.R;
import com.lucas.buscadebarbearia.helper.ConfiguracaoFirebase;

public class BarbeariaActivity extends AppCompatActivity {


    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbearia);


        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //configuração Toolbar

        Toolbar toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle("Busca barbearia - barbeiros");
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_barbearia,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuSair:
                deslogarUsuario();
                break;
            case R.id.menuConfiguracao:
                abrirConfiguracao();
                break;
            case R.id.menuNovoProd:
                adicionarProd();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void deslogarUsuario(){
        try{
            autenticacao.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private  void abrirConfiguracao(){

        startActivity(new Intent(BarbeariaActivity.this, ConfiguracoesBarbeariaActivity.class));

    }

    private void adicionarProd(){

        startActivity(new Intent(BarbeariaActivity.this, NovoProdutoBarbeariaActivity.class));
    }
}
