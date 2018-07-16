// Copyright (c) 2014-2018 K Team. All Rights Reserved.
package org.kframework.unparser;

import java.util.Map;

/**
 * @author Denis Bogdanas
 *         Date: 10/9/13
 */
public enum ColorSetting {
    OFF, ON, EXTENDED;

    public ColorSetting color(boolean ttyStdout, String outputFile, Map<String, String> env) {
        boolean colorize = outputFile == null && ttyStdout;
        if (this == null) {
            try {
                if (!colorize) {
                    return ColorSetting.OFF;
                } else if (Integer.parseInt(env.get("K_COLOR_SUPPORT")) >= 256) {
                    return ColorSetting.EXTENDED;
                } else {
                    return ColorSetting.ON;
                }
            } catch (NumberFormatException e) {
                return ColorSetting.ON;
            }
        }
        return this;
    }
}
