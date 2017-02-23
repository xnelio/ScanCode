package com.example.nelioeasynet.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelio.easynet on 20/01/2017.
 */

public class ManagerBanco {

    private SQLiteDatabase db;
    private ConexaoBanco banco;
    private Cursor cursor;

    public ManagerBanco(Context context){

            banco = new ConexaoBanco(context);

    }

    public String insereDados(List<Produto> prod){
        ContentValues values;
        long resultado;
        values = new ContentValues();
        db = banco.getWritableDatabase();
        for (Produto k : prod){
            values.put("code", k.getCode());
            values.put("desc", k.getDesc());
            values.put("unidade_medida", k.getUnidMedida());
            values.put("quantidade", k.getQtd());
            resultado = db.insert(ConexaoBanco.TABELA, null, values);
            Log.d("Aviso: ", "code: " + k.getCode());
            if(resultado == -1){
                String msg = "Erro ao inserir registro: " + values.toString();
                return msg;
            }
        }
        String msg = "Dados Inseridos com sucesso.";
        db.close();
        return msg ;

    }

    public ArrayList<String> getAllItens(){
       db = banco.getReadableDatabase();
        String sql = "SELECT * FROM PRODUTO";
        this.cursor = db.rawQuery(sql, null);
        ArrayList<String> itens = new ArrayList<String>();

        while (cursor.moveToNext()){

                itens.add("Produto: " + cursor.getString(2) + "\nQuantidade: " + cursor.getString(4));


        }
        cursor.close();
        db.close();
        return itens;
    }
    public ArrayList<String> getAllID(){
        db = banco.getReadableDatabase();
        String sql = "SELECT * FROM PRODUTO";
        this.cursor = db.rawQuery(sql, null);
        ArrayList<String> itens = null;

        if(cursor != null && cursor.moveToFirst()){
            itens = new ArrayList<String>();

            do{
                itens.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itens;
    }




    public Produto getProduto(String codigo) {

        String[] param = new String[]{codigo};


        Cursor c = banco.getReadableDatabase()

                .rawQuery("SELECT * FROM " + ConexaoBanco.TABELA + "  WHERE code = ?" , param);
        Log.d("View", String.valueOf(param));

        Produto prod = new Produto();
        if(c.moveToNext()) {
            prod.setCode(c.getString(c.getColumnIndex("code")));
            prod.setDesc(c.getString(c.getColumnIndex("desc")));
            prod.setQtd(c.getString(c.getColumnIndex("quantidade")));
            prod.setUnidMedida(c.getString(c.getColumnIndex("unidade_medida")));

        }

        c.close();

        return prod;

    }
    public boolean bdIsEmpty(){
        db = banco.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + ConexaoBanco.TABELA, null);
        boolean result = false;
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt (0) == 0) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }


    public void limparBanco(String id){
        String sql = "DELETE FROM " + ConexaoBanco.TABELA
                + " where ID = " + id;
        db = banco.getReadableDatabase();
        db.execSQL(sql);
        db.close();
    }
}
