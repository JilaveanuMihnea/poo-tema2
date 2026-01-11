package main.tickets.featureRequestTicket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

        public Builder(TicketInput ticketInput) {
            mandatory(ticketInput);
            this.businessValue = BusinessValue.valueOf(ticketInput.getBusinessValue());
            this.customerDemand = CustomerDemand.valueOf(ticketInput.getCustomerDemand());
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public FeatureRequestTicket build() {
            return new FeatureRequestTicket(this);
        }
    }

    private FeatureRequestTicket(Builder builder) {
        super(builder);
        this.businessValue = builder.businessValue;
        this.customerDemand = builder.customerDemand;
    }

    @Override
    public ObjectNode toObjectNode(ObjectMapper mapper) {
        ObjectNode ticketNode = super.toObjectNode(mapper);
        ticketNode.put("businessValue", businessValue.toString());
        ticketNode.put("customerDemand", customerDemand.toString());
        return ticketNode;
    }
}