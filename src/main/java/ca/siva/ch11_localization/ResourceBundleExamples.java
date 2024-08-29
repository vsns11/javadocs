package ca.siva.ch11_localization;

import lombok.extern.slf4j.Slf4j;

import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/*
NOTE:
1) if the match does not found for the given requested locale to getBundle(xx) method:
    Search for rb with passed locale to getBundle(xx) method, if not use the default locale, if no default, then finally use name.properties
2) if the match was found for the given requested locale to getBundle(xx) method:
    Search for rb with passed locale to getBundle(xx) method, if not found, use name.properties else throw error even the property is not found at runtime.
3) ResourceBundle.getKeys() will return keys from all resource bundle files in the inheritance chain,
not just from a single file.

 */
@Slf4j
public class ResourceBundleExamples {

    /**
     * Example of loading the default ResourceBundle (e.g., for the US locale).
     * Input: No input, uses default locale (en_US).
     * Output: Logs the greeting and farewell messages in English.
     */
    public static void loadDefaultResourceBundle() {
        Locale locale = Locale.US;  // Default locale
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        
        String greeting = bundle.getString("greeting");
        String farewell = bundle.getString("farewell");
        
        log.info("Greeting (en_US): {}", greeting);  // Expected output: Hello
        log.info("Farewell (en_US): {}", farewell);  // Expected output: Goodbye
    }

    /**
     * Example of loading a ResourceBundle for the French locale.
     * Input: No input, uses French locale (fr_FR).
     * Output: Logs the greeting and farewell messages in French.
     */
    public static void loadFrenchResourceBundle() {
        Locale locale = Locale.FRANCE;  // French locale
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        
        String greeting = bundle.getString("greeting");
        String farewell = bundle.getString("farewell");
        
        log.info("Greeting (fr_FR): {}", greeting);  // Expected output: Bonjour
        log.info("Farewell (fr_FR): {}", farewell);  // Expected output: Au revoir
    }

    /**
     * Example of loading a ResourceBundle for the German locale.
     * Input: No input, uses German locale (de_DE).
     * Output: Logs the greeting and farewell messages in German.
     */
    public static void loadGermanResourceBundle() {
        Locale locale = Locale.GERMANY;  // German locale
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        
        String greeting = bundle.getString("greeting");
        String farewell = bundle.getString("farewell");
        
        log.info("Greeting (de_DE): {}", greeting);  // Expected output: Hallo
        log.info("Farewell (de_DE): {}", farewell);  // Expected output: Auf Wiedersehen
    }

    /**
     * Example of loading a ResourceBundle with a fallback.
     * Input: No input, uses a locale without a specific bundle (e.g., es_ES).
     * Output: Logs the greeting and farewell messages from the default bundle (en_US) as a fallback.
     */
    public static void loadResourceBundleWithFallback() {
        Locale locale = new Locale("es", "ES");  // Spanish locale, but no specific bundle provided
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        
        String greeting = bundle.getString("greeting");
        String farewell = bundle.getString("farewell");
        
        log.info("Greeting (es_ES fallback to en_US): {}", greeting);  // Expected output: Hello
        log.info("Farewell (es_ES fallback to en_US): {}", farewell);  // Expected output: Goodbye
    }

    /**
     * Example of loading a ResourceBundle using Locale.Category.DISPLAY.
     * Input: No input, uses the DISPLAY category locale (e.g., fr_FR for displaying UI text).
     * Output: Logs the greeting and farewell messages in the display locale's language.
     */
    public static void loadResourceBundleUsingDisplayCategory() {
        Locale.setDefault(Locale.Category.DISPLAY, Locale.FRANCE);  // Set DISPLAY category to French
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault(Locale.Category.DISPLAY));
        
        String greeting = bundle.getString("greeting");
        String farewell = bundle.getString("farewell");
        
        log.info("Greeting (DISPLAY category, fr_FR): {}", greeting);  // Expected output: Bonjour
        log.info("Farewell (DISPLAY category, fr_FR): {}", farewell);  // Expected output: Au revoir
    }

    /**
     * Example of mixing Locale.Category.FORMAT and Locale.Category.DISPLAY.
     * Input: No input, uses different locales for FORMAT and DISPLAY categories.
     * Output: Logs the greeting and formatted number with different locales.
     */
    public static void mixedLocaleScenario() {
        // Set the default locale for FORMAT to Germany (for formatting numbers)
        Locale.setDefault(Locale.Category.FORMAT, Locale.GERMANY);

        // Set the default locale for DISPLAY to France (for displaying text)
        Locale.setDefault(Locale.Category.DISPLAY, Locale.FRANCE);

        // Fetch resource bundle using DISPLAY locale (France)
        ResourceBundle displayBundle = ResourceBundle.getBundle("messages", Locale.getDefault(Locale.Category.DISPLAY));

        // Retrieve greeting and farewell messages using DISPLAY locale (French messages)
        String greeting = displayBundle.getString("greeting");  // From messages_fr_FR.properties
        String farewell = displayBundle.getString("farewell");  // From messages_fr_FR.properties
        log.info("Greeting (DISPLAY locale = France): {}", greeting);  // Expected: Bonjour
        log.info("Farewell (DISPLAY locale = France): {}", farewell);  // Expected: Au revoir

        // Format a number using FORMAT locale (Germany)
        double number = 1234567.89;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();  // Uses the FORMAT category locale (Germany)
        String formattedNumber = numberFormat.format(number);
        log.info("Formatted Number (FORMAT locale = Germany): {}", formattedNumber);  // Expected: 1.234.567,89

        // Reset the default locales to avoid affecting other parts of the application
        Locale.setDefault(Locale.Category.FORMAT, Locale.US);
        Locale.setDefault(Locale.Category.DISPLAY, Locale.US);
    }

    /**
     * Example of loading a ResourceBundle and printing all keys from the inheritance chain.
     * This demonstrates how keys are loaded from different files based on the locale.
     * Input: Locale "en_UK"
     * Output: Logs all keys and values from both the base bundle (mymsgs.properties) and the locale-specific bundle (mymsgs_en_UK.properties).
     */
    public static void loadResourceBundleAndPrintKeys() {
        Locale myloc = new Locale.Builder().setLanguage("en").setRegion("UK").build();  // en_UK locale
        ResourceBundle msgs = ResourceBundle.getBundle("mymsgs", myloc);  // Load resource bundle for en_UK

        Enumeration<String> en = msgs.getKeys();  // Get all keys in the resource bundle (including inheritance chain)
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            String val = msgs.getString(key);
            log.info("{}: {}", key, val);  // Print key-value pairs
        }
    }

    public static void main(String[] args) {
        loadDefaultResourceBundle();  // Load the default (en_US) resource bundle
        loadFrenchResourceBundle();  // Load the French (fr_FR) resource bundle
        loadGermanResourceBundle();  // Load the German (de_DE) resource bundle
        loadResourceBundleWithFallback();  // Load a bundle with fallback to default (en_US)
        loadResourceBundleUsingDisplayCategory();  // Load bundle using DISPLAY category locale
        mixedLocaleScenario();  // Run the mixed locale scenario
        loadResourceBundleAndPrintKeys(); // Load resource bundle and print all keys and values
    }
}
