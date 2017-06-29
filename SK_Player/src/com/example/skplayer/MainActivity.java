package com.example.skplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements  OnSeekBarChangeListener {
	MediaPlayer m1;
	TextView tv,td,tart;
	boolean rep=false,shuf=false;
	String nxt;
	//ImageView iv;
	ImageButton play,forward,backward,next,prev,repeat,shuffle;
	SeekBar s;
	public static ArrayList<String> songTitle=new ArrayList<String>();
	public static ArrayList<String> path=new ArrayList<String>();
	public static ArrayList<String> artista=new ArrayList<String>();
	//public static ArrayList<String> albumart=new ArrayList<String>();
	Button list;
	public static int i=0;
	public   void getSongList(){
		//query external audio
		ContentResolver musicResolver = getContentResolver();
		
		Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
		//iterate over results if valid
		if(musicCursor!=null && musicCursor.moveToFirst()){
			//get columns
			int titleColumn = musicCursor.getColumnIndex
					(android.provider.MediaStore.Audio.Media.TITLE);
			int idColumn = musicCursor.getColumnIndex
				(android.provider.MediaStore.Audio.Media._ID);
			int data = musicCursor.getColumnIndex
			(android.provider.MediaStore.Audio.Media.DATA);
			int artist= musicCursor.getColumnIndex
					(android.provider.MediaStore.Audio.Media.ARTIST);
		//	int albumart1= musicCursor.getColumnIndex
					//(android.provider.MediaStore.Audio.Albums.ALBUM_ART);
			//add songs to list
			do {
				long thisId = musicCursor.getLong(idColumn);
				String thisTitle = musicCursor.getString(titleColumn);
				String data1 =musicCursor.getString(data);
				String arter=musicCursor.getString(artist);
				//String album=musicCursor.getString(albumart1);
				//String thisArtist = musicCursor.getString(artistColumn);
				//songList.add(new Song(thisId, thisTitle, thisArtist));
				//songId.add(thisId);
				songTitle.add(thisTitle);
				path.add(data1);
				artista.add(arter);
			//	albumart.add(album);
				
			//songList.put(data1, thisId);
			 
			} 
			while (musicCursor.moveToNext());
		}
	}
	@Override
	protected void onResume(){
		
		super.onResume();
		
		
		Thread tt=new Thread()
		{ public void run()
			{
			while(true)
			{
				int putki=0;
				if(m1.isPlaying())
				{putki=m1.getCurrentPosition();
				s.setProgress(putki);
				}
				try {
					sleep(400);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			}
		};
		tt.start();
		}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play=(ImageButton)findViewById(R.id.i1);
        getSongList();
        
        forward=(ImageButton)findViewById(R.id.ifff);
        backward=(ImageButton)findViewById(R.id.ibbbb);
        next=(ImageButton)findViewById(R.id.next);
        shuffle=(ImageButton)findViewById(R.id.shuffle);
        repeat=(ImageButton)findViewById(R.id.repeat);
        prev=(ImageButton)findViewById(R.id.btnPrevious);
        //iv=(ImageButton)findViewById(R.id.imvia);
        s=(SeekBar)findViewById(R.id.sk1);
        tv=(TextView)findViewById(R.id.textView1);
        td=(TextView)findViewById(R.id.durationbar);
        tart=(TextView)findViewById(R.id.te1);
        list =(Button)findViewById(R.id.Playlist);
        
        //pehla gaana
		
        m1=MediaPlayer.create(this, R.raw.b);
        //m1=new MediaPlayer();
        s.setMax(m1.getDuration());
        s.setOnSeekBarChangeListener(this);
        list.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i =new Intent(MainActivity.this,Playlist.class);
				startActivityForResult(i,0);
				
				
			}
		});
 
        
     
        
    
        play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			if((!m1.isPlaying()) || (m1 ==null))
			{	//tv.setText(songTitle.get(i).toString());
				//tart.setText(artista.get(i).toString());
				//Drawable img = Drawable.createFromPath(albumart.get(i).toString());
				//iv.setImageDrawable(img);
				m1.start();
				
				
				
				
				play.setImageResource(R.drawable.img_btn_pause);
			}
			else
			{tv.setText(songTitle.get(i).toString());
				m1.pause();
				play.setImageResource(R.drawable.img_btn_play);
			}
				
			}
		});
        
        
        
        
        forward.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		long cp=m1.getCurrentPosition();
        		m1.seekTo((int) (cp+10000));
        		
        	}
        });
