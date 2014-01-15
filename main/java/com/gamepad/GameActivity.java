package com.gamepad;

import android.app.Activity;
import android.os.Bundle;

import com.gamepad.lib.game.GameManager;

/**
 * Author: root
 * Date: 14.10.13.
 */
public class GameActivity extends Activity
{
    private static GameManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


    }

    public static GameManager getGameManager()
    {
        return manager;
    }
}
