package biz.nellemann.libsensor;

public interface TelegramListener {
    public void onTelegramResultEvent(TelegramResultEvent event);
    public void onTelegramErrorEvent(TelegramErrorEvent error);
}
