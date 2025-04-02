package real;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


public class landing {
    private static JFrame frame;
    private static JButton login, createAcc;
        
    public static void show(){
        frame = new JFrame("Landing Page");
        login = new JButton("Login");
        createAcc = new JButton("Create Account");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        Map<String, Locale> languageMap = new LinkedHashMap<>();
        languageMap.put("English", Locale.ENGLISH);
        languageMap.put("日本語", Locale.JAPANESE);
        languageMap.put("Deutsch", Locale.GERMAN);
        languageMap.put("Polski", Locale.FRENCH);
        languageMap.put("Русский", Locale.ITALIAN);
        //languageMap.put("Français", Locale.FRENCH);  // Example: Add more languages here

        JComboBox<String> languageSelector = new JComboBox<>(languageMap.keySet().toArray(new String[0]));
        languageSelector.addActionListener((ActionEvent e) -> {
            String selectedLanguage = (String) languageSelector.getSelectedItem();
            Locale selectedLocale = languageMap.getOrDefault(selectedLanguage, Locale.ENGLISH);
            LanguageManager.getInstance().setLocale(selectedLocale);
            updateLanguage(); // Refresh UI text
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(languageSelector);
        frame.add(topPanel);
        
        frame.setSize(400,300);
        frame.setLayout(new GridLayout(6,2,10,10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation ( 150, 150 );
        buttonPanel.add(login);
        buttonPanel.add(createAcc);
        frame.add(buttonPanel);

        
        
        //Login
        login.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                signIn.show();
            }
          
        });
        //Create Account
        createAcc.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                createAccount.show();
            }
          
        });
        frame.setVisible(true);
        
    }
    private static void updateLanguage() {
        ResourceBundle messages = LanguageManager.getInstance().getMessages();
        frame.setTitle(messages.getString("landing.title"));
        login.setText(messages.getString("login"));
        createAcc.setText(messages.getString("createAccount"));
    }
    

    
}
