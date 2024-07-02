import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static int[][] seats;
    static Ticket[] tickets = new Ticket[52]; // Assuming a maximum of 100 tickets

    static {
        seats = new int[4][]; // initializes the array to have 4 rows
        seats[0] = new int[14]; //1st row
        seats[1] = new int[12];//2nd row
        seats[2] = new int[12];//3rd row
        seats[3] = new int[14];//4th row
    }

    static int ticketCount = 0;

    public static void main(String[] args) {
        Scanner myOBJ = new Scanner(System.in);
        System.out.println("Welcome to the Plane Management application");
        while (true) {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Please select an option: ");
            System.out.println("1) Buy a seat ");
            System.out.println("2) Cancel a seat ");
            System.out.println("3) Find first seat available ");
            System.out.println("4) Show seating plan ");
            System.out.println("5) Print ticket information and total sales ");
            System.out.println("6) Search ticket ");
            System.out.println("0) Quit ");
            System.out.println("--------------------------------------------------------------------");

            try {
                System.out.print("Please select an option: ");
                int option = myOBJ.nextInt();
                switch (option) {
                    case 1:
                        buy_seat(seats);
                        break;
                    case 2:
                        cancel_seat(seats);
                        break;
                    case 3:
                        find_first_available(seats);
                        break;
                    case 4:
                        show_seating_plans(seats);
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket();
                        break;

                    case 0:
                        quit();
                        break;
                    default:
                        System.out.println("Incorrect input!!");
                        System.out.println("Try Again!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                myOBJ.nextLine(); // clear the input buffer
            }
        }
    }

    public static void buy_seat(int[][] seats) { //creating a method
        Scanner seat_input = new Scanner(System.in);

        System.out.println("Enter the row letter (A-D): ");
        char rowLetter = seat_input.next().toUpperCase().charAt(0); // Convert to uppercase for case-insensitivity

        if (rowLetter < 'A' || rowLetter > 'D') {
            System.out.println("Invalid row letter. Please enter a valid row (A-D).");
            return;
        }

        int maxSeatsInRow;
        if (rowLetter == 'A' || rowLetter == 'D') {
            maxSeatsInRow = 14;
        } else {
            maxSeatsInRow = 12;
        }

        int rowNumber; // Declare rowNumber outside the if statements
        if (rowLetter == 'A') {
            rowNumber = 0;
        } else if (rowLetter == 'B') {
            rowNumber = 1;
        } else if (rowLetter == 'C') {
            rowNumber = 2;
        } else { // rowLetter == 'D'
            rowNumber = 3;
        }

        System.out.println("Enter the seat number (1-" + maxSeatsInRow + "): ");
        int seatNo = seat_input.nextInt();

        if (seatNo < 1 || seatNo > maxSeatsInRow) {
            System.out.println("Invalid seat number. Please enter a valid seat number (1-" + maxSeatsInRow + ").");
            return;
        }

        int seatIndex = rowNumber * maxSeatsInRow + seatNo - 1;

        if (0 == seats[rowNumber][seatNo - 1]) {
            seats[rowNumber][seatNo - 1] = 1; // Mark the seat as sold
            System.out.println("Seat " + rowLetter + seatNo + " has been successfully purchased!");

            // Ask for Person information
            System.out.println("Enter Person information:");
            System.out.print("Name: ");
            String name = seat_input.next();
            System.out.print("Surname: ");
            String surname = seat_input.next();
            System.out.print("Email: ");
            String email = seat_input.next();

            // Create a new Person object
            Person person = new Person(name, surname, email);

            // Calculate ticket price (replace this with your pricing mechanism)
            double ticketPrice = calculateTicketPrice(rowLetter, seatNo);

            // Creating new Ticket object
            Ticket ticket = new Ticket(rowLetter, seatNo, ticketPrice, person);
            tickets[ticketCount++] = ticket;
            ticket.save();


        } else {
            System.out.println("Sorry, seat " + rowLetter + seatNo + " is already taken.");
        }
    }

    public static double calculateTicketPrice(char rowLetter, int seatNo) {
        double basePrice;
        if(seatNo<6) {
            basePrice = 200.0;
        }else if (seatNo>=6 && seatNo<10){
            basePrice=150.0;
        }else {
            basePrice=180.0;
        }
        return basePrice;

    }

    public static void cancel_seat(int[][] seats) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the row letter (A-D): ");
        char rowLetter = scanner.next().toUpperCase().charAt(0); // Convert to uppercase for case-insensitivity

        if (rowLetter < 'A' || rowLetter > 'D') {
            System.out.println("Invalid row letter. Please enter a valid row (A-D).");
            return;
        }

        int maxSeatsInRow;
        if (rowLetter == 'A' || rowLetter == 'D') {
            maxSeatsInRow = 14;
        } else {
            maxSeatsInRow = 12;
        }

        System.out.println("Enter the seat number (1-" + maxSeatsInRow + "): ");
        int seatNo = scanner.nextInt();

        if (seatNo < 1 || seatNo > maxSeatsInRow) {
            System.out.println("Invalid seat number. Please enter a valid seat number (1-" + maxSeatsInRow + ").");
            return;
        }

        for (int i = 0; i < ticketCount; i++) {
            Ticket ticket = tickets[i];
            if (ticket.getRowLetter() == rowLetter && ticket.getSeatNo() == seatNo) {
                // Remove the ticket from the array of tickets
                for (int j = i; j < ticketCount - 1; j++) {
                    tickets[j] = tickets[j + 1];
                }
                tickets[--ticketCount] = null;
                break;
            }
        }

        int rowNumber;
        if (rowLetter == 'A') {
            rowNumber = 0;
        } else if (rowLetter == 'B') {
            rowNumber = 1;
        } else if (rowLetter == 'C') {
            rowNumber = 2;
        } else { // rowLetter == 'D'
            rowNumber = 3;
        }

        if (seats[rowNumber][seatNo - 1] == 1) {
            seats[rowNumber][seatNo - 1] = 0; // Mark the seat as available
            System.out.println("Seat " + rowLetter + seatNo + " has been successfully cancelled.");
        } else {
            System.out.println("Seat " + rowLetter + seatNo + " is not booked.");
        }
    }

    public static void find_first_available(int[][] seats) {
        for (char rowLetter = 'A'; rowLetter <= 'D'; rowLetter++) {
            int maxSeatsInRow;
            if (rowLetter == 'A' || rowLetter == 'D') {
                maxSeatsInRow = 14;
            } else {
                maxSeatsInRow = 12;
            }

            int rowNumber;
            if (rowLetter == 'A') {
                rowNumber = 0;
            } else if (rowLetter == 'B') {
                rowNumber = 1;
            } else if (rowLetter == 'C') {
                rowNumber = 2;
            } else { // rowLetter == 'D'
                rowNumber = 3;
            }

            for (int seatNo = 1; seatNo <= maxSeatsInRow; seatNo++) {
                if (seats[rowNumber][seatNo - 1] == 0) {
                    System.out.println("The first available seat is: " + rowLetter + seatNo);
                    return;
                }
            }
        }

        System.out.println("Sorry, all seats are currently booked.");
    }

    public static void show_seating_plans(int[][] seats) {
        System.out.println("Seating Plan:");

        for (int i = 0; i < seats.length; i++) {
            char rowLetter = (char) ('A' + i);
            int maxSeatsInRow = seats[i].length;

            System.out.print(rowLetter + ": ");

            for (int j = 0; j < maxSeatsInRow; j++) {
                if (seats[i][j] == 0) {
                    System.out.print("O ");
                } else {
                    System.out.print("X "); //marking if seat is available or not in the seating plan
                }
            }

            System.out.println();
        }
    }


    public static void print_tickets_info() { //printing ticket information
        double totalAmount = 0;

        System.out.println("Ticket Information:");
        for (int i = 0; i < ticketCount; i++) {
            Ticket ticket = tickets[i];
            System.out.println("Row: " + ticket.getRowLetter() + ", Seat: " + ticket.getSeatNo());
            System.out.println("Price: $" + ticket.getPrice());
            System.out.println("Person: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
            System.out.println("Email: " + ticket.getPerson().getEmail());

            totalAmount += ticket.getPrice();
        }

        System.out.println("Total Amount: $" + totalAmount);
    }

    public static void search_ticket() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the row letter (A-D): ");
        char rowLetter = scanner.next().toUpperCase().charAt(0); // Convert to uppercase

        if (rowLetter < 'A' || rowLetter > 'D') {
            System.out.println("Invalid row letter. Please enter a valid row (A-D).");
            return;
        }

        int maxSeatsInRow;
        if (rowLetter == 'A' || rowLetter == 'D') {
            maxSeatsInRow = 14;
        } else {
            maxSeatsInRow = 12;
        }

        System.out.println("Enter the seat number (1-" + maxSeatsInRow + "): ");
        int seatNo = scanner.nextInt();

        if (seatNo < 1 || seatNo > maxSeatsInRow) {
            System.out.println("Invalid seat number. Please enter a valid seat number (1-" + maxSeatsInRow + ").");
            return;
        }

        boolean seatFound = false;
        for (int i = 0; i < ticketCount; i++) {
            Ticket ticket = tickets[i];
            if (ticket.getRowLetter() == rowLetter && ticket.getSeatNo() == seatNo) {
                System.out.println("Ticket Information:");
                System.out.println("Row: " + ticket.getRowLetter() + ", Seat: " + ticket.getSeatNo());
                System.out.println("Price: $" + ticket.getPrice());
                System.out.println("Person: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
                System.out.println("Email: " + ticket.getPerson().getEmail());
                seatFound = true;
                break;
            }
        }

        if (!seatFound) {
            System.out.println("This seat is available."); // find the availability of the seat
        }
    }

    public static void quit() {
        System.out.println("Thank you for your reservation. Goodbye!");
        System.exit(0);
    }
}