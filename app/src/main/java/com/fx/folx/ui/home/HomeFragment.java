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
import com.fx.folx.api.Restaurant;
import com.fx.folx.layoutmanager.OnItemSwiped;
import com.fx.folx.layoutmanager.SwipeableLayoutManager;
import com.fx.folx.layoutmanager.SwipeableTouchHelperCallback;
import com.fx.folx.layoutmanager.touchelper.ItemTouchHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG ="HOMEFRAGMENT";

    RecyclerView recyclerView;
    private TextView loading;
    private StackAdapter adapter;
    private List<User> users;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        loading = root.findViewById(R.id.loadingFeedText);
        users = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        ref=database.getReference();
        ref.child("accounts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String userkey = ds.getKey();
                    User u = new User();
                    for(DataSnapshot s: ds.getChildren()){
                        String key = s.getKey();
                        switch (key){
                            case "company":{u.setCompany(s.getValue(String.class)); break;}
                            case "viewLastSeen":{u.setViewLastSeen(s.getValue(Boolean.class));break;}
                            case "dob":{u.setDob(s.getValue(Date.class));break;}
                            case "email":{u.setEmail(s.getValue(String.class)); break;}
                            case "favRestaurantList":{
                                GenericTypeIndicator<ArrayList<Restaurant>> t = new GenericTypeIndicator<ArrayList<Restaurant>>(){};
                                ArrayList<Restaurant> favRest = s.getValue(t);
                                u.setFavRestaurantList(favRest);
                                Log.d(TAG,"Fav Restaurant fetched");
                                break;
                            }
                            case "gender": {u.setGender(s.getValue(String.class));break;}
                            case "maxAgeRange":{u.setMaxAgeRange(s.getValue(Integer.class));break;}
                            case "maxDistance":{u.setMaxDistance(s.getValue(Integer.class));break;}
                            case "minAgeRange":{u.setMinAgeRange(s.getValue(Integer.class));break;}
                            case "nickName":{u.setNickName(s.getValue(String.class));break;}
                            case "phone":{u.setPhone(s.getValue(String.class));break;}
                            case "profession":{u.setProfession(s.getValue(String.class));break;}
                            case "imageList":{
                                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>(){};
                                ArrayList<String> imgList = s.getValue(t);
                                u.setImageList(imgList);
                                Log.d(TAG,"Image List fetched");
                                break;
                            }
                            case "sexualOrientation": {
                                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>(){};
                                ArrayList<String> sexOrient = s.getValue(t);
                                u.setSexualOrientation(sexOrient);
                                Log.d(TAG,"Sexual Orientation fetched");
                                break;
                            }
                            case "university": {u.setUniversity(s.getValue(String.class));break;}
                            case "name": {u.setName(s.getValue(String.class));break;}
                            case "story":{u.setStory(s.getValue(String.class));break;}
                        }
                    }
                    if(!userkey.equals(mUser.getUid())) {
                        users.add(u);
                    }
                }
                adapter = new StackAdapter(users);
                Log.d(TAG,"Feed Size "+users.size());

                recyclerView = root.findViewById(R.id.recylcer_view);



                SwipeableTouchHelperCallback swipeableTouchHelperCallback = new SwipeableTouchHelperCallback(new OnItemSwiped() {
                    @Override
                    public void onItemSwiped() {
                        if(ItemTouchHelper.UP == 1 | ItemTouchHelper.DOWN == 1<<1){     // ItemTouchHelper.UP and ItemTouchHelper.Down are initialized to respective value in com.fx.folx.layoutmanager.touchelper.ItemTouchHelper
                            adapter.removeTopItem();
                        }

                        //TODO: On swiping left the card gets removed from the feed. It should not be removed from the user's feed.

                        //TODO: If sexual orientation is null show all kinds of people to the user
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
                loading.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


     /*   User u1 = new User("Jane Doe", 24);
        User u2 = new User("Anna", 28);
        User u3 = new User("Mary",29);
        User u4 = new User("Juliet", 26);
        User u5 = new User("Jane Doe", 24);
        User u6 = new User("Anna", 28);
        User u7 = new User("Mary",29);
        User u8 = new User("Juliet", 26);


        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        users.add(u5);
        users.add(u6);
        users.add(u7);
        users.add(u8);
*/



        return root;
    }
}
