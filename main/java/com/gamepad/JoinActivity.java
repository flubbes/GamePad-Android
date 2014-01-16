package com.gamepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.gamepad.lib.GPC;
import com.gamepad.lib.game.Lobby;
import com.gamepad.lib.game.LobbyJoinedEvent;
import com.gamepad.lib.game.LobbyPlayer;
import com.gamepad.lib.game.LobbyUpdateEvent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Fabian on 07.01.14.
 */
public class JoinActivity extends Activity
{
    ExpandableListAdapter elvLobbiesAdapter;
    ExpandableListView elvLobbies;
    LinkedHashMap<String, List<String>> lobbies = new LinkedHashMap<String, List<String>>();
    String joinedLobby;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Lobby lobbie = new Lobby();
        lobbie.setName("Tokkier");
        lobbie.setGameName("Gaypad");
        LobbyPlayer player1 = new LobbyPlayer();
        player1.setName("Jeroen");
        lobbie.addPlayer(player1);
        GPC.getJoin().addLobby(lobbie);

        Button btnJoin = (Button)findViewById(R.id.refresh);

        btnJoin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openLobby();
            }
        });

        elvLobbies = (ExpandableListView)findViewById(R.id.elv_lobbies);





        updateLobbyList();

        GPC.getJoin().addLobbyUpdateEventListener(new LobbyUpdateEvent() {
            @Override
            public void onLobbyUpdate() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateLobbyList();
                    }
                });
            }
        });

        elvLobbies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickedOnListView(i);
            }
        });



        elvLobbies.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) elvLobbiesAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });

        GPC.getJoin().addLobbyJoinedEventListener(new LobbyJoinedEvent() {
            @Override
            public void lobbyJoined() {
                joinedLobby();
            }
        });
        GPC.setJoinMode();

    }

    private void openLobby()
    {
        Intent intent = new Intent(this, LobbyActivity.class);
        startActivity(intent);
    }

    private void clickedOnListView(int i)
    {
        Lobby toJoin = GPC.getJoin().getLobbies().get(i);
        try
        {
            GPC.getJoin().requestJoin(toJoin);

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void joinedLobby()
    {
    }

    private void updateLobbyList()
    {
        lobbies.clear();
        for(Lobby lobby : GPC.getJoin().getLobbies())
        {
            ArrayList<LobbyPlayer> lobbyPlayers = lobby.getPlayers();
            ArrayList<String> playersNames = new ArrayList<String>();
            String temp = "";
            for(int p = 0; p < lobbyPlayers.size();p++)
            {
                playersNames.add(lobbyPlayers.get(p).getName());
            }
            lobbies.put(lobby.getName() + " " + lobby.getGameName(), playersNames);
        }
        elvLobbiesAdapter = new ExpandableListAdapter(this, lobbies);
        elvLobbies.setAdapter(elvLobbiesAdapter);

    }


}
