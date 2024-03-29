package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.fx.folx.layoutmanager.OnItemSwiped;
import com.fx.folx.layoutmanager.SwipeableLayoutManager;
import com.fx.folx.layoutmanager.SwipeableTouchHelperCallback;
import com.fx.folx.layoutmanager.touchelper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private StackAdapter adapter;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        User u1 = new User("Jane Doe", 24);
        User u2 = new User("Anna", 28);
        User u3 = new User("Mary",29);
        User u4 = new User("Juliet", 26);

        users = new ArrayList<>();

        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);

        recyclerView = findViewById(R.id.recylcer_view);

        adapter = new StackAdapter(users);

        SwipeableTouchHelperCallback swipeableTouchHelperCallback = new SwipeableTouchHelperCallback(new OnItemSwiped() {
            @Override
            public void onItemSwiped() {
                adapter.removeTopItem();
            }

            @Override
            public void onItemSwipedLeft() {
                Log.e("SWIPE", "LEFT");
                //TODO: Render another card for the bio and make disable all swipes except swipe right to go back to the stack
            }

            @Override
            public void onItemSwipedRight() {
                Log.e("SWIPE", "RIGHT");
            }

            @Override
            public void onItemSwipedUp() {
                Log.e("SWIPE", "UP");
            }

            @Override
            public void onItemSwipedDown() {
                Log.e("SWIPE", "DOWN");

            }
        }){
            @Override
            public int getAllowedSwipeDirectionsMovementFlags(RecyclerView.ViewHolder viewHolder) {
                return ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
        };


        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeableTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new SwipeableLayoutManager().setAngle(10)
                .setAnimationDuratuion(450)
                .setMaxShowCount(3)
                .setScaleGap(0.1f)
                .setTransYGap(0));
        recyclerView.setAdapter(adapter);

    }
}
