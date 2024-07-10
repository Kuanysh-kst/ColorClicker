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
        map.put(new Color(239, 105, 118), true);
        map.put(new Color(225, 92, 107), true);

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
        map.put(new Color(250,4,197),true);
        map.put(new Color(254,1,200),true);
        map.put(new Color(218,0,174),true);
        map.put(new Color(226,38,138),true);
        map.put(new Color(81,81,75),true);
        map.put(new Color(255,0,200),true);
        map.put(new Color(98,84,59),true);
        map.put(new Color(74,64,53),true);
        map.put(new Color(92,92,92),true);
        map.put(new Color(79,62,41),true);
        map.put(new Color(125,125,123),true);
        map.put(new Color(115,115,115),true);
        map.put(new Color(73,73,71),true);
        map.put(new Color(128,129,126),true);
        map.put(new Color(205,1,162),true);
        map.put(new Color(85,84,77),true);
        map.put(new Color(55,113,171),true);
        map.put(new Color(55,149,216),true);
        map.put(new Color(57,135,202),true);
        map.put(new Color(61,165,228),true);
        map.put(new Color(47,70,82),true);
        map.put(new Color(157,124,135),true);
        map.put(new Color(228,11,157),true);
        map.put(new Color(118,34,58),true);
        map.put(new Color(64,53,49),true);
        map.put(new Color(238,24,183),true);
        map.put(new Color(227,2,179),true);
        map.put(new Color(203,4,165),true);
        map.put(new Color(254,2,201),true);
        map.put(new Color(242,3,190),true);
        map.put(new Color(160,88,123),true);
        map.put(new Color(249,144,227),true);
        map.put(new Color(183,104,151),true);
        map.put(new Color(206,4,138),true);


        return map.getOrDefault(color, false);
    }
}
