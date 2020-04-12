package biz.nellemann.libsensor;

public enum TelegramError {

    UNKNOWN(99, "An unknown error occurred."),
    LIGHT_6(6, "Too little light returned or there is no target at all."),
    LIGHT_5(5, "Too much light returned/blinding or false light."),
    LIGHT_4(4, "False light or an undefined spot recorded."),
    TARGET_2(2, "A target is observed but outside the measuring range."),
    TARGET_1(1, "A target is observed but outside the measuring range."),
    TARGET_0(0, "A target is observed but outside the measuring range.");

    private final int id;
    private final String message;

    TelegramError(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() { return id; }
    public String getMessage() { return message; }

}
