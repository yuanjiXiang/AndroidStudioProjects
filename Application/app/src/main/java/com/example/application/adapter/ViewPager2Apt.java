package com.example.application.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.example.application.ui2.ConfessWallFragment;
import com.example.application.ui2.DataShareFragment;

public class ViewPager2Apt extends FragmentStateAdapter {

    public ViewPager2Apt(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment confessWallFragment = new ConfessWallFragment();
        Fragment dataShareFragment = new DataShareFragment();
        if (position == 0) {
            return dataShareFragment;
        }
        return confessWallFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
