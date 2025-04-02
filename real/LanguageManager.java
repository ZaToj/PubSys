package real;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static LanguageManager instance;
    private Locale locale;
    private ResourceBundle messages;

    private LanguageManager() {
        // Default to English
        locale = Locale.ENGLISH;
        messages = ResourceBundle.getBundle("real.messages", locale);    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    public void setLocale(Locale newLocale) {
        this.locale = newLocale;
        this.messages = ResourceBundle.getBundle("real.messages", locale);
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public Locale getLocale() {
        return locale;
    }
}