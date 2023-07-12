// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.util;

import java.util.Random;

public class StringUtil
{
    public static String randomText(final int length) {
        final int leftLimit = 48;
        final int rightLimit = 122;
        final Random random = new Random();
        final String generatedString = random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(length).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        return generatedString;
    }
}
