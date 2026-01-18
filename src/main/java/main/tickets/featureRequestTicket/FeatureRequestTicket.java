package main.tickets.featureRequestTicket;


import lombok.Getter;
import lombok.NoArgsConstructor;
import main.exceptions.AnonymousReportException;
import main.exceptions.ReportOutOfTestingException;
import main.exceptions.UserNotFoundException;
import main.utils.TicketInput;
import main.tickets.baseTicket.Ticket;

@Getter
@NoArgsConstructor
public class FeatureRequestTicket extends Ticket {
    private enum BusinessValue {S, M, L, XL};
    private enum CustomerDemand {LOW, MEDIUM, HIGH, VERY_HIGH};

    private BusinessValue businessValue;
    private CustomerDemand customerDemand;

    public static class Builder extends Ticket.Builder<Builder> {
        private BusinessValue businessValue; // required
        private CustomerDemand customerDemand; // required

        public Builder(TicketInput ticketInput, String timestamp) {
            mandatory(ticketInput, timestamp);
            this.businessValue = BusinessValue.valueOf(ticketInput.getBusinessValue());
            this.customerDemand = CustomerDemand.valueOf(ticketInput.getCustomerDemand());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public FeatureRequestTicket build()
                throws AnonymousReportException {
            return new FeatureRequestTicket(this);
        }
    }

    private FeatureRequestTicket(Builder builder)
            throws AnonymousReportException {
        super(builder);
        this.businessValue = builder.businessValue;
        this.customerDemand = builder.customerDemand;
    }
}