package com.example.nelioeasynet.scancode;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.ArrayAdapter;

import android.widget.ListView;

import com.example.nelioeasynet.model.ManagerBanco;

import java.util.ArrayList;

public class ListaDadosImportados extends AppCompatActivity {

    ArrayList<String> itens;
    ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ManagerBanco db = new ManagerBanco(this);
        setContentView(R.layout.activity_lista_dados_importados);
        itens = db.getAllItens();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itens);
        ListView listView = (ListView) findViewById(R.id.exibeDados);
        listView.setAdapter(adapter);


    }

}
