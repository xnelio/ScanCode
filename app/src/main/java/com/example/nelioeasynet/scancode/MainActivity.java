package com.example.nelioeasynet.scancode;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.nelioeasynet.model.ManagerBanco;
import com.example.nelioeasynet.model.Produto;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private static final int IMPORTAR_ARQUIVO = 0;
    private static final int ARQ_SALVAR = 1;
    private static final int ZERAR_BANCO= 2;
    private static final int VER_DADOS_IMPORTADOS = 3;
    private ManagerBanco db = new ManagerBanco(this);


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    //product barcode mode
    public void scanBar(View v) {

        try {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();

        } catch (ActivityNotFoundException anfe) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Não foi possivel iniciar o Leitor!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    //product qr code mode
    public void scanQR(View v) {

    }

    //Importa arquivo txt
    public List<Produto> importarArquivo() {
        List<Produto> prod = new ArrayList<>();
        String code ="", desc= "", unidMedida= "", qtd = "";
        AssetManager assetManager = getResources().getAssets();
        InputStream inputStream;
        Produto produto = null;
        StringTokenizer str;
        try {
            inputStream = assetManager.open("dados.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String recebe_string;
            if ((recebe_string = bufferedReader.readLine()) != null) {
                while ((recebe_string = bufferedReader.readLine()) != null) {
                    str = new StringTokenizer(recebe_string, ";");
                    while (str.hasMoreTokens()) {
                        code = str.nextToken();
                        desc = str.nextToken();
                        unidMedida = str.nextToken();
                        qtd = str.nextToken();
                        produto = new Produto(code, desc, unidMedida, qtd);
                        prod.add(produto);
                    }
                }
            } else {
                Toast toast = Toast.makeText(this, "Não há dados no arquivo!", Toast.LENGTH_LONG);
                toast.show();
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prod;

    }

    //alert dialog for downloadDialog

    private static AlertDialog showDialog(final AppCompatActivity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Produto produto;

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        Toast toast;

        if (scanningResult != null) {
            String scanResult = scanningResult.getContents();

            try {

                produto = db.getProduto(scanResult);
                Log.d("Leitura: ", produto.getCode());

                if(produto != null){
                    if(produto.getCode().equals(scanResult))
                    {
                        String[] dados = {produto.getCode(), produto.getDesc(), produto.getQtd(), produto.getUnidMedida()};
                        Intent myIntent = new Intent(this, EditarProduto.class);
                        myIntent.putExtra("v", dados);
                        startActivity(myIntent);
                    }

                }else{
                    toast = Toast.makeText(getApplicationContext(), "Produto não encontrado na lista Importada.", Toast.LENGTH_SHORT);
                    toast.show();
                    //produtoNaoEncontrado("Erro", "Esse Código não foi localizado na base", "OK" );
                }
            } catch (EmptyStackException ignored) {

            }

        } else {
            toast = Toast.makeText(getApplicationContext(), " não foi possivel LER O ITEM!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     *  cria o menu e submenus. Caso seja necessário desabilitar o menu Arquivo, abaixo segue exemplo
     *  menu.findItem(ARQUIVO).setEnabled(false);
     *  caso seja necessário desabilitar um subitem do menu, abaixo segue exemplo
     *  menuEditar.findItem(EDITAR_COPIAR).setEnabled(false);
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {

            MenuItem menuArquivo = menu.add(IMPORTAR_ARQUIVO, IMPORTAR_ARQUIVO, 0, "Importar Arquivo");
            MenuItem menuSalvar = menu.add(ARQ_SALVAR, ARQ_SALVAR, 1, "Salvar Alterações");
            MenuItem menuDeletar = menu.add(ZERAR_BANCO, ZERAR_BANCO, 2, "Apagar o Banco");
            MenuItem menuVerImportados = menu.add(VER_DADOS_IMPORTADOS, VER_DADOS_IMPORTADOS, 3, "Ver Dados Importados");

        } catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //de acordo com o item selecionado você executará a função desejada
        switch (item.getItemId()) {
            case IMPORTAR_ARQUIVO:

                String result = db.insereDados(importarArquivo());
                if(result.contains("Erro")) {
                    Toast toast = Toast.makeText(this, result, Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    Toast toast = Toast.makeText(this, result, Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            case ARQ_SALVAR:
                trace("Você selecionou o menu Salvar");
                break;
            case ZERAR_BANCO:
                if(db.bdIsEmpty()){
                    Toast toast = Toast.makeText(this, "Não há dados no banco", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    ArrayList<String>  array =  db.getAllID();
                    for(String k: array){
                        db.limparBanco(k);
                        Toast toast = Toast.makeText(this, "O banco foi apagado!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }

                break;
            case VER_DADOS_IMPORTADOS:
                if(db.bdIsEmpty()){
                    Toast toast = Toast.makeText(this, "Não há dados a serem exibidos!", Toast.LENGTH_LONG);
                    toast.show();
                } else{
                    Intent intent = new Intent(this, ListaDadosImportados.class);
                    startActivity(intent);
                }

                break;
            default:
                break;
        }
        return true;
    }
    private AlertDialog produtoNaoEncontrado(CharSequence title, CharSequence message, CharSequence buttonYes) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(getBaseContext());
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });

        return downloadDialog.show();
    }

    private void trace(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}