package com.fermii.imp4j.common.utility;


public class PathUtility {
    public static String getProjectRootPath() {
        return PathUtility.class.getClass().getResource("/").getPath();
    }
}
