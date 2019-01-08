package com.example.satayam.mypetplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class VocabListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab_list);

        // Create an ArrayList of VocabList objects
        ArrayList<VocabList> vocablists = new ArrayList<VocabList>();
        vocablists.add(new VocabList("Accrue", "1.6", R.drawable.donut));
        vocablists.add(new VocabList("Adamant", "2.0-2.1", R.drawable.eclair));
        vocablists.add(new VocabList("Adhere", "2.2-2.2.3", R.drawable.froyo));
        vocablists.add(new VocabList("Adulterate", "2.3-2.3.7", R.drawable.gingerbread));
        vocablists.add(new VocabList("Aesthetic", "3.0-3.2.6", R.drawable.honeycomb));
        vocablists.add(new VocabList("Affinity", "4.0-4.0.4", R.drawable.icecream));
        vocablists.add(new VocabList("Aggregate", "4.1-4.3.1", R.drawable.jellybean));
        vocablists.add(new VocabList("Aloof", "4.4-4.4.4", R.drawable.kitkat));
        vocablists.add(new VocabList("Amalgamate", "5.0-5.1.1", R.drawable.lollipop));
        vocablists.add(new VocabList("Amenity", "6.0-6.0.1", R.drawable.marshmallow));

        // Create an {@link VocabListAdapter}, whose data source is a list of
        // {@link VocabList}s. The adapter knows how to create list item views for each item
        // in the list.
        VocabListAdapter flavorAdapter = new VocabListAdapter(this, vocablists);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.vocab_list_view);
        listView.setAdapter(flavorAdapter);
    }
}
