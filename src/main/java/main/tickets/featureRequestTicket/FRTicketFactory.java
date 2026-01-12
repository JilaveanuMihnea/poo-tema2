package main.tickets.featureRequestTicket;

import main.exceptions.AnonymousReportException;
import main.tickets.baseTicket.*;
import main.utils.TicketInput;

public class FRTicketFactory extends TicketFactory {
    @Override
    public Ticket createTicket(TicketInput ticketInput, String timestamp)
    throws AnonymousReportException {
        FeatureRequestTicket.Builder builder = new FeatureRequestTicket.Builder(ticketInput, timestamp);

        if (ticketInput.getDescription() != null) {
            builder.description(ticketInput.getDescription());
        }

        return builder.build();
    }
}
