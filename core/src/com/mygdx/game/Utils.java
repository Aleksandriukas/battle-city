package com.mygdx.game;

public  class Utils {

public static int random(int min, int max){
    return (int) (Math.random() * (max - min + 1) + min);
    }
}
