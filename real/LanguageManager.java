package real;

import java.awt.Font;
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
    public Font getFont() {
        // Return a font based on the current locale
        String language = locale.getLanguage();
        int fontSize = 16; // Default size, you can adjust this
        
        switch (language) {
            case "en": // English
                return new Font("Arial", Font.PLAIN, fontSize);
            case "ja": // Japanese
                return new Font("MS Gothic", Font.PLAIN, fontSize);
            default:
                // Fallback font for unsupported languages
                return new Font("Arial", Font.PLAIN, fontSize);
        }
    }
    public Font getFont(int size) {
        // Return a font based on the current locale
        String language = locale.getLanguage();
        int fontSize = size; // Default size, you can adjust this
        
        switch (language) {
            case "en": // English
                return new Font("Arial", Font.PLAIN, fontSize);
            case "ja": // Japanese
                return new Font("MS Gothic", Font.PLAIN, fontSize);
            default:
                // Fallback font for unsupported languages
                return new Font("Arial", Font.PLAIN, fontSize);
        }
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