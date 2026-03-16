import java.util.*;
class AddOnService {
    private String serviceName;
    private double price;
    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }
    public String getServiceName() {
        return serviceName;
    }
    public double getPrice() {
        return price;
    }
    public String toString() {
        return serviceName + " (" + price + " rupees)";
    }
}
class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();
    public void addServiceToReservation(String roomId, AddOnService service) {
        reservationServices.computeIfAbsent(roomId, k -> new ArrayList<>()).add(service);
    }
    public void displayServicesForReservation(String roomId) {
        List<AddOnService> services = reservationServices.getOrDefault(roomId, new ArrayList<>());
        if (services.isEmpty()) {
            System.out.println("No additional services selected.");
        } else {
            double totalCost = 0;
            System.out.print("Add on services selection:\nReservation ID:");
            for (AddOnService s : services) {
                System.out.print(s + " ");
                totalCost += s.getPrice();
            }
            System.out.println("\nTotal Add On Cost: " + totalCost+" rupees");
        }
    }
}
class Reservation {
    private String guestName;
    private String roomType;
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();
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
        System.out.println("\nCurrent Inventory:");
        availability.forEach((k, v) -> System.out.println(k + " Rooms Available: " + v));
    }
}
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();
    public void addRequest(Reservation r) { queue.offer(r); }
    public Reservation getNextRequest() { return queue.poll(); }
    public boolean isEmpty() { return queue.isEmpty(); }
}
class BookingService {
    private RoomInventory inventory;
    private Map<String, String> guestToRoomIdMap = new HashMap<>();
    private int roomCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public String processBooking(Reservation request) {
        String roomType = request.getRoomType();
        if (inventory.getAvailability(roomType) > 0) {
            String roomId = roomType.substring(0, 1) + roomCounter++;
            inventory.decrementRoom(roomType);
            guestToRoomIdMap.put(request.getGuestName(), roomId);
            System.out.println("Reservation Confirmed for " + request.getGuestName() + "! ID: " + roomId);
            return roomId;
        }
        System.out.println("No " + roomType + " available for " + request.getGuestName());
        return null;
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Book My Stay App - Version 7.0");
        RoomInventory inventory = new RoomInventory();
        inventory.initializeInventory();
        BookingService bookingService = new BookingService(inventory);
        AddOnServiceManager serviceManager = new AddOnServiceManager();
        AddOnService breakfast = new AddOnService("Breakfast", 15.0);
        AddOnService spa = new AddOnService("Spa", 50.0);
        AddOnService wifi = new AddOnService("Premium WiFi", 10.0);
        Reservation res1 = new Reservation("Arjjun", "Single");
        String id1 = bookingService.processBooking(res1);
        if (id1 != null) {
            serviceManager.addServiceToReservation(id1, breakfast);
            serviceManager.addServiceToReservation(id1, wifi);
            System.out.println("\nGuest Bill Summary:");
            System.out.println("Guest: " + res1.getGuestName() + " (Room: " + id1 + ")");
            serviceManager.displayServicesForReservation(id1);
        }
        inventory.displayInventory();
    }
}
