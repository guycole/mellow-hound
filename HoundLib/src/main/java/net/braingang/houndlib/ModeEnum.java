package net.braingang.houndlib;

/**
 *  Run mode
 */
public enum ModeEnum {
    ONE_SHOT("OneShot"),
    RUNNING("Running"),
    STOPPED("Stopped"),
    UNKNOWN("Unknown");

    private final String name;

    ModeEnum(String arg) {
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
    public static ModeEnum discoverMatchingEnum(String arg) {
        ModeEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (ModeEnum token : ModeEnum.values()) {
            if (token.name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
