package com.lucas.buscadebarbearia.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lucas.buscadebarbearia.R;

public class BarbeariaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbearia);


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
}
