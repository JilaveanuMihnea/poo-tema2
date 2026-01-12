package main.tickets.baseTicket;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.exceptions.AnonymousReportException;
import main.exceptions.ReportOutOfTestingException;
import main.exceptions.UserNotFoundException;
import main.utils.TicketInput;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public abstract class Ticket {
    private enum BusinessPriority {LOW, MEDIUM, HIGH, CRITICAL;}
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
    private String assignedTo = "";
    private String solvedAt = "";
    private String assignedAt = "";
    private String createdAt;
    private List<String> comments = new ArrayList<>();

    protected abstract static class Builder<T extends Builder<T>> {
        private int id; // required
        private String type; // required
        private String title; // required
        private BusinessPriority businessPriority; // required
        private Status status; // required
        private ExpertiseArea expertiseArea; // required
        private String description = ""; // optional
        private String reportedBy = ""; // required
        private String createdAt; // required

        public T mandatory(TicketInput ticketInput, String timestamp){
            this.id = Ticket.idCount++;
            this.type = ticketInput.getType();
            this.title = ticketInput.getTitle();
            if (ticketInput.getReportedBy().isEmpty()) {
                this.businessPriority = BusinessPriority.LOW;
            } else {
                this.businessPriority = BusinessPriority.valueOf(ticketInput.getBusinessPriority());
            }
            this.expertiseArea = ExpertiseArea.valueOf(ticketInput.getExpertiseArea());
            this.reportedBy = ticketInput.getReportedBy();
            this.createdAt = timestamp;
            return self();
        }

        public T description(String description) {
            this.description = description;
            return self();
        }

        protected abstract T self();
    }

    protected Ticket(Builder<?> builder)
            throws AnonymousReportException{
        if (builder.reportedBy.isEmpty() && !builder.type.equalsIgnoreCase("BUG")) {
            throw new AnonymousReportException();
        }
        this.reportedBy = builder.reportedBy;
        this.type = builder.type;
        this.title = builder.title;
        this.businessPriority = builder.businessPriority;
        this.status = Status.OPEN; // default status
        this.expertiseArea = builder.expertiseArea;
        this.description = builder.description;
        this.createdAt = builder.createdAt;
        this.id = builder.id;
    }

    public ObjectNode toObjectNode(com.fasterxml.jackson.databind.ObjectMapper mapper) {
        ObjectNode ticketNode = mapper.createObjectNode();
        ticketNode.put("id", this.id);
        ticketNode.put("type", this.type);
        ticketNode.put("title", this.title);
        ticketNode.put("businessPriority", this.businessPriority.toString());
        ticketNode.put("status", this.status.toString());
        ticketNode.put("createdAt", this.createdAt);
        ticketNode.put("assignedAt", this.assignedAt);
        ticketNode.put("solvedAt", this.solvedAt);
        ticketNode.put("assignedTo", this.assignedTo);
        ticketNode.put("reportedBy", this.reportedBy);
        ArrayNode commentsArray = mapper.createArrayNode();
        for (String comment : this.comments) {
            commentsArray.add(comment);
        }
        ticketNode.set("comments", commentsArray);
        return ticketNode;
    }
}