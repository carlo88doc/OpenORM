package openorm.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import openorm.myapplication.core.OrmManager;
import openorm.myapplication.test.Artist;
import openorm.myapplication.test.Track;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Artist artist = new Artist();
        Track track = new Track();

        artist.setId(UUID.randomUUID().toString());
        artist.setName("Sottotono");
        artist.setSurname("");

        track.setId(UUID.randomUUID().toString());
        track.setTitle("La mia coccinella");
        track.setFkArtist(artist.getId());

        OrmManager.register(artist, getApplicationContext());
        OrmManager.register(track, getApplicationContext());

    }


}
