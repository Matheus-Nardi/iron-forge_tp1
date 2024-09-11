package com.example.models;

public class Photo{
    public String thumb;
    public Object highres;
    public boolean is_user_uploaded;
    @Override
    public String toString() {
        return "Photo [thumb=" + thumb + ", highres=" + highres + ", is_user_uploaded=" + is_user_uploaded + "]";
    }
    
}