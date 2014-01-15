package com.gamepad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.gamepad.lib.GPC;
import com.gamepad.lib.update.AutoUpdater;

import java.util.concurrent.FutureTask;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class MainActivity extends Activity
{
    static Context appContext;
    static final AutoUpdater updater = new AutoUpdater();
    
    public static Context getContext()
    {
        return appContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        appContext = getApplicationContext();

        ImageView btnLib = (ImageView)findViewById(R.id.joinGame);
        btnLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGameLibrary();
            }
        });

        ImageView btnHost = (ImageView) findViewById(R.id.hostGame);
        btnHost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //turnCard();
                openHostScreen();
            }
        });

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                updater.getInventory();
                updater.getData();
                if( updater.hasUpdates() ) {
                    updater.doUpdate();
                }
                return null;
            }
        };


        task.execute();

        if(GameActivity.getGameManager() != null)
        {
            //GameActivity.getGameManager().setCurrentGame(new Poker());
        }
        GPC.InitGamePad();
        GPC.InitGameWakeLock();

    }

    /*public void turnCard() {
        ImageView btnLib = (ImageView) findViewById(R.id.joinGame);

        animate(btnLib).setDuration(1000).rotationYBy(180).x(0).y(0);
        Runnable theTask = new FutureTask<Object>(new Runnable() {
            public void run() {
                try {
                    ImageView btnLib = (ImageView) findViewById(R.id.joinGame);
                    Thread.sleep(500);
                    btnLib.setImageResource(R.drawable.btn_host);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, null);

        // start task in a new thread
        new Thread(theTask).start();
    }*/

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        GPC.ReleaseGameWakeLock();
    }

    private void openGameScreen()
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void openHostScreen()
    {
      Intent intent = new Intent(this, HostActivity.class);
      startActivity(intent);
    }

    private void openGameLibrary()
    {
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }

    private void openNetworkDebugActivity()
    {
        Intent intent = new Intent(this, NetworkDebugActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
