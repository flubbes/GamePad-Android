package com.gamepad.lib.game;

import com.gamepad.lib.GPC;
import com.gamepad.lib.net.Packet;
import com.gamepad.lib.net.PacketEvent;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Fabian on 16.12.13.
 */
public class Join implements PacketEvent
{
    private ArrayList<Lobby> lobbies;
    private Lobby curLobby;

    public Join()
    {
        lobbies = new ArrayList<Lobby>();
        GPC.getNetwork().addPacketEventListener(this);
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public void addLobby(Lobby lobby)
    {
        if(!lobbyExists(lobby))
        {
            lobbies.add(lobby);
        }
    }

    public Lobby getLobbyByName(String name)
    {
        for(Lobby lob : lobbies)
        {
            if(lob.getName().equals(name))
            {
                return lob;
            }
        }
        return null;
    }

    public Lobby getCurrentLobby()
    {
        return curLobby;
    }

    public void setCurrentLobby(Lobby lob)
    {
        this.curLobby = lob;
    }

    public Boolean lobbyExists(Lobby lobby)
    {
        Iterator<Lobby> pIt = lobbies.iterator();
        while(pIt.hasNext())
        {
            Lobby cur = pIt.next();
            if(cur.getHostIp().equals(lobby.getHostIp()))
            {
                return true;
            }
        }
        return false;
    }

    public void SearchForHosts()
    {
        GPC.getNetwork().sendPingBroadcast();
    }

    @Override
    public void newPacket(Packet p) {
        if(p.getMessage().startsWith("pong")){
            String lobbyName = p.getMessage().replace("pong ", "");
            Lobby lob = new Lobby();
            lob.setName(lobbyName);
            addLobby(lob);
        }
    }
}