backward.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		long cp=m1.getCurrentPosition();
        		m1.seekTo((int) (cp-10000));
        		
        	}
        });
shuffle.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		if(shuf==true){shuf=false; shuffle.setImageResource(R.drawable.img_btn_shuffle); }
		else {shuf=true; shuffle.setImageResource(R.drawable.img_btn_shuffle_pressed);   }
		
	}
});


repeat.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(rep==true){rep=false; repeat.setImageResource(R.drawable.img_btn_repeat); }
		else {rep=true; repeat.setImageResource(R.drawable.img_btn_repeat_pressed);   }
	}
});




next.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		if(shuf==true){
			Random rand = new Random();
			i= rand.nextInt((path.size() - 1) - 0 + 1) + 0;
		}
		else{
		if(i==path.size()-1) i=0;
		else
		i =i+1;
		}		
		//Drawable img = Drawable.createFromPath(albumart.get(i).toString());
		//iv.setImageDrawable(img);
		tv.setText(songTitle.get(i).toString());
		tart.setText(artista.get(i).toString());
		nxt =path.get(i).toString();
		 m1.reset();
		
	
		//Toast.makeText(MainActivity.this,nxt, Toast.LENGTH_LONG).show();
		try {
			m1.setDataSource(nxt);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			m1.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m1.start();
		s.setProgress(0); 
		 s.setMax(m1.getDuration());
	
		
	}
});

//previous buttton
prev.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		if(shuf==true){
			Random rand = new Random();
			i= rand.nextInt((path.size() - 1) - 0 + 1) + 0;
		}
		else{
		if(i==0) i=path.size()-1;
		else
		i =i-1;
	} 
		
		//Drawable img = Drawable.createFromPath(albumart.get(i).toString());
		//iv.setImageDrawable(img);
		tv.setText(songTitle.get(i).toString());
		tart.setText(artista.get(i).toString());
		nxt =path.get(i).toString();
		 m1.reset();
		
	
		//Toast.makeText(MainActivity.this,nxt, Toast.LENGTH_LONG).show();
		try {
			m1.setDataSource(nxt);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			m1.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m1.start();
		s.setProgress(0); 
		 s.setMax(m1.getDuration());
	
		
	}
});


m1.setOnCompletionListener(new OnCompletionListener() {
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		m1.reset();
		if(rep==true)
		i=i;
		else if(shuf==true){
			Random rand = new Random();
			i= rand.nextInt((path.size() - 1) - 0 + 1) + 0;
		}
		else i=i+1;
		//Drawable img = Drawable.createFromPath(albumart.get(i).toString());
		//iv.setImageDrawable(img);
		tv.setText(songTitle.get(i).toString());
		tart.setText(artista.get(i).toString());
		String monk=path.get(i).toString();
		try {
			m1.setDataSource(monk);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	try {
		m1.prepare();
	} catch (IllegalStateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	m1.start();
	s.setProgress(0);
	s.setMax(m1.getDuration());
	
	}
});








/*Thread th =new Thread(){
	public void run()
	{
		while(m1.getCurrentPosition()<m1.getDuration())
		{
			try {
				if(m1.isPlaying())
				s.setProgress(m1.getCurrentPosition());
				
				//Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	
};
th.start();*/
        	
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    



	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if(fromUser)
			m1.seekTo(progress);
			int min =(m1.getCurrentPosition()/1000)/60;
			int sec =(m1.getCurrentPosition()/1000)%60;
			int totalmin=((m1.getDuration())/1000)/60;
			int totalsec=((m1.getDuration())/1000)%60;
			
			int mm,ss,xx;
			
			xx=m1.getCurrentPosition();
			xx=xx/1000;
			mm=xx/60;
			ss=xx%60;
			
			int t=m1.getDuration();
			int m=t/1000;
			int mmm=m/60;
			int sss=m%60;
			String msg=mmm+":"+sss;
			//tv.setText(msg);
			td.setText(""+mm+":"+ss+" / "+msg);
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bundle b =data.getExtras();
		String rs =b.getString("data");
		tv.setText(songTitle.get(i).toString());
		tart.setText(artista.get(i).toString());
		//Toast.makeText(this, "Recieved", Toast.LENGTH_LONG).show();
		m1.reset();
		try {
			m1.setDataSource(rs);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			m1.prepare();
			 
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m1.start();
		s.setProgress(0);
		
		play.setImageResource(R.drawable.img_btn_pause);
	}



	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	

	
	
}
