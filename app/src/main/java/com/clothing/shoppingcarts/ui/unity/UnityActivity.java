package com.clothing.shoppingcarts.ui.unity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.unity3d.player.UnityPlayerActivity;

public class UnityActivity extends UnityPlayerActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("UnityActivity", "onCreate called!");
        Intent intent = getIntent();

        //getting Intent data
        String vfrString = intent.getStringExtra("vfrString");
        //String colorwayID = String.valueOf(intent.getIntExtra("colorwayID", 0));

        //String myConcatenatedString = productID.concat(";").concat(colorwayID);
        //Intent intent = new Intent(this, com.unity3d.player.UnityPlayerActivity.class);

        //onSendMessage("ShoppingAppAPI", "SendGarments", myConcatenatedString);
        super.mUnityPlayer.UnitySendMessage("ShoppingAppAPI", "SendGarments", vfrString);
       // super.mUnityPlayer.UnitySendMessage("ShoppingAppAPI", "Execute", "{'messageId': '001', 'parameters': ''}");
        startActivity(intent);
    }

    public void onSendMessage(String s1, String s2, String s3)
    {
        super.mUnityPlayer.UnitySendMessage(s1, s2, s3);
    }

    @Override
    public void onBackPressed()
    {
        // instead of calling UnityPlayerActivity.onBackPressed() we just ignore the back button event
        super.onBackPressed();
        Log.d("onBackPressed","onBackPressed");
    }
}