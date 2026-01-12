package main.tickets.UIFeedbackTicket;

import main.exceptions.AnonymousReportException;
import main.tickets.baseTicket.Ticket;
import main.tickets.baseTicket.TicketFactory;

public class UITicketFactory extends TicketFactory {
    @Override
    public Ticket createTicket(main.utils.TicketInput ticketInput, String timestamp)
    throws AnonymousReportException {
        UIFeedbackTicket.Builder builder = new UIFeedbackTicket.Builder(ticketInput, timestamp);

        if (ticketInput.getScreenshotUrl() != null) {
            builder.screenshotUrl(ticketInput.getScreenshotUrl());
        }
        if (ticketInput.getSuggestedFix() != null) {
            builder.suggestedFix(ticketInput.getSuggestedFix());
        }
        if (ticketInput.getDescription() != null) {
            builder.description(ticketInput.getDescription());
        }
        if (ticketInput.getUiElementId() != null) {
            builder.uiElementId(ticketInput.getUiElementId());
        }

        return builder.build();
    }
}
