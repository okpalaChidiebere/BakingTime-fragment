package com.example.android.bakingtime.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.android.bakingtime.Model.BakingStep;
import com.example.android.bakingtime.R;

import java.util.List;

public class RecipeStepDescriptionDetail extends AppCompatActivity {

    private static final String TAG = "StepDescriptionDetail";

    public static final String BAKING_RECIPE_STEPS_LIST = "baking_recipe_steps_list";
    public static final String LIST_INDEX = "list_index";

    private FragmentRecipePlayerView playerViewFragment;
    private List<BakingStep> bakingStep;
    private int currentListIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_description_detail);

        if (savedInstanceState == null) {
            // Create a new head BodyPartFragment
            playerViewFragment = new FragmentRecipePlayerView();
            currentListIndex = getIntent().getIntExtra(LIST_INDEX, 0);
            bakingStep = (List<BakingStep>) getIntent().getSerializableExtra(BAKING_RECIPE_STEPS_LIST);

            //Log.e(TAG, "Position clicked: "+ currentListIndex);
            playerViewFragment.setRecipeStepsList(bakingStep);
            playerViewFragment.setListIndex(currentListIndex);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.player_view_and_description_body, playerViewFragment)
                    .commit();
        }

        // The "Next" button launches a new fragment with next steps
        Button nextButton = (Button) findViewById(R.id.tv_next_step);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentListIndex < bakingStep.size()) {

                    playerViewFragment = new FragmentRecipePlayerView();
                    playerViewFragment.setRecipeStepsList(bakingStep);

                    Log.e("Index before add", ""+currentListIndex);

                    /*int nextIndex;
                    if(currentListIndex < 0)
                        nextIndex = 0;
                    else
                        nextIndex = currentListIndex++;*/

                    int nextIndex = currentListIndex++;

                    Log.e("Index after increment", ""+nextIndex);

                    playerViewFragment.setListIndex(nextIndex);

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.player_view_and_description_body, playerViewFragment)
                            .commit();
                }
            }
        });

        // The "previous" button launches a new AndroidMeActivity
        Button previousButton = (Button) findViewById(R.id.tv_previous_step);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentListIndex >= 0) {

                    playerViewFragment = new FragmentRecipePlayerView();
                    playerViewFragment.setRecipeStepsList(bakingStep);
                    Log.e("Index before subtract", ""+currentListIndex);
                    int previousIndex = currentListIndex--;
                    Log.e("Index after subtract", ""+previousIndex);
                    playerViewFragment.setListIndex(previousIndex);

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.player_view_and_description_body, playerViewFragment)
                            .commit();
                }
            }
        });

    }



}
