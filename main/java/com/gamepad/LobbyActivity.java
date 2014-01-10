package com.gamepad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.gamepad.lib.GPC;
import com.gamepad.lib.game.Lobby;
import com.gamepad.lib.game.LobbyPlayer;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gamepad.ExpandableListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

/**
 * Created by Fabian on 04.01.14.
 */
public class LobbyActivity extends MainActivity
{
    private ListView lvNetworkDebug;
    private ArrayList<String> clients;
    private String[] players;

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        /**super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        lvNetworkDebug = (ListView) findViewById(R.id.listView_clients);
        clients = new ArrayList<String>();

        for(LobbyPlayer player : GPC.getHost().getLobby().getPlayers())
        {
            clients.add(player.getIp().toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, clients);
        lvNetworkDebug.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        //Fake lobby maken
        Lobby lobby = new Lobby();
        LobbyPlayer sander = new LobbyPlayer();
        sander.setName("sander");
        lobby.setGameName("Fake poker lobby");
        lobby.setName("sander is een tokkie");
        lobby.addPlayer(sander);
        GPC.getJoin().addLobby(lobby);


        createGroupList();

       // createCollection();

        expListView = (ExpandableListView) findViewById(R.id.laptop_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, laptopCollection);
        expListView.setAdapter(expListAdapter);
        expListAdapter.notifyDataSetChanged();
        //setGroupIndicatorToRight();

        expListView.setOnChildClickListener(new OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
    }

    private void createGroupList()
    {
        groupList = new ArrayList<String>();

        for(Lobby lobby : GPC.getJoin().getLobbies())
        {
            String lobbyName = lobby.getName();
            groupList.add(lobbyName);
        }
        groupList.add("HP");
     }

    private void createCollection()
    {
        for(Lobby lobby : GPC.getJoin().getLobbies())
        {
            // preparing laptops collection(child)

            ArrayList<LobbyPlayer> test = new ArrayList<LobbyPlayer>();

            test = lobby.getPlayers();

            for(int p = 0; p < test.size();p++)
            {
                players[p]=(test.get(p).getName());
            }

            laptopCollection = new LinkedHashMap<String, List<String>>();

            for (String laptop : groupList)
            {
                if (laptop.equals(lobby.getName()))
                {
                    for(int p = 0; p < test.size();p++)
                    {
                        loadChild(players);
                    }
                }

               // else if (laptop.equals("Dell"))
                //    loadChild(dellModels);

                laptopCollection.put(laptop, childList);
            }

        }
    }

    private void loadChild(String[] laptopModels)
    {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
            childList.add(model);
    }

    private void setGroupIndicatorToRight()
    {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels)
    {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }




}
