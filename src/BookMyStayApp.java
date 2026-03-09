import java.util.*;
class Reservation {

    private String guestName;
    private String roomType;
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
    public String getGuestName() {
        return guestName;
    }
    public String getRoomType() {
        return roomType;
    }
}
class RoomInventory {
    private Map<String, Integer> availability;
    public RoomInventory() {
        availability = new HashMap<>();
    }
    public void initializeInventory() {
        availability.put("Single", 2);
        availability.put("Double", 2);
        availability.put("Suite", 1);
    }
    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
    public void decrementRoom(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }
    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();
    public void addRequest(Reservation r) {
        queue.offer(r);
    }
    public Reservation getNextRequest() {
        return queue.poll();
    }
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
class BookingService {
    private RoomInventory inventory;
    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomAllocationMap = new HashMap<>();
    private int roomCounter = 1;
    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }
    public void processBookings(BookingRequestQueue queue) {
        while (!queue.isEmpty()) {
            Reservation request = queue.getNextRequest();
            String roomType = request.getRoomType();
            System.out.println("\nProcessing request for: " + request.getGuestName());
            if (inventory.getAvailability(roomType) > 0) {
                String roomId = roomType.substring(0,1) + roomCounter++;
                if (!allocatedRoomIds.contains(roomId)) {
                    allocatedRoomIds.add(roomId);
                    roomAllocationMap.putIfAbsent(roomType, new HashSet<>());
                    roomAllocationMap.get(roomType).add(roomId);
                    inventory.decrementRoom(roomType);
                    System.out.println("Reservation Confirmed!");
                    System.out.println("Guest: " + request.getGuestName());
                    System.out.println("Room Type: " + roomType);
                    System.out.println("Assigned Room ID: " + roomId);

                }
            } else {
                System.out.println("Sorry! No " + roomType + " rooms available.");
            }
        }
    }
    public void displayAllocations() {
        System.out.println("\nAllocated Rooms");
        for (Map.Entry<String, Set<String>> entry : roomAllocationMap.entrySet()) {
            System.out.println(entry.getKey() + " Rooms: " + entry.getValue());
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome User");
        System.out.println("Book My Stay App");
        System.out.println("Version: 6.0");
        System.out.println("Room Allocation System");
        RoomInventory inventory = new RoomInventory();
        inventory.initializeInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Arjjun", "Single"));
        queue.addRequest(new Reservation("Dhanush", "Double"));
        queue.addRequest(new Reservation("Rishit", "Suite"));
        queue.addRequest(new Reservation("Hari", "Single"));
        BookingService bookingService = new BookingService(inventory);
        bookingService.processBookings(queue);
        bookingService.displayAllocations();
        inventory.displayInventory();
    }
}