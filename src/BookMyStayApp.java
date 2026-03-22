import java.util.*;
class CancellationService {
    private Stack<String> releasedRoomIds;
    private Map<String, String> reservationRoomTypeMap;
    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }
    public void cancelBooking(String reservationId, RoomInventory inventory) {
        if (reservationRoomTypeMap.containsKey(reservationId)) {
            String roomType = reservationRoomTypeMap.get(reservationId);
            inventory.incrementRoom(roomType);
            releasedRoomIds.push(reservationId);
            reservationRoomTypeMap.remove(reservationId);

            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        } else {
            System.out.println("Cancellation failed: Reservation ID " + reservationId + " not found.");
        }
    }
    public void showRollbackHistory() {
        System.out.println("Rollback History (Most Recent First):");
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No cancellations recorded.");
        } else {
            // Stack naturally provides LIFO order
            Stack<String> tempStack = (Stack<String>) releasedRoomIds.clone();
            while (!tempStack.isEmpty()) {
                System.out.println("Released Reservation ID: " + tempStack.pop());
            }
        }
    }
}
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();
    public void initializeInventory() {
        availability.put("Single", 5); // Starting with 5 for demonstration
        availability.put("Double", 2);
        availability.put("Suite", 1);
    }
    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
    public void decrementRoom(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }
    public void incrementRoom(String roomType) {
        availability.put(roomType, availability.get(roomType) + 1);
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Booking Cancellation");
        RoomInventory inventory = new RoomInventory();
        inventory.initializeInventory();
        CancellationService cancellationService = new CancellationService();
        String resId = "Single-1";
        String type = "Single";
        inventory.decrementRoom(type);
        cancellationService.registerBooking(resId, type);
        cancellationService.cancelBooking(resId, inventory);
        cancellationService.showRollbackHistory();
        System.out.println("Updated Single Room Availability: " + inventory.getAvailability("Single"));
    }
}