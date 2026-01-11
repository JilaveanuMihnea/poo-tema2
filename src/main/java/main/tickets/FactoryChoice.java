package main.tickets;

import lombok.NoArgsConstructor;
import main.tickets.baseTicket.TicketFactory;

@NoArgsConstructor
public class FactoryChoice {
    public TicketFactory getFactory(String type) {
        switch (type.toLowerCase()) {
            case "bug":
                return new main.tickets.bugTicket.BugTicketFactory();
            case "feature_request":
                return new main.tickets.featureRequestTicket.FRTicketFactory();
            case "ui_feedback":
                return new main.tickets.UIFeedbackTicket.UITicketFactory();
            default:
                throw new IllegalArgumentException("Unknown ticket type: " + type);
        }
    }
}
