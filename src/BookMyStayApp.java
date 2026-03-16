import java.util.*;
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}
class ReservationValidator {
    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }
        List<String> validTypes = Arrays.asList("Single", "Double", "Suite");
        if (!validTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No " + roomType + " rooms available.");
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
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Booking Validation");
        Scanner scanner = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();
        inventory.initializeInventory();
        ReservationValidator validator = new ReservationValidator();
        try {
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();
            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();
            validator.validate(guestName, roomType, inventory);
            inventory.decrementRoom(roomType);
            System.out.println("Booking successful for " + guestName);
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred.");
        } finally {
            scanner.close();
        }
    }
}
