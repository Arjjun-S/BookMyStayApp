import java.util.*;
public abstract class Room{
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;
    Room(int numberOfBeds,int squareFeet,double pricePerNight){
        this.numberOfBeds=numberOfBeds;
        this.squareFeet=squareFeet;
        this.pricePerNight=pricePerNight;
    }
    public void displayRoomDetails(){
        System.out.println("Beds:"+numberOfBeds);
        System.out.println("Size:"+squareFeet+"sqft");
        System.out.println("Price Per Night:"+pricePerNight);
    }
}
public class singleRoom extends Room{
    int available;
    singleRoom(int available){
        this.available=available;
        super(1,250,1500);
    }
    public void displayRoomDetails(){
        System.out.println("Single Room:");
        super.displayRoomDetails();
        System.out.println("Available:"+available+"\n");
    }
}
public class doubleRoom extends Room{
    int available;
    doubleRoom(int available){
        this.available=available;
        super(2,400,2500);
    }
    public void displayRoomDetails(){
        System.out.println("Double Room:");
        super.displayRoomDetails();
        System.out.println("Available:"+available+"\n");
    }
}
public class suiteRoom extends Room{
    int available;
    suiteRoom(int available){
        this.available=available;
        super(3,750,5000);
    }
    public void displayRoomDetails(){
        System.out.println("Suite Room:");
        super.displayRoomDetails();
        System.out.println("Available:"+available+"\n");
    }
}
void main() {
    System.out.println("Welcome user");
    System.out.println("Book My Stay App");
    System.out.println("Version:2.1");
    System.out.println("Hotel Room Initialization\n");
    singleRoom s=new singleRoom(5);
    doubleRoom d=new doubleRoom(3);
    suiteRoom p=new suiteRoom(2);
    s.displayRoomDetails();
    d.displayRoomDetails();
    p.displayRoomDetails();
}