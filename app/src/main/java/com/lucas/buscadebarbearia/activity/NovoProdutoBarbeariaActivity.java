package com.lucas.buscadebarbearia.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.lucas.buscadebarbearia.R;

public class NovoProdutoBarbeariaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_produto_barbearia);


        //configurações para Toolbar
        Toolbar toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle("Adicionar novo Serviço");
        setSupportActionBar(toolbar);
    }


}
