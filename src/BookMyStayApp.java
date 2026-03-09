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

    SingleRoom() {
        super(1, 250, 1500.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Single Room:");
        super.displayRoomDetails();
    }
}
class DoubleRoom extends Room {

    DoubleRoom() {
        super(2, 400, 2500.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Double Room:");
        super.displayRoomDetails();
    }
}
class SuiteRoom extends Room {

    SuiteRoom() {
        super(3, 750, 5000.0);
    }
    @Override
    public void displayRoomDetails() {
        System.out.println("Suite Room:");
        super.displayRoomDetails();
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
}
class RoomSearchService {
    private RoomInventory inventory;
    RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }
    public void searchAvailableRooms() {

        Map<String, Integer> availability = inventory.getRoomAvailability();
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            String type = entry.getKey();
            int count = entry.getValue();

            if (count > 0) {
                Room room = null;
                if (type.equals("Single"))
                    room = new SingleRoom();
                else if (type.equals("Double"))
                    room = new DoubleRoom();
                else if (type.equals("Suite"))
                    room = new SuiteRoom();
                if (room != null) {
                    room.displayRoomDetails();
                    System.out.println("Available Rooms: " + count);
                    System.out.println();
                }
            }
        }
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Welcome User");
        System.out.println("Book My Stay App");
        System.out.println("Version: 4.0");
        System.out.println("Room Search System");
        RoomInventory inventory = new RoomInventory();
        inventory.initializeInventory();
        RoomSearchService searchService = new RoomSearchService(inventory);
        searchService.searchAvailableRooms();
    }
}