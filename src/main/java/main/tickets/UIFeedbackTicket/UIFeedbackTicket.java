package main.tickets.UIFeedbackTicket;

import lombok.Getter;
import lombok.NoArgsConstructor;
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

        public Builder(TicketInput ticketInput) {
            mandatory(ticketInput);
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

        @Override
        public UIFeedbackTicket build() {
            return new UIFeedbackTicket(this);
        }
    }

    private UIFeedbackTicket(Builder builder) {
        super(builder);
        this.uiElementId = builder.uiElementId;
        this.businessValue = builder.businessValue;
        this.usabilityScore = builder.usabilityScore;
        this.screenshotUrl = builder.screenshotUrl;
        this.suggestedFix = builder.suggestedFix;
    }
}