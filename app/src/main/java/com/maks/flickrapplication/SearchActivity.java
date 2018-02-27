package com.maks.flickrapplication;

import android.os.Bundle;

/**
 * Created by GuessWh0o on 06.05.2017.
 * Email: developerint97@gmail.com
 */

public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        activateToolbar(true);
    }

}
