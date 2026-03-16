import java.util.*;
class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;
    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }
    public void run() {
        while (true) {
            Reservation reservation = null;
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                reservation = bookingQueue.getNextRequest();
            }
            if (reservation != null) {
                synchronized (inventory) {
                    allocationService.allocateRoom(reservation, inventory);
                }
            }
            try { Thread.sleep(10); } catch (InterruptedException e) { break; }
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
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }
    public int getAvailability(String roomType) { return availability.getOrDefault(roomType, 0); }
    public void decrementRoom(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }
    public void displayRemaining() {
        System.out.println("Remaining Inventory:");
        availability.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();
    public void addRequest(Reservation r) { queue.offer(r); }
    public Reservation getNextRequest() { return queue.poll(); }
    public boolean isEmpty() { return queue.isEmpty(); }
}
class RoomAllocationService {
    private Map<String, Integer> counters = new HashMap<>();
    public void allocateRoom(Reservation res, RoomInventory inventory) {
        String type = res.getRoomType();
        if (inventory.getAvailability(type) > 0) {
            int count = counters.getOrDefault(type, 1);
            System.out.println("Booking confirmed for Guest: " + res.getGuestName() + ", Room ID: " + type + "-" + count);
            inventory.decrementRoom(type);
            counters.put(type, count + 1);
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Concurrent Booking Simulation");
        RoomInventory inventory = new RoomInventory();
        inventory.initializeInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();
        bookingQueue.addRequest(new Reservation("Arjjun S", "Single"));
        bookingQueue.addRequest(new Reservation("Dhanush", "Double"));
        bookingQueue.addRequest(new Reservation("Rishit", "Suite"));
        bookingQueue.addRequest(new Reservation("Tharun", "Single"));
        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService);
        Thread t1 = new Thread(processor);
        Thread t2 = new Thread(processor);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }
        inventory.displayRemaining();
    }
}
