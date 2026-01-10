package main.tickets.baseTicket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import main.utils.TicketInput;

@Getter
@NoArgsConstructor
public abstract class Ticket {
    private enum BusinessPriority {LOW, MEDIUM, HIGH;}
    private enum Status {OPEN, IN_PROGRESS, CLOSED};
    private enum ExpertiseArea {FRONTEND, BACKEND, DEVOPS, DESIGN, DB};

    private static int idCount = 0;
    private int id;
    private String type;
    private String title;
    private BusinessPriority businessPriority;
    private Status status;
    private ExpertiseArea expertiseArea;
    private String description;
    private String reportedBy;

    protected abstract static class Builder<T extends Builder<T>> {
        private int id; // required
        private String type; // required
        private String title; // required
        private BusinessPriority businessPriority; // required
        private Status status; // required
        private ExpertiseArea expertiseArea; // required
        private String description = ""; // optional
        private String reportedBy = ""; // required

        public T mandatory(TicketInput ticketInput){
            this.id = Ticket.idCount++;
            this.type = ticketInput.getType();
            this.title = ticketInput.getTitle();
            this.businessPriority = BusinessPriority.valueOf(ticketInput.getBusinessPriority());
            this.expertiseArea = ExpertiseArea.valueOf(ticketInput.getExpertiseArea());
            this.reportedBy = ticketInput.getReportedBy();
            return self();
        }

        public T description(String description) {
            this.description = description;
            return self();
        }

        protected abstract T self();

        public abstract Ticket build();
    }

    protected Ticket(Builder<?> builder) {
        this.type = builder.type;
        this.title = builder.title;
        this.businessPriority = builder.businessPriority;
        this.status = builder.status;
        this.expertiseArea = builder.expertiseArea;
        this.description = builder.description;
        this.reportedBy = builder.reportedBy;
        this.id = builder.id;
    }
}