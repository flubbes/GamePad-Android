package com.gamepad.lib.update;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.gamepad.MainActivity;
import com.gamepad.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.util.HashMap;

public class AutoUpdater {

    //the url to the info file (saved as a json file)
    public static final String INFO_FILE = "https://dl.dropboxusercontent.com/u/19280458/updateInfo.json";

    //the url used for an internet check
    public static final String INTERNET_CHECK_URL = "http://google.de";

    private ArrayList<AvailableGame> games;
    private HashMap<String, Double> inventory;
    private ArrayList<AvailableGame> updateList;

    //initialized a new instance of the autoupdater class
    public AutoUpdater()
    {
        games = new ArrayList<AvailableGame>();
        inventory = new HashMap<String, Double>();
        updateList = new ArrayList<AvailableGame>();
    }

    //downloads via the http protocol the page source to a string
    private static String downloadHttp(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(15 * 1000);
        connection.setUseCaches(false);
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while((line = reader.readLine()) != null){
            stringBuilder.append(line + "\n");
        }
        return stringBuilder.toString();
    }

    // Converts a json array to an arraylist with json objects
    private ArrayList<JSONObject> getArrayListFromJsonArray(JSONArray jArr)
    {
        ArrayList<JSONObject> result = new ArrayList<JSONObject>();
        for(int i = 0;i<jArr.length(); i++)
        {
            try
            {
                result.add(jArr.getJSONObject(i));
            }
            catch(JSONException ex)
            {

            }
        }
        return result;
    }

    //gets all the games that are currently in the game list
    public ArrayList<AvailableGame> getGames()
    {
        return games;
    }

    //get the data for the auto updater class including all games for the core
    public void getData()
    {
        try{
            //download the content from the update info file
            String data = downloadHttp(new URL(INFO_FILE));

            //parse the json code
            JSONObject json = new JSONObject(data);

            //get the gamelist
            JSONArray jArr = json.getJSONArray("GameList");

            //convert the json array to an arraylist with json objects
            ArrayList<JSONObject> jObjects = getArrayListFromJsonArray(jArr);

            //walk through the elements and add new available games to the list in this class
            for(JSONObject jObject : jObjects)
            {
                String name = jObject.getString("Name");
                String versionName = jObject.getString("VersionName");
                int version = jObject.getInt("Version");
                String downloadUrl = jObject.getString("DownloadUrl");
                AvailableGame theGame = new AvailableGame(name, versionName, version, downloadUrl);

                games.add(theGame);
            }


        }catch(JSONException e){
            Log.e("AutoUpdate", "Error with JSON", e);
        }
        catch(IOException e){
            Log.e("AutoUpdate", "Error with the download", e);
        }
    }

    public void getInventory()
    {
        Context context = MainActivity.getContext();
        XmlResourceParser xrp = context.getResources().getXml(R.xml.inventory);

        try {
            int eventType = xrp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && xrp.getName().equals("game")) {
                    String name = xrp.getAttributeValue(0);
                    String version = xrp.getAttributeValue(1);

                    //Log.e("Game info", attrValue + ", version " + intValue);
                    inventory.put(name, Double.parseDouble(version));
                }
                eventType = xrp.next();
            }
        }
        catch (XmlPullParserException e) { }
        catch (IOException e) { }
        catch (NullPointerException e) { }
    }

    public boolean hasUpdates()
    {
        for(int i = 0; i < games.size(); i++) {

            AvailableGame game = games.get(i);
            String gameName = game.getName();

            Log.e("Game check", "Name: " + gameName);
            Log.e("Game check", "Server version: " + game.getVersion());

            if(inventory.get(gameName) != null) {
                Log.e("Game check", "Local version: " + inventory.get(gameName));

                if(game.getVersion() > inventory.get(gameName)) {
                    Log.e("Game check", "Update available");
                    updateList.add(game);
                }
                else if (game.getVersion() <= inventory.get(gameName)) {
                    Log.e("Game check", "Up to date");
                }

            } else {
                Log.e("Game check", "Local version: none");
                Log.e("Game check", "Update available");
                updateList.add(game);
            }
        }

        if(updateList.size() > 0) {
            return true;
        }
        return false;
    }

    //Checks if there is an internet connection
    public Boolean hasInternetConnection()
    {
        try
        {
            downloadHttp(new URL(INTERNET_CHECK_URL));
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
}
