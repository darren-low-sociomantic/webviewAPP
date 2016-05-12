package com.example.sociomantic_darren.sonartestwebviewapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.sociomantic.Sociomantic;
import com.sociomantic.Sociomantic.Region;
import com.example.sociomantic_darren.sonartestwebviewapp.utils.SociomanticUtils;

/**
 * Created by Sociomantic_Darren on 4/26/16.
 */
public class BaseActivity  extends AppCompatActivity {
	private Sociomantic.Region region = Sociomantic.Region.AP;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		SociomanticUtils.instance().setContext(getApplicationContext());
	}

}
