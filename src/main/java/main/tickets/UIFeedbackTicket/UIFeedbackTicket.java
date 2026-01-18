package main.tickets.UIFeedbackTicket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.exceptions.AnonymousReportException;
import main.utils.TicketInput;
import main.tickets.baseTicket.Ticket;

@Getter
@NoArgsConstructor
public class UIFeedbackTicket extends Ticket {
    private enum BusinessValue {S, M, L, XL};

    private String uiElementId;
    private BusinessValue businessValue;
    private Integer usabilityScore;
    private String screenshotUrl;
    private String suggestedFix;

    public static class Builder extends Ticket.Builder<Builder> {
        private String uiElementId;
        private BusinessValue businessValue; // required
        private Integer usabilityScore; // required
        private String screenshotUrl;
        private String suggestedFix;

        public Builder(TicketInput ticketInput, String timestamp) {
            mandatory(ticketInput, timestamp);
            this.businessValue = BusinessValue.valueOf(ticketInput.getBusinessValue());
            this.usabilityScore = ticketInput.getUsabilityScore();
        }

        public Builder uiElementId(String uiElementId) {
            this.uiElementId = uiElementId;
            return this;
        }

        public Builder screenshotUrl(String screenshotUrl) {
            this.screenshotUrl = screenshotUrl;
            return this;
        }

        public Builder suggestedFix(String suggestedFix) {
            this.suggestedFix = suggestedFix;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }


        public UIFeedbackTicket build()
        throws AnonymousReportException {
            return new UIFeedbackTicket(this);
        }
    }

    private UIFeedbackTicket(Builder builder)
            throws AnonymousReportException {
        super(builder);
        this.uiElementId = builder.uiElementId;
        this.businessValue = builder.businessValue;
        this.usabilityScore = builder.usabilityScore;
        this.screenshotUrl = builder.screenshotUrl;
        this.suggestedFix = builder.suggestedFix;
    }

//    @Override
//    public ObjectNode toObjectNode(ObjectMapper mapper) {
//        ObjectNode ticketNode = super.toObjectNode(mapper);
//        ticketNode.put("businessValue", businessValue.toString());
//        ticketNode.put("usabilityScore", usabilityScore);
//        if (uiElementId != null)
//            ticketNode.put("uiElementId", uiElementId);
//        if (screenshotUrl != null)
//            ticketNode.put("screenshotUrl", screenshotUrl);
//        if (suggestedFix != null)
//            ticketNode.put("suggestedFix", suggestedFix);
//        return ticketNode;
//    }
}