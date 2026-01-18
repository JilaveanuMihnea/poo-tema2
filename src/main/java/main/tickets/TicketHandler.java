package main.tickets;


import lombok.Getter;
import lombok.NoArgsConstructor;
import main.exceptions.AnonymousReportException;
import main.tickets.baseTicket.Ticket;
import main.tickets.baseTicket.TicketFactory;
import main.users.UserHandler;
import main.users.user.User;
import main.utils.TicketInput;

import java.util.*;

@Getter
public class TicketHandler {
    private static TicketHandler instance;
    private List<Ticket> tickets;

    private TicketHandler() {
        this.tickets = new ArrayList<Ticket>();
    }

    public static TicketHandler getInstance() {
        if (instance == null) {
            instance = new TicketHandler();
        }
        return instance;
    }


    public void addTicket(TicketInput ticketInput, String timestamp)
    throws AnonymousReportException {
        TicketFactory factory = new FactoryChoice().getFactory(ticketInput.getType());
        Ticket ticket = factory.createTicket(ticketInput, timestamp);
        tickets.add(ticket);
    }

    public List<Ticket> getTickets(User user) {
        if (user.isManager()) {
            return tickets;
        } else if (user.isDeveloper()) {
            List<Ticket> developerTickets = new ArrayList<>();
            // todo: implement this
            return developerTickets;
        } else { // Reporter
            List<Ticket> reporterTickets = new ArrayList<>();
            for (Ticket ticket : tickets) {
                if (ticket.getReportedBy().equals(user.getUsername())) {
                    reporterTickets.add(ticket);
                }
            }
            return reporterTickets;
        }
    }
}
