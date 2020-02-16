package com.openclassrooms.realestatemanager.ui.main;

import java.util.ArrayList;

public interface MainFragmentContract {

    interface View{

    }

    interface Presenter{

        ArrayList<String> getMainDatas();
    }
}
