package com.feed.feedo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference {

    SharedPreferences sp;

    public SharedPreference(Context con)
    {
        sp = con.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setDistanceValue (int distance)
    {
        Editor edit = sp.edit();
        edit.putInt(Constant.KEY_DISTANCE_VALUE, distance);
        edit.commit();
    }

    public Integer getDistanceValue()
    {
        return sp.getInt(Constant.KEY_DISTANCE_VALUE , 5);
    }

    public void setValueString(String paramName, String paramValue) {
        Editor et = sp.edit();
        et.putString(paramName, paramValue);
        et.commit();
    }

    public String getValueString(String paramName) {
        return sp.getString(paramName, "");
    }

    public void setValueInt(String paramName, int paramValue) {
        Editor et = sp.edit();
        et.putInt(paramName, paramValue);
        et.commit();
    }

    public int getValueInt(String paramName) {
        return sp.getInt(paramName, 0);
    }

    public void setValueBool(String paramName, boolean paramValue) {
        Editor et = sp.edit();
        et.putBoolean(paramName, paramValue);
        et.commit();
    }

    public boolean getValueBoolean(String paramName) {
        return sp.getBoolean(paramName, false);
    }

    public void setValueDouble(String paramName, Double paramValue) {
        Editor et = sp.edit();
        et.putLong(paramName, Double.doubleToRawLongBits(paramValue));
        et.commit();

    }

    public Double getValueDouble(String paramName) {

        return Double.longBitsToDouble(sp.getLong(paramName, Double.doubleToLongBits(0)));
    }

    public void setValueLong(String paramName, long paramValue) {
        Editor et = sp.edit();
        et.putLong(paramName, paramValue);
        et.commit();

    }

    public long getValueLong(String paramName) {

        return (sp.getLong(paramName, Double.doubleToLongBits(0)));
    }


    public void clearAll() {
        String dashboardData = sp.getString(Constant.DASHBOARD_DATA, "");
        sp.edit().clear().commit();
        setValueString(Constant.DASHBOARD_DATA, dashboardData);
    }



}
