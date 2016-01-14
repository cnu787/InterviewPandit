package com.testmyinterview.portal.util;

import nl.captcha.text.producer.TextProducer;
import java.security.SecureRandom;
import java.util.Random;


/**
 * This class is used to generate randomNumber,This randomNumber are used in side the project
 * 
 */
public class  RandomNumberGenerator implements TextProducer {

    private static final Random _gen = new SecureRandom();
    private static final int DEFAULT_LENGTH = 5;

    private static final char[] DEFAULT_CHARS = new char[] {  '1', '2', '3', '4', '5', '6', '7', '8', '9',};
    private final int _length;
    private final char[] _srcChars;
    public RandomNumberGenerator() {
    	this(DEFAULT_LENGTH, DEFAULT_CHARS);
    }
    public  RandomNumberGenerator(int length) {
    this(length, DEFAULT_CHARS);
    }
    public RandomNumberGenerator(int length, char[] srcChars) {
    	_length = length;
    	_srcChars = srcChars != null ? copyOf(srcChars, srcChars.length) : DEFAULT_CHARS;
    }
    public String  getText() {
        int car = _srcChars.length - 1;
        String capText = "";
        for (int i = 0; i < _length; i++) {
            capText += _srcChars[_gen.nextInt(car) + 1];
        }
        return capText;
    }
    public static char[]  copyOf(char[] original, int newLength) {
      char[] copy = new char[newLength];
      System.arraycopy(original, 0, copy, 0,Math.min(original.length, newLength));    
        return copy;
    }
}