package com.example.testjacoco.test_jacoco;

/**
 * Utility class for bitwise flags helpers and manipulations.
 *
 * @author Ivan_Kobzarev on 5/26/2014.
 */
public final class FlagUtil {

    /**
     * Default epmty private constructor for utility class.
     */
    private FlagUtil() {
        //empty
    }

    /**
     * Checks flags contains checkFlags.
     *
     * @param flags flags
     * @param checkFlags  checking checkFlags
     * @return True if flags contains checkFlags
     */
    public static boolean hasFlags(int flags, int checkFlags) {
        return (flags & checkFlags) == checkFlags;
    }
}
