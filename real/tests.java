package real;

import static org.junit.Assert.*;

import java.awt.Font;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

public class tests {
    private static final String TEST_NAME = "toj";
    private static final String TEST_DOB = "2003-11-01 00:00:00";
    private static final String TEST_ADDRESS = "Swag City";
    private static final String TEST_GENDER = "Tojian";
    private static final String TEST_PASSWORD = "maxcatman123";
    private static final int TEST_POINTS = 178;
    private static final int TEST_ID = 1;
    private static final boolean TEST_IS_ADMIN = true;

    private static final int TEST_ITEM_ID = 1;
    private static final int TEST_ITEM_COST = 1000;
    private static final int TEST_POINT_AMOUNT = 10;
    private static final String TEST_ITEM_NAME = "Hamburger";
    private String absoluteImgPath;
    private LanguageManager manager = LanguageManager.getInstance();


    private User user;

    @Before
    public void resetLanguage() throws SQLException {
        manager.setLocale(Locale.ENGLISH);
    }

    @Before
    public void setUp() throws SQLException {
        // Initialize User object
        user = new User(TEST_NAME, TEST_PASSWORD, TEST_DOB, TEST_ADDRESS, TEST_GENDER, TEST_POINTS, TEST_ID, TEST_IS_ADMIN);
    }
    @Test
    public void testAgeCAlc() {
        String dob = "1990-05-15";
        int expectedAge = 34; // 2025-04-11 - 1990-05-15
        assertEquals("Age should be calculated correctly", expectedAge, User.ageCAlc(dob));
    }
    @Test
    public void testAgeCAlcBirthdayToday() {
        String dob = "1990-04-11";
        int expectedAge = 35; // Exact birthday on 2025-04-11
        assertEquals("Age should be exact on birthday", expectedAge, User.ageCAlc(dob));
    }
    @Test
    public void testPassHasher() {
        String inputPass = "abcdef";
        User user = new User();
        String expectedHash = "11101111000010011110";
        assertEquals("Password hash should match", expectedHash, user.passHasher(inputPass));
    }
    @Test
    public void testPassHasherEmptyInput() {
        assertEquals("Empty password should return empty hash", "", user.passHasher(""));
    }
    @Test
    public void testSetPointsAmountDoesNotUpdate() {
        int newPoints = 200;
        user.setPointsAmount(newPoints);
        assertEquals("setPointsAmount should update ", 200, user.getPointAmount());
    }
    @Test
    public void testGetUserInvalidPassword() throws Exception {
        User result = user.getUser(TEST_NAME, "wrongPassword");
        assertNull("User should not be found with wrong password", result);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(TEST_NAME, user.getName());
        assertEquals(TEST_PASSWORD, user.getPass());
        assertEquals(TEST_DOB, user.getDob());
        assertEquals(TEST_ADDRESS, user.getAddress());
        assertEquals(TEST_GENDER, user.getGender());
        assertEquals(TEST_POINTS, user.getPointAmount());
        assertEquals(TEST_ID, user.getId());
        assertEquals(TEST_IS_ADMIN, user.isAdmin());
        assertEquals(21, user.getAge()); // 1990-05-15 to 2025-04-11 = 34 years
    }
    @Test
    public void testGetUserSuccess() throws Exception {
        User result = user.getUser(TEST_NAME, TEST_PASSWORD);
        assertNotNull("User should be found", result);
        assertEquals(TEST_NAME, result.getName());
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_POINTS, result.getPointAmount());
        assertEquals(TEST_ADDRESS, result.getAddress());
        assertEquals(TEST_GENDER, result.getGender());
        assertEquals(TEST_DOB, result.getDob());
        assertEquals(TEST_IS_ADMIN, result.isAdmin());
    }
    @Test
    public void testGetFavFood() throws Exception {
        // Test data: Pizza (itemId 1) ordered twice, Burger (itemId 2) once
        String result = user.getFav(1); // 1 = food
        assertEquals("Burger should be the most ordered food", "Burger", result);
    }  
    @Test
    public void testGetFavDrink() throws Exception {
        String result = user.getFav(2); // 2 = drink
        assertEquals("Asahi should be the most ordered Drink", "Asahi", result);
    }
    @Test
    public void testMenuItemConstructorAndGetters() {
        menuItem item = new menuItem(TEST_ITEM_ID, TEST_ITEM_COST, TEST_ITEM_NAME, absoluteImgPath, TEST_POINT_AMOUNT);
        assertEquals("Item ID should match", TEST_ITEM_ID, item.getItemId());
        assertEquals("Item cost should match", TEST_ITEM_COST, item.getItemCost());
        assertEquals("Item name should match", TEST_ITEM_NAME, item.getItemName());
        assertEquals("Image path should match", absoluteImgPath, item.getImgFilePath());
        assertEquals("Point amount should match", TEST_POINT_AMOUNT, item.getPointAmount());
        assertNotNull("Icon should be created", item.getIcon());
        assertTrue("Icon should be ImageIcon", item.getIcon() instanceof ImageIcon);
    }
    @Test
    public void testGetMenuItemFood() {
        menuItem item = menuItem.getMenuItem(TEST_ITEM_ID);
        assertNotNull("Item should be found", item);
        assertTrue("Should return foodItem", item instanceof foodItem);
        assertEquals("Item ID should match", TEST_ITEM_ID, item.getItemId());
        assertEquals("Item cost should match", TEST_ITEM_COST, item.getItemCost());
        assertEquals("Item name should match", TEST_ITEM_NAME, item.getItemName());
        assertEquals("Point amount should match", TEST_POINT_AMOUNT, item.getPointAmount());
    }
    @Test
    public void testGetMenuItemDrink() {
        menuItem item = menuItem.getMenuItem(10);
        assertNotNull("Item should be found", item);
        assertTrue("Should return drinkItem", item instanceof drinkItem);
        assertEquals("Item ID should match", 10, item.getItemId());
        assertEquals("Item cost should match", 5, item.getItemCost());
        assertEquals("Item name should match", "Draft Beer", item.getItemName());
        assertEquals("Point amount should match", 3, item.getPointAmount());
    }
    @Test
    public void testGetMenuItemNonExistent() {
        menuItem item = menuItem.getMenuItem(999);
        assertNull("Non-existent item should return null", item);
    }
    @Test
    public void testSingletonInstance() {
        LanguageManager instance1 = LanguageManager.getInstance();
        LanguageManager instance2 = LanguageManager.getInstance();
        assertSame("getInstance should return the same instance", instance1, instance2);
        assertEquals("Default locale should be English", Locale.ENGLISH, instance1.getLocale());
    }
    @Test
    public void testGetFontDefaultSizeEnglish() {
        LanguageManager manager = LanguageManager.getInstance();
        Font font = manager.getFont();
        assertEquals("Font for English should be Arial", "Arial", font.getName());
        assertEquals("Font style should be plain", Font.PLAIN, font.getStyle());
        assertEquals("Default font size should be 16", 16, font.getSize());
    }

    @Test
    public void testGetFontDefaultSizeJapanese() {
        LanguageManager manager = LanguageManager.getInstance();
        manager.setLocale(Locale.JAPANESE);
        Font font = manager.getFont();
        assertEquals("Font for Japanese should be MS Gothic", "MS Gothic", font.getName());
        assertEquals("Font style should be plain", Font.PLAIN, font.getStyle());
        assertEquals("Default font size should be 16", 16, font.getSize());
    }
    @Test
    public void testGetFontCustomSizeEnglish() {
        LanguageManager manager = LanguageManager.getInstance();
        int customSize = 24;
        Font font = manager.getFont(customSize);
        assertEquals("Font for English should be Arial", "Arial", font.getName());
        assertEquals("Font style should be plain", Font.PLAIN, font.getStyle());
        assertEquals("Font size should match custom size", customSize, font.getSize());
    }
    @Test
    public void testGetFontCustomSizeJapanese() {
        LanguageManager manager = LanguageManager.getInstance();
        manager.setLocale(Locale.JAPANESE);
        int customSize = 12;
        Font font = manager.getFont(customSize);
        assertEquals("Font for Japanese should be MS Gothic", "MS Gothic", font.getName());
        assertEquals("Font style should be plain", Font.PLAIN, font.getStyle());
        assertEquals("Font size should match custom size", customSize, font.getSize());
    }
    @Test
    public void testSetLocaleAndGetMessagesEnglish() {
        LanguageManager manager = LanguageManager.getInstance();
        manager.setLocale(Locale.ENGLISH);
        assertEquals("Locale should be set to English", Locale.ENGLISH, manager.getLocale());
        ResourceBundle messages = manager.getMessages();
        assertEquals("Message should match English properties", "Welcome back, ${0}!", messages.getString("welcome.message"));
    }
    @Test
    public void testSetLocaleFallbackToDefault() {
        LanguageManager manager = LanguageManager.getInstance();
        manager.setLocale(Locale.FRENCH); // No messages_fr.properties
        assertEquals("Locale should be set to French", Locale.FRENCH, manager.getLocale());
        ResourceBundle messages = manager.getMessages();
        // Falls back to messages.properties (English)
        assertEquals("Message should fallback to English", "Welcome back, ${0}!", messages.getString("welcome.message"));
    }
    @Test
    public void testNoArgConstructor() {
        order noArgOrder = new order();
        assertNull("Items should be null", noArgOrder.getItems());
        assertEquals("Cost should be default (0.0)", 0.0, noArgOrder.getCost(), 0.001);
        assertEquals("User ID should be default (0)", 0, noArgOrder.getUserId());
    }
}
