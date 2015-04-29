package openorm.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import openorm.myapplication.test.Artist;
import openorm.myapplication.test.Track;
import reply.ormlibrary.core.OrmManager;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Artist artist = new Artist();
        Track track = new Track();

        OrmManager ormManager = OrmManager.getInstance(getApplicationContext());
        ormManager.createTable(artist);
        ormManager.createTable(track);

        artist.setId(UUID.randomUUID().toString());
        artist.setName("Sottotono");
        artist.setSurname("");

        track.setId(UUID.randomUUID().toString());
        track.setTitle("La mia coccinella");
        track.setFkArtist(artist.getId());

        ormManager.insert(artist);
        ormManager.insert(track);

        track.setTitle("Titolo nuovo");
        ormManager.update(track);


    }


}
