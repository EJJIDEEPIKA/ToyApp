/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

// This activity is responsible for displaying the master list of all images
// Implement the MasterListFragment callback, OnImageClickListener
public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener{
        int headindex;
        int bodyindex;
        int legindex;
        public  boolean mtwoPane;
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.android_me_linear_layout)!=null){
            mtwoPane=true;
            Button b= (Button) findViewById(R.id.button);
            b.setVisibility(View.INVISIBLE);
            if(savedInstanceState == null) {

                // TODO (5) Retrieve list index values that were sent through an intent; use them to display the desired Android-Me body part image
                // Use setListindex(int index) to set the list index for all BodyPartFragments

                // Create a new head BodyPartFragment
                BodyPartFragment headFragment = new BodyPartFragment();

                // Set the list of image id's for the head fragment and set the position to the second image in the list
                headFragment.setImageIds(AndroidImageAssets.getHeads());
                headFragment.setListIndex(getIntent().getIntExtra("head",0));

                // Add the fragment to its container using a FragmentManager and a Transaction
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                // Create and display the body and leg BodyPartFragments

                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());
                bodyFragment.setListIndex(getIntent().getIntExtra("body",0));
                fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImageIds(AndroidImageAssets.getLegs());
                legFragment.setListIndex(getIntent().getIntExtra("leg",0));
                fragmentManager.beginTransaction()
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }

        }else {
            mtwoPane = false;
        }
    }

    // Define the behavior for onImageSelected
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
            int partNumber=position/12;
            int listindex=position-partNumber*12;
if(mtwoPane){
    BodyPartFragment fragment=new BodyPartFragment();
    switch (partNumber){
        case 0:
            fragment.setImageIds(AndroidImageAssets.getHeads());
            fragment.setListIndex(listindex);
            getSupportFragmentManager().beginTransaction().replace(R.id.head_container,fragment).commit();
        case 1:
            fragment.setImageIds(AndroidImageAssets.getBodies());
            fragment.setListIndex(listindex);
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment).commit();
        case 2:
            fragment.setImageIds(AndroidImageAssets.getLegs());
            fragment.setListIndex(listindex);
            getSupportFragmentManager().beginTransaction().replace(R.id.leg_container,fragment).commit();

    }
}
        // TODO (2) Based on where a user has clicked, store the selected list index for the head, body, and leg BodyPartFragments
          else
{
    switch (partNumber){
        case 0:
            headindex=listindex;
            break;
        case 1:
            bodyindex=listindex;
            break;
        case 2:
            legindex=listindex;
            break;
    }
}
        b= (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("head",headindex);
                bundle.putInt("body",bodyindex);
                bundle.putInt("leg",legindex);
                Intent i=new Intent(MainActivity.this,AndroidMeActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        // TODO (3) Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity

        // TODO (4) Get a reference to the "Next" button and launch the intent when this button is clicked

    }

}
