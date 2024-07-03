package org.example;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorChecker {
    private static final Color GREEN_FLOWER1 = new Color(46, 161, 0);
    private static final Color GREEN_FLOWER2 = new Color(225, 254, 139);
    private static final Color GREEN_FLOWER3 = new Color(121, 251, 36);
    private static final Color GREEN_FLOWER4 = new Color(231, 251, 173);
    private static final Color GREEN_FLOWER5 = new Color(187, 239, 8);

    public static boolean itsGreen(Color color) {
        Map<Color, Boolean> map = new HashMap<>();
        map.put(GREEN_FLOWER1, true);
        map.put(GREEN_FLOWER2, true);
        map.put(GREEN_FLOWER3, true);
        map.put(GREEN_FLOWER4, true);
        map.put(GREEN_FLOWER5, true);

        return map.getOrDefault(color, false);
    }
}
