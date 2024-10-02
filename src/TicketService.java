import java.util.Random;

public class TicketService {
    private static final int MIN_VALUE = 3600;
    private static final int MAX_VALUE = 10800;

    public static void main(String[] args) {

        int randomDuration = generateRandomValueFromRange();

        Ticket emptyTicket = new Ticket();
        Ticket limitedTicket = new Ticket("lim1", "TAURON", 378);
        Ticket fullTicket = new Ticket("ful1", "NARODOWY", 948, randomDuration,true, StadiumSector.B, 12.874567f, 194.567f);

        System.out.println(emptyTicket);
        System.out.println(limitedTicket);
        System.out.println(fullTicket);
    }

    private static int generateRandomValueFromRange() {
        Random random = new Random();

        return random.nextInt(TicketService.MAX_VALUE - TicketService.MIN_VALUE + 1) + TicketService.MIN_VALUE;
    }
}
