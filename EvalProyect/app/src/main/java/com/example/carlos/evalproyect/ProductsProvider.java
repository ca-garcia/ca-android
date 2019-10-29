package com.example.carlos.evalproyect;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

public class ProductsProvider extends ContentProvider {
	
	//Definicion del CONTENT_URI
	private static final String uri = "content://com.example.carlos.evalproyect/products";
	
	public static final Uri CONTENT_URI = Uri.parse(uri);
	
	//Necesario para UriMatcher
	private static final int PRODUCTS = 1;
	private static final int PRODUCTS_ID = 2;
	private static final UriMatcher uriMatcher;
	
	//Clase interna para declarar las constantes de columna
	public static final class Products implements BaseColumns
	{
		private Products() {}
	
		//Nombres de columnas
		public static final String COL_NAME = "name";
		public static final String COL_MODEL = "model";
		public static final String COL_ITEM = "item";
	}
	
	//Base de datos
	private ProductsSQLiteHelper proddbh;
	private static final String DB_NAME = "dbProducts";
	private static final int DB_VERSION = 1;
	private static final String TABLE_PRODUCTS= "Products";
	
	//Inicializamos el UriMatcher
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.example.carlos.evalproyect", "products", PRODUCTS);
		uriMatcher.addURI("com.example.carlos.evalproyect", "products/#", PRODUCTS_ID);
	}
	
	@Override
	public boolean onCreate() {
		
		proddbh = new ProductsSQLiteHelper(getContext(), DB_NAME, null, DB_VERSION);
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
		
		//Si es una consulta a un ID concreto construimos el WHERE
		String where = selection;
		if(uriMatcher.match(uri) == PRODUCTS_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

		SQLiteDatabase db = proddbh.getWritableDatabase();
		
		Cursor c = db.query(TABLE_PRODUCTS, projection, where,
				            selectionArgs, null, null, sortOrder);
		
		return c;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		long regId = 1; 
		
		SQLiteDatabase db = proddbh.getWritableDatabase();
		
		regId = db.insert(TABLE_PRODUCTS, null, values);
		
		Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
		
		return newUri;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, 
			          String selection, String[] selectionArgs) {
		
		int cont;
		
		//Si es una consulta a un ID concreto construimos el WHERE
		String where = selection;
		if(uriMatcher.match(uri) == PRODUCTS_ID){
            where = "_id=" + uri.getLastPathSegment();
        }
		
		SQLiteDatabase db = proddbh.getWritableDatabase();
		
		cont = db.update(TABLE_PRODUCTS, values, where, selectionArgs);
		
		return cont;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		int cont;
		
		//Si es una consulta a un ID concreto construimos el WHERE
		String where = selection;
		if(uriMatcher.match(uri) == PRODUCTS_ID){
            where = "_id=" + uri.getLastPathSegment();
        }
		
		SQLiteDatabase db = proddbh.getWritableDatabase();
		
		cont = db.delete(TABLE_PRODUCTS, where, selectionArgs);
		
		return cont;
	}
	
	@Override
	public String getType(Uri uri) {
		
		int match = uriMatcher.match(uri);
		
		switch (match) 
		{
			case PRODUCTS:
				return "vnd.android.cursor.dir/vnd.carlos.products";
			case PRODUCTS_ID:
				return "vnd.android.cursor.item/vnd.carlos.products";
			default: 
				return null;
		}
	}
}
