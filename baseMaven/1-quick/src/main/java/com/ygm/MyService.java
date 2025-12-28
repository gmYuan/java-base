package com.ygm;


public class MyService {
    public String grade( int score ) {

        if (score >= 60) return "及格";
        else return "不及格";
    }
}
