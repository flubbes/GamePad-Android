package com.gamepad;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.gamepad.lib.forms.Point;
import com.gamepad.lib.forms.Size;
import com.gamepad.lib.forms.controls.Button;
import com.gamepad.lib.game.Game;

/**
 * Created by Fabian on 14.11.13.
 */
public class TestGame implements Game
{
    Button btnTest;

    @Override
    public void initialize() {
        btnTest = new Button(new Point(50, 50), new Size(200, 50));
    }

    @Override
    public void draw(Canvas c) {
        btnTest.draw(c);
    }

    @Override
    public void update() {
        btnTest.update();
    }

    @Override
    public void unload() {
    }
}
