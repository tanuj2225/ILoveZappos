package com.example.tanuj.ilovezappos;

import android.widget.TextView;

/**
 * Created by tanuj on 2/6/2017.
 */

public class CheckFieldValidator {
    public static boolean checkField(TextView field){
        String fieldData=field.getText().toString();
        if(fieldData.equalsIgnoreCase("") && fieldData!=null){field.setError("Please fill this field");return false;}
        return true;
    }
}
