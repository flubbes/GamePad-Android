package com.gamepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gamepad.lib.GPC;
import com.gamepad.lib.game.Lobby;

import java.util.ArrayList;

public class HostActivity extends Activity
{
    private ArrayList<String> games;
    private String texasPoker = "Poker";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        ListView lv = (ListView)findViewById(R.id.listView);
        games = new ArrayList<String>();
        games.add(texasPoker);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, games);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = games.get(i);
                if(s.equals(texasPoker))
                {
                    createPokerLobby();
                }
            }
        });

        Log.e("Game type", "Host");

    }

    private void createPokerLobby()
    {
        GPC.getHost().createLobby(texasPoker + " by Unknown");
        GPC.getHost().getLobby().setGameName(texasPoker);
        Intent intent = new Intent(this, LobbyActivity.class);
        startActivity(intent);
    }


}
