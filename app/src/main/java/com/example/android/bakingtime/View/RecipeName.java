package com.example.android.bakingtime.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.android.bakingtime.Model.BakingStep;
import com.example.android.bakingtime.R;

import java.io.Serializable;
import java.util.List;

public class RecipeName extends AppCompatActivity implements RecipeNameMasterListFragment.OnRecipeStepDescriptionClickListener{

    public static final String BAKING_RECIPE_STEPS_LIST = "baking_recipe_steps_list";
    public static final String LIST_INDEX = "list_index";

    private FragmentRecipePlayerView playerViewFragment;
    private List<BakingStep> bakingStep;
    private int currentListIndex;

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_name);

        // Determine if you're creating a two-pane or single-pane display
        if(findViewById(R.id.android_me_linear_layout) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case

            Log.e("DoublePane", "yes");
            mTwoPane = true;

            if(savedInstanceState == null) {

                // Create a new head BodyPartFragment
                playerViewFragment = new FragmentRecipePlayerView();
                bakingStep = (List<BakingStep>) getIntent().getSerializableExtra(BAKING_RECIPE_STEPS_LIST);

                //Log.e(TAG, "Position clicked: "+ currentListIndex);
                playerViewFragment.setRecipeStepsList(bakingStep);

                // Add the fragment to its container using a FragmentManager and a Transaction
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.player_view_and_description_body, playerViewFragment)
                        .commit();
            }
        }else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
            Log.e("DoublePane", "No");
        }
    }

    @Override
    public void onRecipeStepDescriptionSelected(int position, List<BakingStep> recipeSteps) {
        // Create a Toast that displays the position that was clicked
        //Toast.makeText(this, "Position clicked = " + recipeSteps.getDescription(), Toast.LENGTH_SHORT).show();

        // Handle the two-pane case and replace existing fragments right when a new image is selected from the master list
        if (mTwoPane) {
            // Create two=pane interaction

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


        }else {

            Bundle b = new Bundle();
            b.putInt(LIST_INDEX, position);
            b.putSerializable(BAKING_RECIPE_STEPS_LIST, (Serializable) recipeSteps);

            // Attach the Bundle to an intent
            final Intent intent = new Intent(this, RecipeStepDescriptionDetail.class);
            intent.putExtras(b);

            startActivity(intent);
        }
    }
}
