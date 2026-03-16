import java.util.*;

abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;
    Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }
    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price Per Night: " + pricePerNight);
    }
}
class SingleRoom extends Room {
    int available;

    SingleRoom(int available) {
        super(1, 250, 1500);
        this.available = available;
    }
    public void displayRoomDetails() {
        System.out.println("Single Room:");
        super.displayRoomDetails();
        System.out.println("Available: " + available + "\n");
    }
}
class DoubleRoom extends Room {
    int available;

    DoubleRoom(int available) {
        super(2, 400, 2500);
        this.available = available;
    }
    public void displayRoomDetails() {
        System.out.println("Double Room:");
        super.displayRoomDetails();
        System.out.println("Available: " + available + "\n");
    }
}
class SuiteRoom extends Room {
    int available;

    SuiteRoom(int available) {
        super(3, 750, 5000);
        this.available = available;
    }
    public void displayRoomDetails() {
        System.out.println("Suite Room:");
        super.displayRoomDetails();
        System.out.println("Available: " + available + "\n");
    }
}
class RoomInventory {
    private Map<String, Integer> roomAvailability;
    public RoomInventory() {
        roomAvailability = new HashMap<>();
    }
    public void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Welcome user");
        System.out.println("Book My Stay App");
        System.out.println("Version: 3.0");
        System.out.println("Hotel Room Initialization\n");
        SingleRoom s = new SingleRoom(5);
        DoubleRoom d = new DoubleRoom(3);
        SuiteRoom p = new SuiteRoom(2);
        s.displayRoomDetails();
        d.displayRoomDetails();
        p.displayRoomDetails();
        RoomInventory inventory = new RoomInventory();
        inventory.initializeInventory();
        System.out.println("\nCentralized Inventory State:");
        inventory.displayInventory();
    }
}