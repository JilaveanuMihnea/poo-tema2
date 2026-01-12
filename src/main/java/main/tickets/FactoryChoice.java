package main.tickets;

import lombok.NoArgsConstructor;
import main.tickets.UIFeedbackTicket.UITicketFactory;
import main.tickets.baseTicket.TicketFactory;
import main.tickets.bugTicket.BugTicketFactory;
import main.tickets.featureRequestTicket.FRTicketFactory;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class FactoryChoice {
    private static final Map<String, TicketFactory> factories = new HashMap<>();

    static {
        factories.put("bug", new BugTicketFactory());
        factories.put("feature_request", new FRTicketFactory());
        factories.put("ui_feedback", new UITicketFactory());
    }

    public TicketFactory getFactory(String type) {
        return factories.get(type.toLowerCase());
    }
}
