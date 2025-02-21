import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TicketBookingSystem {
    private final boolean[] seats;
    private final Lock lock = new ReentrantLock();

    public TicketBookingSystem(int seatCount) {
        seats = new boolean[seatCount];
    }

    public void bookSeat(int seatNumber, String userName, boolean isVIP) {
        lock.lock();
        try {
            if (seatNumber < 1 || seatNumber > seats.length) {
                System.out.println(userName + ": Invalid seat number!");
                return;
            }
            if (!seats[seatNumber - 1]) {
                seats[seatNumber - 1] = true;
                System.out.println(userName + " booked seat " + seatNumber);
            } else {
                System.out.println(userName + ": Seat " + seatNumber + " is already booked!");
            }
        } finally {
            lock.unlock();
        }
    }
}

class User extends Thread {
    private final TicketBookingSystem system;
    private final int seatNumber;
    private final boolean isVIP;

    public User(TicketBookingSystem system, int seatNumber, String name, boolean isVIP) {
        super(name);
        this.system = system;
        this.seatNumber = seatNumber;
        this.isVIP = isVIP;
        if (isVIP) {
            setPriority(MAX_PRIORITY);
        } else {
            setPriority(NORM_PRIORITY);
        }
    }

    @Override
    public void run() {
        system.bookSeat(seatNumber, getName(), isVIP);
    }
}

public class TicketBookingMain {
    public static void main(String[] args) {
        TicketBookingSystem system = new TicketBookingSystem(5);

        Thread user1 = new User(system, 1, "Anish (VIP)", true);
        Thread user2 = new User(system, 2, "Bobby (Regular)", false);
        Thread user3 = new User(system, 3, "Charlie (VIP)", true);
        Thread user4 = new User(system, 1, "David (Regular)", false);
        Thread user5 = new User(system, 4, "Elena (VIP)", true);
        Thread user6 = new User(system, 2, "Fiona (Regular)", false);
        
        user1.start();
        user2.start();
        user3.start();
        user4.start();
        user5.start();
        user6.start();
    }
}
