package main.tickets.baseTicket;

import main.utils.TicketInput;

public abstract class TicketFactory {
    public abstract Ticket createTicket(TicketInput ticketInput);
}
