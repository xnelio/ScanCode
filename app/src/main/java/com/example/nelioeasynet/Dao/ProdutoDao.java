package com.example.nelioeasynet.Dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.nelioeasynet.model.ConexaoBanco;
import com.example.nelioeasynet.model.Produto;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelio.easynet on 01/02/2017.
 */

public class ProdutoDao extends SQLiteOpenHelper {

    /**
     * O nome do arquivo de base de dados no sistema de arquivos
     */
    private static final String DATABASE = "BDProduto.db";
    /**
     * A versão da base de dados que esta classe compreende.
     */
    private static final int VERSION = 1;
    private static final String LOG_TAG = "Produto";
    /**
     * Mantém rastreamento do contexto que nós podemos carregar SQL
     */

    private static ConexaoBanco dbHelper = null;
    private static final String TABLE = "PRODUTO";


    public ProdutoDao(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE +
                "(ID INTEGER PRIMARY KEY autoincrement, " +
                "code TEXT NOT NULL, " +
                "desc TEXT, " +
                "unidade_medida TEXT, " +
                "quantidade TEXT, " +
                "Lido TEXT default nao );";

        db.execSQL(sql);

    }
    public void insert(Produto produto) {
        ContentValues values = new ContentValues();
        values.put("code", produto.getCode());
        values.put("desc", produto.getDesc());
        values.put("unidade_medida", produto.getUnidMedida());
        values.put("quantidade", produto.getQtd());


        getWritableDatabase().insert(TABLE, null, values);
    }
    public void insertList(List<Produto> produto) {

        for(int i=0; i < produto.size(); i++){
            insert(produto.get(i));
        }

    }
    public void update(Produto produto) {
        ContentValues values = new ContentValues();
        values.put("code", produto.getCode());
        values.put("desc", produto.getDesc());
        values.put("unidade_medida", produto.getUnidMedida());
        values.put("quantidade", produto.getQtd());


        String [] params = {produto.getID().toString()};
        getWritableDatabase().update(TABLE, values, "id=?", params);
    }
    public List<Produto> list() {
        List<Produto> listaProduto = new ArrayList<Produto>();

        Cursor c = getReadableDatabase()
                .rawQuery("SELECT * FROM " + TABLE + " ORDER BY DATA ASC",null);

        while (c.moveToNext()) {
            Produto produto = new Produto();
            produto.setID(c.getInt(c.getColumnIndex("id")));
            produto.setCode(c.getString(c.getColumnIndex("code")));
            produto.setDesc(c.getString(c.getColumnIndex("desc")));
            produto.setUnidMedida(c.getString(c.getColumnIndex("unidade_medida")));
            produto.setQtd(c.getString(c.getColumnIndex("quantidade")));

            listaProduto.add(produto);
        }
        c.close();
        return listaProduto;
    }

    public void delete(Produto produto) {
        String [] params = {produto.getID().toString()};
        getWritableDatabase().delete(TABLE, "id=?", params);
    }
    public Produto buscaItem(String codigo) {

        String[] param = new String[]{codigo};


        Cursor c = dbHelper.getReadableDatabase()

                .rawQuery("SELECT * FROM " + TABLE + "  WHERE code = ?" , param);
        Log.d("View", String.valueOf(param));

        Produto prod = new Produto();
        if(c.moveToNext()) {

            prod.setDesc(c.getString(c.getColumnIndex("desc")));
            prod.setQtd(c.getString(c.getColumnIndex("quantidade")));
            prod.setUnidMedida(c.getString(c.getColumnIndex("unidade_medida")));

        }

        c.close();

        return prod;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

