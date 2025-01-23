// Import necessary libraries
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class representing a weather record
class WeatherRecord {
    private String location;
    private String date;
    private double temperature;

    // Constructor for WeatherRecord
    public WeatherRecord(String location, String date, double temperature) {
        this.location = location;
        this.date = date;
        this.temperature = temperature;
    }

    // Getter methods for WeatherRecord
    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }
}

// CustomHashMap implementation for key-value storage
class CustomHashMap<K, V> {
    // Initial capacity for the hashmap
    public static final int INITIAL_CAPACITY = 10;
    // List to store entries
    private List<Entry<K, V>> entries;

    // Constructor for CustomHashMap
    public CustomHashMap() {
        // Initialize entries with null values
        this.entries = new ArrayList<>(INITIAL_CAPACITY);
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            entries.add(null);
        }
    }

    // Put method to add key-value pairs
    public void put(K key, V value) {
        int index = getIndex(key);
        Entry<K, V> entry = entries.get(index);

        // Check if the key already exists
        if (entry == null) {
            entries.set(index, new Entry<>(key, value));
        } else {
            while (entry.next != null) {
                if (entry.key.equals(key)) {
                    entry.value = value;
                    return;
                }
                entry = entry.next;
            }
            entry.next = new Entry<>(key, value);
        }
    }

    // Get method to retrieve value based on key
    public V get(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = entries.get(index);

        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    // Get index for a given key
    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % INITIAL_CAPACITY;
    }

    // Entry class to represent key-value pairs
    private static class Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        // Constructor for Entry
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
}

// Class representing admin credentials
class AdminCredentials {
    private String username;
    private String password;

    // Constructor for AdminCredentials
    public AdminCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter methods for AdminCredentials
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}


class Editor {
}


class CustomLinkedList<T> {
    // Node class to represent elements in the linked list
    private static class Node<T> {
        private T data;
        private Node<T> next;

        // Constructor for Node
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;

    // Push method to add an element to the linked list
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
    }

    // Pop method to remove and return the element from the top of the linked list
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T data = head.data;
        head = head.next;
        return data;
    }

    // Check if the linked list is empty
    public boolean isEmpty() {
        return head == null;
    }
}

// Main class representing the Weather Monitoring System
public class WeatherMonitoringSystem {
    // List to store weather records
    private List<WeatherRecord> weatherRecords;
    // CustomHashMaps to store data by location and date
    private CustomHashMap<String, List<WeatherRecord>> locationDataMap;
    private CustomHashMap<String, List<WeatherRecord>> dateDataMap;
    // List to store admin credentials
    private List<AdminCredentials> adminCredentialsList;
    // CustomLinkedList to store the latest weather updates
    private CustomLinkedList<WeatherRecord> latestWeatherUpdates;

    // Constructor for WeatherMonitoringSystem
    public WeatherMonitoringSystem() {
        // Initialize lists and maps
        weatherRecords = new ArrayList<>();
        locationDataMap = new CustomHashMap<>();
        dateDataMap = new CustomHashMap<>();
        adminCredentialsList = new ArrayList<>();
        latestWeatherUpdates = new CustomLinkedList<>(); // Initialize the CustomLinkedList

        // Adding sample admin users
        adminCredentialsList.add(new AdminCredentials("Ammad", "2212105"));
        adminCredentialsList.add(new AdminCredentials("Zain", "2212135"));
        adminCredentialsList.add(new AdminCredentials("Habib", "2212109"));
    }

    // Method to perform admin login
    private Editor loginAsAdmin(String username, String password) {
        for (AdminCredentials credentials : adminCredentialsList) {
            if (credentials.getUsername().equals(username) && credentials.getPassword().equals(password)) {
                return new Editor(); // Create an instance of the Editor class
            }
        }
        return null;
    }

    // Method to add a weather record
    public void addWeatherRecord(WeatherRecord record) {
        // Add record to the main list
        weatherRecords.add(record);

        // Update locationDataMap
        int index = getIndex(record.getLocation());
        List<WeatherRecord> locationRecords = locationDataMap.get(String.valueOf(index));
        if (locationRecords == null) {
            locationRecords = new ArrayList<>();
            locationDataMap.put(String.valueOf(index), locationRecords);
        }
        locationRecords.add(record);

        // Update dateDataMap
        index = getIndex(record.getDate());
        List<WeatherRecord> dateRecords = dateDataMap.get(String.valueOf(index));
        if (dateRecords == null) {
            dateRecords = new ArrayList<>();
            dateDataMap.put(String.valueOf(index), dateRecords);
        }
        dateRecords.add(record);

        // Update latestWeatherUpdates
        latestWeatherUpdates.push(record);
    }

    // Method to display weather information by location
    public void determineWeatherByLocation(String location) {
        int index = getIndex(location);
        List<WeatherRecord> records = locationDataMap.get(String.valueOf(index));
        if (records != null) {
            System.out.println("\nWeather Information for Location: " + location);
            for (WeatherRecord record : records) {
                System.out.println("Date: " + record.getDate() + ", Temperature: " + record.getTemperature());
            }
        }
    }

