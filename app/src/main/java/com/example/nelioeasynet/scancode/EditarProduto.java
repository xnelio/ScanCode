package com.example.nelioeasynet.scancode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by nelio.easynet on 22/02/2017.
 */

public class EditarProduto extends AppCompatActivity {
    TextView nomeProduto, codigoDoProduto;
    EditText quantidade,  unidadeDeMedida;
    TextView produto, codigo, qtd, unidMedida;
    Button btnSalvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produto_layout);
        produto = (TextView) findViewById(R.id.textView);
        codigo = (TextView) findViewById(R.id.textView04);
        qtd = (TextView) findViewById(R.id.textView03);
        unidMedida = (TextView) findViewById(R.id.textView03);
        nomeProduto = (TextView) findViewById(R.id.textView01);
        codigoDoProduto = (TextView) findViewById(R.id.TextView05);
        quantidade = (EditText) findViewById(R.id.editText01);
        unidadeDeMedida = (EditText) findViewById(R.id.editText02);

        String[] dadosRecuperados = getIntent().getStringArrayExtra("v");

        nomeProduto.setText(dadosRecuperados[1]);
        codigoDoProduto.setText(dadosRecuperados[0]);
        //quantidade.setText(dadosRecuperados[2]);
        unidadeDeMedida.setText(dadosRecuperados[3]);
        btnSalvar = (Button) findViewById(R.id.editarProdutoSalvar);

    }

}
