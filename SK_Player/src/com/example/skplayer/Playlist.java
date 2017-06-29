package com.example.skplayer;

import java.util.ArrayList;
import java.util.TreeMap;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.*;
import android.widget.ListView;

public class Playlist extends ListActivity {
	TreeMap<String, Long> songList =new TreeMap<String,Long>();

	ListView li;
	String rs="";
	public static String bp="back";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playlist);
		
			
		
		li =getListView();
		 ArrayAdapter<String> ad;
	        ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,MainActivity.songTitle);
	        setListAdapter(ad);
	        li.setOnItemClickListener(new OnItemClickListener(
	        		) {

						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						String temp =	MainActivity.path.get(position);
						//Toast.makeText(Playlist.this, temp, Toast.LENGTH_LONG).show();
						bp="list";
						MainActivity.i=position;
						Intent in =new Intent();
						in.putExtra("data", temp);
						setResult(0,in);
						finish();
						
						
							
						}
			});
	        
	        
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		moveTaskToBack(true);
		
		//onBackPressed();
		
	}
	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        moveTaskToBack(true);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}*/
	

}