    // Method to display weather information by date
    public void determineWeatherByDate(String date) {
        int index = getIndex(date);
        List<WeatherRecord> records = dateDataMap.get(String.valueOf(index));
        if (records != null) {
            System.out.println("\nWeather Information for Date: " + date);
            for (WeatherRecord record : records) {
                System.out.println("Location: " + record.getLocation() + ", Temperature: " + record.getTemperature());
            }
        } else {
            System.out.println("No weather information available for the specified date.");
        }
    }

    // Method to display the latest weather updates
    public void displayLatestWeatherUpdates(int count) {
        System.out.println("\nLatest Weather Updates:");
        for (int i = 0; i < count; i++) {
            if (!latestWeatherUpdates.isEmpty()) {
                WeatherRecord record = latestWeatherUpdates.pop();
                System.out.println("Location: " + record.getLocation() +
                        ", Date: " + record.getDate() +
                        ", Temperature: " + record.getTemperature());
            } else {
                System.out.println("No more updates available.");
            }
        }
    }

    // Method to add a weather record by an admin user
    public void addWeatherRecordByAdmin() {
        Scanner scanner = new Scanner(System.in);

        // Get admin credentials
        System.out.print("Enter Admin Username: ");
        String adminUsername = scanner.next();

        System.out.print("Enter Admin Password: ");
        String adminPassword = scanner.next();

        // Attempt to login as admin
        Editor currentEditor = loginAsAdmin(adminUsername, adminPassword);

        if (currentEditor != null) {
            // Admin is logged in, get weather record details
            System.out.print("Enter Location: ");
            String location = scanner.next();

            System.out.print("Enter Date (YYYY-MM-DD): ");
            String date = scanner.next();

            System.out.print("Enter Temperature: ");
            double temperature = scanner.nextDouble();

            // Create a new WeatherRecord and add it
            WeatherRecord newRecord = new WeatherRecord(location, date, temperature);
            addWeatherRecord(newRecord);

            System.out.println("Weather record added successfully by admin!");
        } else {
            System.out.println("Invalid credentials. Weather record not added.");
        }
    }

    // Method to get the index from the key using a custom hashing method
    private int getIndex(String key) {
        return Math.abs(key.hashCode()) % CustomHashMap.INITIAL_CAPACITY;
    }

    // Main method
    public static void main(String[] args) {
        // Create an instance of WeatherMonitoringSystem
        WeatherMonitoringSystem weatherSystem = new WeatherMonitoringSystem();
        Scanner scanner = new Scanner(System.in);

        // Adding sample records to the system
        weatherSystem.addWeatherRecord(new WeatherRecord("karachi", "2023-01-01", 25.5));
        weatherSystem.addWeatherRecord(new WeatherRecord("lahore", "2023-01-01", 26.0));
        weatherSystem.addWeatherRecord(new WeatherRecord("islamabad", "2023-01-01", 28.3));
        // ... (adding more sample records)

        // Variable to store the user's role choice
        int roleChoice;

        // Main loop for role selection
        do {
            System.out.println("\n\t\t*********************Select Role*********************");
            System.out.println("\t\t****                 1. Admin                    ****");
            System.out.println("\t\t****                 2. User                     ****");
            System.out.println("\t\t****                 3. Exit                     ****");
            System.out.println("\t\t*****************************************************");
            System.out.print("Enter your choice: ");

            roleChoice = scanner.nextInt();

            if (roleChoice == 1) {
                // Admin role
                weatherSystem.addWeatherRecordByAdmin();
            } else if (roleChoice == 2) {
                // User role
                int userChoice;
                do {
                    System.out.println("\n\t\t*********************User Options:*********************");
                    System.out.println("\t\t****          1. Determine Weather by Location     ****");
                    System.out.println("\t\t****          2. Determine Weather by Date         ****");
                    System.out.println("\t\t****          3. Display Latest Weather Updates   ****");
                    System.out.println("\t\t****          4. Exit                             ****");
                    System.out.println("\t\t******************************************************");

                    userChoice = scanner.nextInt();

                    switch (userChoice) {
                        case 1:
                            // Get location from the user and display weather information
                            System.out.print("Enter Location: ");
                            String location = scanner.next();
                            weatherSystem.determineWeatherByLocation(location);
                            break;

                        case 2:
                            // Get date from the user and display weather information
                            System.out.print("Enter Date (YYYY-MM-DD): ");
                            String date = scanner.next();
                            weatherSystem.determineWeatherByDate(date);
                            break;

                        case 3:

                            weatherSystem.displayLatestWeatherUpdates(1);
                            break;

                        case 4:
                            // Exit user options
                            System.out.println("Exiting User Options.");
                            break;

                        default:
                            // Invalid choice
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }
                } while (userChoice != 4);
            }
        } while (roleChoice != 3);

        // Exit message
        System.out.println("Thank you For Using Our Project Goodbye!");

        // Group members information
        System.out.println("***********************************************");
        System.out.println("***********************************************");
        System.out.println("****************GROUP MEMBERS******************");
        System.out.println("*****     1.Ammad khan                    *****");
        System.out.println("*****     2.Habibuallah Uqaili            *****");
        System.out.println("*****     3. Zain Dhanani                 *****");
        System.out.println("***********************************************");
        System.out.println("***********************************************");
        System.out.println("***********************************************");

        // Close the scanner
        scanner.close();
    }
}
