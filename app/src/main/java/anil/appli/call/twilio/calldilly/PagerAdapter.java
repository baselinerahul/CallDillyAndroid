package anil.appli.call.twilio.calldilly;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import anil.appli.call.twilio.calldilly.fragment.FirstFragment;
import anil.appli.call.twilio.calldilly.fragment.SecondFragment;
import anil.appli.call.twilio.calldilly.fragment.ThirdFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int NumOfTabs;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        this.NumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FirstFragment tab1 = new FirstFragment();
                return tab1;
            case 1:
                SecondFragment tab2 = new SecondFragment();
                return tab2;
            case 2:
                ThirdFragment tab3 = new ThirdFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NumOfTabs;
    }
}
