package com.gamepad.lib.game;

import android.os.Environment;
import java.io.File;
import java.lang.reflect.Method;
import dalvik.system.DexClassLoader;

public class GameLoader {

    @SuppressWarnings("unchecked")
    public void loadGame(String gameName)
    {
        try
        {
            String libPath = Environment.getExternalStorageDirectory() + "/GamePad/" + gameName + ".jar";
            File tmpDir = new File(Environment.getExternalStorageDirectory() + "/GamePad");

            DexClassLoader classLoader = new DexClassLoader(libPath, tmpDir.getAbsolutePath(), null, this.getClass().getClassLoader());
            Class<Object> classToLoad = (Class<Object>) classLoader.loadClass("Game");

            Object loadedGame = classToLoad.newInstance();
            Method initializeGame = classToLoad.getMethod("initialize");

            initializeGame.invoke(loadedGame);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}