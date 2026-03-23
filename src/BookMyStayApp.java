import java.util.*;
import java.io.*;
class FilePersistenceService {
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            Map<String, Integer> data = inventory.getAllAvailability();
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }
    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            inventory.initializeInventory();
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    inventory.updateRoom(parts[0], Integer.parseInt(parts[1]));
                }
            }
            System.out.println("Inventory restored from persistence.");
        } catch (Exception e) {
            System.out.println("Error loading inventory. Starting fresh.");
            inventory.initializeInventory();
        }
    }
}
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void initializeInventory() {
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }
    public void updateRoom(String type, int count) {
        availability.put(type, count);
    }
    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
    public Map<String, Integer> getAllAvailability() {
        return availability;
    }
    public void displayInventory() {
        System.out.println("Current Inventory:");
        availability.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("System Recovery");
        String filePath = "inventory.txt";
        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();
        persistenceService.loadInventory(inventory, filePath);
        inventory.displayInventory();
        persistenceService.saveInventory(inventory, filePath);
    }
}
