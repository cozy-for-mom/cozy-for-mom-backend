package com.juju.cozyformombackend3.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculateUtil {
    public static double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100) / 100.0;
    }
}
