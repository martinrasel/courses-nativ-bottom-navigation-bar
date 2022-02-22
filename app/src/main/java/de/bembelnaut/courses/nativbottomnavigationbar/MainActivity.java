package de.bembelnaut.courses.nativbottomnavigationbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    public static final String ACTIVE_NAV_FRAGMENT = "ACTIVE_NAV_FRAGMENT";
    private NavigationBarView.OnItemSelectedListener bottomNavClickListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment newNavFragment = null;

            int activeFragmentId = -1;
            Fragment activeFragment = getSupportFragmentManager().findFragmentByTag(ACTIVE_NAV_FRAGMENT);
            if (activeFragment != null && activeFragment.isVisible()) {
                if (activeFragment instanceof FriendsFragment) {
                    activeFragmentId = R.id.friends;
                } else if (activeFragment instanceof MoneyFragment) {
                    activeFragmentId = R.id.money;
                } else if (activeFragment instanceof UmbrellaFragment) {
                    activeFragmentId = R.id.umbrella;
                }
            }

            int itemId = item.getItemId();
            if (itemId == activeFragmentId) {
                return true;
            }

            int moveAnimation = R.anim.move_in;

            switch (itemId) {
                case R.id.friends:
                    if (activeFragmentId == R.id.umbrella
                            || activeFragmentId == R.id.money) {
                        moveAnimation = R.anim.move_out;
                    }

                    newNavFragment = new FriendsFragment();
                    break;
                case R.id.money:
                    if (activeFragmentId == R.id.umbrella) {
                        moveAnimation = R.anim.move_out;
                    }

                    newNavFragment = new MoneyFragment();
                    break;
                case R.id.umbrella:
                    newNavFragment = new UmbrellaFragment();
                    break;
                default:
                    return false;
            }

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(moveAnimation, R.anim.fade_out)
                    .replace(R.id.frameLayout, newNavFragment, ACTIVE_NAV_FRAGMENT)
                    .commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationMenuView = findViewById(R.id.bottomNavigationView);
        navigationMenuView.setOnItemSelectedListener(bottomNavClickListener);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new FriendsFragment(), ACTIVE_NAV_FRAGMENT).commit();
    }
}