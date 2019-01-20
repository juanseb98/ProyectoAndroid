package com.example.proyectoandroid1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class AdaptadorBD {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_COMUNIDAD = "comunidad";
    public static final String KEY_LOGUEADO = "logueado";

    private static final String TAG = "AdaptadorBD";

    private static final String DATABASE_NAME = "login";
    private static final String DATABASE_TABLE = "comunidades";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table "+DATABASE_TABLE+
                    "("+KEY_ROWID+" integer primary key autoincrement, "
                    + KEY_COMUNIDAD +" text not null, "
                    + KEY_LOGUEADO +" int not null);";

    private final Context context;

    private BaseDatosHelper BDHelper;
    private SQLiteDatabase bsSql;
    private String[] todasColumnas =new String[] {KEY_ROWID, KEY_COMUNIDAD, KEY_LOGUEADO};

    public AdaptadorBD(Context ctx) {
        this.context = ctx;
        BDHelper = new BaseDatosHelper(context);
    }

    public AdaptadorBD open() throws SQLException {
        bsSql = BDHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        BDHelper.close();
    }

    public long insertarComunidad(String comunidad){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_COMUNIDAD, comunidad);
        initialValues.put(KEY_LOGUEADO, 1);
        return bsSql.insert(DATABASE_TABLE, null, initialValues);
    }
    public boolean loguearComunidad(String comunidad){
        ContentValues args = new ContentValues();
        args.put(KEY_COMUNIDAD, comunidad);
        args.put(KEY_LOGUEADO, 1);
        return bsSql.update(DATABASE_TABLE, args, KEY_COMUNIDAD + "=" + comunidad, null) > 0;
    }
    public boolean desloguearComunidad(String comunidad) {
        ContentValues args = new ContentValues();
        args.put(KEY_COMUNIDAD, comunidad);
        args.put(KEY_LOGUEADO, 0);
        return bsSql.update(DATABASE_TABLE, args, KEY_COMUNIDAD + "=" + comunidad, null) > 0;
    }
    public boolean borrarComunidad(String comunidad){
        return bsSql.delete(DATABASE_TABLE, KEY_COMUNIDAD + "=" + comunidad, null) > 0;
    }

    public Cursor getComunidades() {
        return bsSql.query(DATABASE_TABLE, todasColumnas,null,null,null,null,null);
    }


    public Cursor getComunidadLogueada() throws SQLException{

        Cursor mCursor = bsSql.query(true, DATABASE_TABLE, todasColumnas,
                KEY_LOGUEADO + "=" + 1,null,null,null,null,null);

        if (mCursor != null)  mCursor.moveToFirst();

        return mCursor;
    }

    public Cursor getTodasComunidades() {
        return bsSql.query(DATABASE_TABLE, todasColumnas,null,null,null,null,null);
    }



//**** CLASE PRIVADA ***/

    private static class BaseDatosHelper extends SQLiteOpenHelper {
        BaseDatosHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)	{
            db.execSQL(DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
            Log.w(TAG, "Actualizando base de datos de la versi�n " + oldVersion
                    + " a "
                    + newVersion + ", borraremos todos los datos");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
}
