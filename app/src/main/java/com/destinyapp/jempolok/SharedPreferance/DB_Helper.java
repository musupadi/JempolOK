package com.destinyapp.jempolok.SharedPreferance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.destinyapp.jempolok.Model.DataModel;

import java.util.LinkedList;
import java.util.List;

public class DB_Helper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "jempol.db";
    private static final int DATABASE_VERSION = 1;
    //Account
    public static final String TABLE_NAME_ACCOUNT = "account";
    public static final String COLUMN_EMAIL = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_TOKEN = "token";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_FOTO = "foto";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_STATUS = "status";
    //Teknisi
    public static final String TABLE_NAME_TEKNISI = "teknisi";
    public static final String ID_TEKNISI = "id_teknisi";
    //Kategori
    public static final String TABLE_NAME_KATEGORI = "kategori";
    public static final String ID_KATEGORI = "id_kategori";
    public static final String COLUMN_KATEGORI = "kategori";
    //Kegiatan
    public static final String TABLE_NAME_KEGIATAN = "kegiatan";
    public static final String ID_KEGIATAN = "id_kegiatan";
    public static final String COLUMN_KEGIATAN = "kegiatan";
    public DB_Helper(Context context){super(
            context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME_ACCOUNT+" (" +
                COLUMN_EMAIL+" TEXT NOT NULL, "+
                COLUMN_PASSWORD+" TEXT NOT NULL, "+
                COLUMN_TOKEN+" TEXT NOT NULL, "+
                COLUMN_NAMA+" TEXT NOT NULL, "+
                COLUMN_FOTO+" TEXT NOT NULL, "+
                COLUMN_LEVEL+" TEXT NOT NULL, "+
                COLUMN_STATUS+" TEXT NOT NULL);"
        );
        db.execSQL("CREATE TABLE "+TABLE_NAME_TEKNISI+" (" +
                ID_TEKNISI+" TEXT NOT NULL);"
        );
        db.execSQL("CREATE TABLE "+TABLE_NAME_KATEGORI+" (" +
                ID_KATEGORI+" TEXT NOT NULL, "+
                COLUMN_KATEGORI+" TEXT NOT NULL);"
        );
        db.execSQL("CREATE TABLE "+TABLE_NAME_KEGIATAN+" (" +
                ID_KEGIATAN+" TEXT NOT NULL, "+
                COLUMN_KEGIATAN+" TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TEKNISI);
        this.onCreate(db);
    }
    //SAVE
    public void saveUser(String username,String password,String token,String nama,String foto,String level,String status){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_TOKEN, token);
        values.put(COLUMN_NAMA,nama);
        values.put(COLUMN_FOTO,foto);
        values.put(COLUMN_LEVEL,level);
        values.put(COLUMN_STATUS,status);
        db.insert(TABLE_NAME_ACCOUNT,null,values);
        db.close();
    }
    public void saveIDTeknisi(String id){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_TEKNISI, id);
        db.insert(TABLE_NAME_TEKNISI,null,values);
        db.close();
    }
    public void saveIDKategori(String id,String kategori){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_KATEGORI, id);
        values.put(COLUMN_KATEGORI,kategori);
        db.insert(TABLE_NAME_KATEGORI,null,values);
        db.close();
    }
    public void saveIDKegiatan(String id,String kegiatan){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_KEGIATAN, id);
        values.put(COLUMN_KEGIATAN, kegiatan);
        db.insert(TABLE_NAME_KEGIATAN,null,values);
        db.close();
    }
    //CHECKER
    public Cursor checkUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM "+TABLE_NAME_ACCOUNT;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
    public  Cursor checkTeknisi(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM "+TABLE_NAME_TEKNISI;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
    public  Cursor checkKategori(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM "+TABLE_NAME_KATEGORI;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
    public  Cursor checkKegiatan(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM "+TABLE_NAME_KEGIATAN;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
    //RESET OR DELETE
    public void resetTeknisi(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_TEKNISI+"");
    }
    public void reseKategori(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_KATEGORI+"");
    }
    public void resetKegiatan(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_KEGIATAN+"");
    }
    public void deleteTeknisi(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_TEKNISI+" WHERE "+ID_TEKNISI+" = "+id+"");
    }
    public void deleteKategori(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_KATEGORI+" WHERE "+ID_KATEGORI+" = "+id+"");
    }
    public void deleteKegiatan(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_KEGIATAN+" WHERE "+ID_KEGIATAN+" = "+id+"");
    }
    public void Logout(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_ACCOUNT+"");
    }

    //LISTING
    public List<DataModel> kategoriList() {
        String query = "SELECT  * FROM " + TABLE_NAME_KATEGORI;

        List<DataModel> list = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DataModel dm;

        if (cursor.moveToFirst()) {
            do {
                dm = new DataModel();
                dm.setNama_kategori(cursor.getString(cursor.getColumnIndex(COLUMN_KATEGORI)));
                dm.setId_kategori(cursor.getInt(cursor.getColumnIndex(ID_KATEGORI)));
                list.add(dm);
            } while (cursor.moveToNext());
        }
        return list;
    }
    public List<DataModel> kegiatanList() {
        String query = "SELECT  * FROM " + TABLE_NAME_KEGIATAN;

        List<DataModel> list = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DataModel dm;

        if (cursor.moveToFirst()) {
            do {
                dm = new DataModel();
                dm.setNama_kegiatan(cursor.getString(cursor.getColumnIndex(COLUMN_KEGIATAN)));
                dm.setId_kegiatan(cursor.getInt(cursor.getColumnIndex(ID_KEGIATAN)));
                list.add(dm);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
