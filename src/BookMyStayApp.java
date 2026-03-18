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
    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}
class BookingRequestQueue {
    private Queue<Reservation> bookingQueue;
    public BookingRequestQueue() {
        bookingQueue = new LinkedList<>();
    }
    public void addRequest(Reservation reservation) {
        bookingQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }
    public void displayQueue() {

        System.out.println("\nBooking Request Queue (FIFO Order)");

        if (bookingQueue.isEmpty()) {
            System.out.println("No booking requests.");
            return;
        }

        for (Reservation r : bookingQueue) {
            r.displayReservation();
        }
    }
    public Queue<Reservation> getQueue() {
        return bookingQueue;
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome User");
        System.out.println("Book My Stay App");
        System.out.println("Version: 5.0");
        System.out.println("Booking Request Queue System");
        BookingRequestQueue queue = new BookingRequestQueue();
        Reservation r1 = new Reservation("Arjun", "Single");
        Reservation r2 = new Reservation("Meera", "Double");
        Reservation r3 = new Reservation("Rahul", "Suite");
        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);
        queue.displayQueue();
    }
}
