import java.io.FileWriter;
import java.io.IOException;
public class Ticket {

    // Attributes
    private char rowLetter;
    private int seatNo;
    private double price;
    private Person person;

    // Constructor
    public Ticket(char rowLetter, int seatNo, double price, Person person) {
        this.rowLetter = rowLetter;
        this.seatNo = seatNo;
        this.price = price;
        this.person = person;
    }

    // Getters
    public char getRow() {
        return rowLetter;
    }

    public int getSeat() {
        return seatNo;
    }

    public double getPrice() {
        return price;
    }

    public Person getPerson() {
        return person;
    }

    // Setters
    public void setRow(char rowLetter) {
        this.rowLetter = rowLetter;
    }

    public void setSeat(int seat) {
        this.seatNo = seatNo;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // Method to print information of a Ticket
    public void printTicketInfo() {
        System.out.println("Ticket Information:");
        System.out.println("Row: " + rowLetter);
        System.out.println("Seat: " + seatNo);
        System.out.println("Price: $" + price);
        System.out.println("Person Information:");
        person.printInfo(); // Utilize the printInfo method from the Person class

        //
        }
    public char getRowLetter() {
        return rowLetter;
    }
    public int getSeatNo() {
        return seatNo;
    }
    public void save() {
        String fileName = rowLetter + String.valueOf(seatNo) + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Ticket Information:\n");
            writer.write("Row: " + rowLetter + "\n");
            writer.write("Seat: " + seatNo + "\n");
            writer.write("Price: $" + price + "\n");
            writer.write("Person Information:\n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
            System.out.println("Ticket information saved to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the ticket information to file: " + e.getMessage());
        }
    }
}
