package com.example.nelioeasynet.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by nelio.easynet on 18/01/2017.
 */

public class ConexaoBanco extends SQLiteOpenHelper {

    /**
     * O nome do arquivo de base de dados no sistema de arquivos
     */
    private static final String NOME_BD = "BDProduto.db";
    /**
     * A versão da base de dados que esta classe compreende.
     */
    private static final int VERSAO_BD = 1;
    private static final String LOG_TAG = "Produto";
    /**
     * Mantém rastreamento do contexto que nós podemos carregar SQL
     */
    private final Context contexto;
    protected static final String TABELA = "PRODUTO";
    protected static final String CODE = "code text";
    protected static final String DESC = "desc text";
    protected static final String UNIDADE_MEDIDA = "unidade_medida text";
    protected static final String QUANTIDADE = "quantidade text";
    protected static final String ID = "ID integer primary key autoincrement";

    public ConexaoBanco(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + " (" + ID + ", "
                + CODE + ", " + DESC + ", " +  UNIDADE_MEDIDA + ", "
                + QUANTIDADE + ")";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Atualizando a base de dados da versão " + oldVersion + " para " + newVersion + ", que destruirá todos os dados antigos");
        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
        onCreate(db);
    }

}

