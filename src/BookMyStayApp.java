import java.util.*;
class BookingHistory {
    private List<Reservation> confirmedReservations;
    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}
class BookingReportService {
    public void generateReport(BookingHistory history) {
        System.out.println("Booking History Report");
        for (Reservation res : history.getConfirmedReservations()) {
            System.out.println("Guest: " + res.getGuestName() + ", Room Type: " + res.getRoomType());
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
}
class BookingService {
    private RoomInventory inventory;
    private BookingHistory history;
    public BookingService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }
    public void processBooking(Reservation request) {
        if (inventory.getAvailability(request.getRoomType()) > 0) {
            inventory.decrementRoom(request.getRoomType());
            history.addReservation(request);
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Booking History and Reporting");
        RoomInventory inventory = new RoomInventory();
        inventory.initializeInventory();
        BookingHistory history = new BookingHistory();
        BookingService bookingService = new BookingService(inventory, history);
        BookingReportService reportService = new BookingReportService();
        bookingService.processBooking(new Reservation("Arjjun S", "Single"));
        bookingService.processBooking(new Reservation("Dhanush S", "Double"));
        bookingService.processBooking(new Reservation("Raghul Sabapathy", "Suite"));
        reportService.generateReport(history);
    }
}
