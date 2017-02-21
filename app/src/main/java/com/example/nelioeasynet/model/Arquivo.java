package com.example.nelioeasynet.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by nelio.easynet on 01/02/2017.
 */

public class Arquivo extends AppCompatActivity {

    private String code, desc, unidMedida, qtd;
    private int id;
    private Produto produto;
    private List<Produto> lstProd = new ArrayList<Produto>();
    private Context context;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public List<Produto> importarArquivo() {
        Log.d("Aviso", "Entrou na função importarArquivo!");
        AssetManager assetManager = getResources().getAssets();
        InputStream inputStream;
        StringTokenizer str;
        try {
            inputStream = assetManager.open("dados.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String recebe_string;
            produto = new Produto();

            if ((recebe_string = bufferedReader.readLine()) != null) {
                Log.d("Aviso: ", recebe_string);
                str = new StringTokenizer(recebe_string, ";");
                while (str.hasMoreTokens()) {

                    produto.setCode(str.nextToken());
                    produto.setDesc(str.nextToken());
                    produto.setUnidMedida(str.nextToken());
                    produto.setQtd(str.nextToken());
                    lstProd.add(produto);

                }
            } else {
                inputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            produto = null;
            return lstProd;

        }

    }
    public String ReadFile() throws FileNotFoundException, IOException{

        File file = context.getFilesDir();
        File textfile = new File(file + "/dados.txt");

        FileInputStream input = context.openFileInput("dados.txt");
        byte[] buffer = new byte[(int)textfile.length()];

        input.read(buffer);

        return new String(buffer);
    }
    public ArrayList<Produto> returnList() throws IOException {
        ArrayList<Produto> prod = new ArrayList<Produto>();
        StringTokenizer str;
        String texto = ReadFile();
        produto = new Produto();


            Log.d("Aviso: ", texto);
            str = new StringTokenizer(texto, ";");
            while (str.hasMoreTokens()) {

                produto.setCode(str.nextToken());
                produto.setDesc(str.nextToken());
                produto.setUnidMedida(str.nextToken());
                produto.setQtd(str.nextToken());
                lstProd.add(produto);

            }


        return prod;
    }



    public boolean isEmpty(List<Produto> produto) {
        return (produto == null) ? true : false;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Arquivo Page") // TODO: Define a title for the content shown.
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
}
