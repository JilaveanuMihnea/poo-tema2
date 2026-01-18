package main.tickets.bugTicket;

import main.exceptions.AnonymousReportException;
import main.tickets.baseTicket.*;
import main.utils.TicketInput;

public class BugTicketFactory extends TicketFactory {
    @Override
    public Ticket createTicket(TicketInput ticketInput, String timestamp)
            throws AnonymousReportException {
        BugTicket.Builder builder = new BugTicket.Builder(ticketInput, timestamp);

        if (ticketInput.getEnvironment() != null) {
            builder.environment(ticketInput.getEnvironment());
        }
        if(ticketInput.getErrorCode() != null) {
            builder.errorCode(ticketInput.getErrorCode());
        }
        if(ticketInput.getDescription() != null) {
            builder.description(ticketInput.getDescription());
        }

        return builder.build();
    }
}
