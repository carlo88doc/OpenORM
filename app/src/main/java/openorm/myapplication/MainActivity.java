package openorm.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import openorm.myapplication.core.OrmManager;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Test test = new Test();

        test.setDescription("Description 1");
        test.setId("ID_1");
        test.setFkTest("FK_1");

        Test test2 = new Test();
        test2.setFkTest("FK_2");
        test2.setId("ID_2");
        test2.setDescription("Description 2");


        List<Test> lst = new ArrayList<>();
        lst.add(test);
        lst.add(test2);

        OrmManager.register(lst);

    }


}
