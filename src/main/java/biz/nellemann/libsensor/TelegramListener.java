package biz.nellemann.libsensor;

public interface TelegramListener {
    public void onEvent(TelegramEvent event);
    public void onError(TelegramErrorEvent error);
}
