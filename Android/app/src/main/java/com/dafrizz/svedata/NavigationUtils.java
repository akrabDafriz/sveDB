//package com.dafrizz.svedata;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.view.MenuItem;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//public class NavigationUtils {
//    public static void setupBottomNavigation(final Activity activity, int selectedItemId) {
//        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(selectedItemId);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Intent intent = null;
//                switch (item.getItemId()) {
//                    case R.id.navigation_main:
//                        if (!(activity instanceof MainActivity)) {
//                            intent = new Intent(activity, MainActivity.class);
//                        }
//                        break;
//                    case R.id.navigation_all_cards:
//                        if (!(activity instanceof AllCardsActivity)) {
//                            intent = new Intent(activity, AllCardsActivity.class);
//                        }
//                        break;
//                    case R.id.navigation_all_decks:
//                        if (!(activity instanceof AllDeckActivity)) {
//                            intent = new Intent(activity, AllDeckActivity.class);
//                        }
//                        break;
//                    case R.id.navigation_all_lists:
//                        if (!(activity instanceof AllListActivity)) {
//                            intent = new Intent(activity, AllListActivity.class);
//                        }
//                        break;
//                    case R.id.navigation_profile:
//                        if (!(activity instanceof ProfileActivity)) {
//                            intent = new Intent(activity, ProfileActivity.class);
//                        }
//                        break;
//                }
//                if (intent != null) {
//                    activity.startActivity(intent);
//                    activity.overridePendingTransition(0, 0);
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
//}
//
