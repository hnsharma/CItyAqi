package com.cityaqi.utils;

import android.graphics.Color;

public class AQIColor {

    public static int  getAQIColor(Double aqi)
    {
        if(aqi <=50)
        {
            return Color.parseColor("#55A84F");
        }
        else if(aqi <=100)
        {
            return Color.parseColor("#A3C853");
        }
        else if(aqi <=200)
        {
            return Color.parseColor("#FFF833");
        }
        else if(aqi <=300)
        {
            return Color.parseColor("#F8CA92");
        }
        else if(aqi <=400)
        {
            return Color.parseColor("#E93F33");
        }
        else if(aqi <=500)
        {
            return Color.parseColor("#AF2D24");
        }
        else
        {
            return Color.parseColor("#000000");
        }
    }
}
