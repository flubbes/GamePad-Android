package com.gamepad;

import android.app.Activity;
import android.os.Bundle;
<<<<<<< HEAD
=======

>>>>>>> c6c51505f6f6b791c9d418007a73500f3beec1e4
import com.gamepad.lib.game.GameManager;

/**
 * Author: root
 * Date: 14.10.13.
 */
<<<<<<< HEAD
public class GameActivity extends Activity
=======
public class GameActivity extends Activity 
>>>>>>> c6c51505f6f6b791c9d418007a73500f3beec1e4
{
    private static GameManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        manager = (GameManager)findViewById(R.id.game_manager_view);
        manager.setCurrentGame(new TestGame());

    }

    public static GameManager getGameManager()
    {
        return manager;
    }
}
