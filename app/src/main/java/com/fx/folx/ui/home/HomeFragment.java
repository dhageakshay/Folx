package com.fx.folx.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.fx.folx.BioActivity;
import com.fx.folx.R;
import com.fx.folx.StackAdapter;
import com.fx.folx.User;
import com.fx.folx.layoutmanager.OnItemSwiped;
import com.fx.folx.layoutmanager.SwipeableLayoutManager;
import com.fx.folx.layoutmanager.SwipeableTouchHelperCallback;
import com.fx.folx.layoutmanager.touchelper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

//    private HomeViewModel homeViewModel;

    RecyclerView recyclerView;

    private StackAdapter adapter;
    private List<User> users;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        User u1 = new User("Jane Doe", 24);
        User u2 = new User("Anna", 28);
        User u3 = new User("Mary",29);
        User u4 = new User("Juliet", 26);
        User u5 = new User("Jane Doe", 24);
        User u6 = new User("Anna", 28);
        User u7 = new User("Mary",29);
        User u8 = new User("Juliet", 26);

        users = new ArrayList<>();

        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        users.add(u5);
        users.add(u6);
        users.add(u7);
        users.add(u8);

        recyclerView = root.findViewById(R.id.recylcer_view);

        adapter = new StackAdapter(users);

        SwipeableTouchHelperCallback swipeableTouchHelperCallback = new SwipeableTouchHelperCallback(new OnItemSwiped() {
            @Override
            public void onItemSwiped() {
                adapter.removeTopItem();
            }

            @Override
            public void onItemSwipedLeft() {
                Log.e("SWIPE", "LEFT");
                //Intent to go to the bio
                Intent i = new Intent(getActivity(), BioActivity.class);
                startActivity(i);
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
                return ItemTouchHelper.LEFT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
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

        return root;
    }
}
