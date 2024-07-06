package org.example;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorChecker {
    public static boolean itsVersion1(Color color) {
        Map<Color, Boolean> map = new HashMap<>();
        map.put(new Color(46, 161, 0), true);
        map.put(new Color(225, 254, 139), true);
        map.put(new Color(121, 251, 36), true);
        map.put(new Color(231, 251, 173), true);
        map.put(new Color(187, 239, 8), true);

        return map.getOrDefault(color, false);
    }

    public static boolean itsVersion2(Color color) {
        Map<Color, Boolean> map = new HashMap<>();
        map.put(new Color(236, 118, 210),true);
        map.put(new Color(132,130,127),true);
        map.put(new Color(117,119,111),true);
        map.put(new Color(197,13,127),true);
        map.put(new Color(243,150,223),true);
        map.put(new Color(38,4,8),true);
      // map.put(new Color(212,213,214),true);
        map.put(new Color(250,4,197),true);
        map.put(new Color(254,1,200),true);
        map.put(new Color(218,0,174),true);
        map.put(new Color(226,38,138),true);
        map.put(new Color(81,81,75),true);
        map.put(new Color(255,0,200),true);

        return map.getOrDefault(color, false);
    }
}
