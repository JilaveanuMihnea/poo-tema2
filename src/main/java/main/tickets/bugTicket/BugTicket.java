package main.tickets.bugTicket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.exceptions.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.utils.TicketInput;
import main.tickets.baseTicket.Ticket;

@Getter
@NoArgsConstructor
public class BugTicket extends Ticket{
    private enum Frequency {RARE, OCCASIONAL, FREQUENT, ALWAYS};
    private enum Severity {MINOR, MODERATE, SEVERE};

    private String expectedBehavior;
    private String actualBehavior;
    private Frequency frequency;
    private Severity severity;
    private String environment;
    private Integer errorCode;

    public static class Builder extends Ticket.Builder<Builder> {
        private String expectedBehavior; // required
        private String actualBehavior; // required
        private Frequency frequency; // required
        private Severity severity; // required
        private String environment; // optional
        private Integer errorCode; // optional

        public Builder(TicketInput ticketInput, String timestamp) {
            mandatory(ticketInput, timestamp);
            this.expectedBehavior = ticketInput.getExpectedBehavior();
            this.actualBehavior = ticketInput.getActualBehavior();
            this.frequency = Frequency.valueOf(ticketInput.getFrequency());
            this.severity = Severity.valueOf(ticketInput.getSeverity());
        }

        public Builder environment(String environment) {
            this.environment = environment;
            return this;
        }

        public Builder errorCode(Integer errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        public BugTicket build()
        throws AnonymousReportException {
            return new BugTicket(this);
        }

    }

    private BugTicket(Builder builder)
        throws AnonymousReportException {
        super(builder);
        this.expectedBehavior = builder.expectedBehavior;
        this.actualBehavior = builder.actualBehavior;
        this.frequency = builder.frequency;
        this.severity = builder.severity;
        this.environment = builder.environment;
        this.errorCode = builder.errorCode;
    }
}