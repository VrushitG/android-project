package com.example.try4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.try4.databinding.ActivityMainBinding;

public class home_page extends AppCompatActivity {
    //initialize variable
    MeowBottomNavigation bottomNavigation;
//    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

//        binding.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CarFragment carFragment = new CarFragment();
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.replace, carFragment);
//                fragmentTransaction.commit();
//            }
//        });

        getSupportFragmentManager().beginTransaction().add(R.id.fram_layout,new CarFragment()).commit();

        setContentView(R.layout.activity_home_page);
        //fragment binding
        //assign var.
        bottomNavigation = findViewById(R.id.bottom_nav);

        // add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_car));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_p));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_more));
//
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(fragment_container,new Fragment());

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //intitalize fragment
                Fragment fragment = null;
                //check condition
                switch (item.getId()){
                    case 1:
                        //when id is 1
                        //initializ car frag
                        fragment = new CarFragment();
                        break;
                    case 2:
                        //when id is 2
                        //initializ p frag
                        fragment = new PFragment();
                        break;
                    case 3:
                        //when id is 3
                        //initializ more frag
                        fragment = new MoreFragment();
                        break;
                }
                //load fragment
                loadFragment(fragment);
            }
        });

        //set notification count
//        bottomNavigation.setCount(1,"10");
        //set hoem fragment inititally selected
        bottomNavigation.show(2,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //display toast

                Toast.makeText(getApplicationContext()
                        ,"You clicked" + item.getId()
                        ,Toast.LENGTH_SHORT).show();
            }
        });


        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //display toast
                Toast.makeText(getApplicationContext()
                        ,"You Reselected" + item.getId()
                        ,Toast.LENGTH_SHORT).show();

            }
        });
  }

    private void loadFragment(Fragment fragment) {
        //replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fram_layout,fragment)
                .commit();

    }
}