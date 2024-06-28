package com.kneelawk.kgui.engine.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

public class KGUIPaths {
    public static Path EXPORT_GENERATED_PATH;

    static {
        String prefix;
        if (KGUIConstants.EXPORT_GENERATED_PATH.isBlank()) {
            prefix = "";
        } else {
            if (KGUIConstants.EXPORT_GENERATED_PATH.endsWith("/")) {
                prefix = KGUIConstants.EXPORT_GENERATED_PATH;
            } else {
                prefix = KGUIConstants.EXPORT_GENERATED_PATH + "/";
            }
        }

        EXPORT_GENERATED_PATH = Paths.get(prefix + ".kgui");
    }
}
