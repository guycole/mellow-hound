package net.braingang.mellow.hound.app;

/**
 *
 */
public enum MainActivityFragmentEnum {
    UNKNOWN("Unknown");


    private final String name;

    MainActivityFragmentEnum(String arg) {
        name = arg;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * string to enum conversion
     *
     * @param arg
     * @return
     */
    public static MainActivityFragmentEnum discoverMatchingEnum(String arg) {
        MainActivityFragmentEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (MainActivityFragmentEnum token : MainActivityFragmentEnum.values()) {
            if (token.name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}