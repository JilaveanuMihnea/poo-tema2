package main.tickets.featureRequestTicket;

import main.tickets.baseTicket.*;
import main.utils.TicketInput;

public class FRTicketFactory extends TicketFactory {
    @Override
    public Ticket createTicket(TicketInput ticketInput) {
        FeatureRequestTicket.Builder builder = new FeatureRequestTicket.Builder(ticketInput);

        if (ticketInput.getDescription() != null) {
            builder.description(ticketInput.getDescription());
        }

        return builder.build();
    }
}
