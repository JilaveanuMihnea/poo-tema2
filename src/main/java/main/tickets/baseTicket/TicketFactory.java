package main.tickets.baseTicket;

import main.exceptions.AnonymousReportException;
import main.utils.TicketInput;

public abstract class TicketFactory {
    public abstract Ticket createTicket(TicketInput ticketInput, String timestamp)
            throws AnonymousReportException;
}
