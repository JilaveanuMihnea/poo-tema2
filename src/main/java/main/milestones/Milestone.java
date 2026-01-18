package main.milestones;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import main.utils.CommandInput;
import main.utils.Data;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Milestone {
    private enum Status {
        ACTIVE,
        COMPLETED,
        OVERDUE
    }

    private String name;
    private List<String> blockingFor;
    private LocalDate dueDate;
    private List<Integer> tickets;
    private List<Integer> openTickets;
    private List<Integer> closedTickets;
    private List<String> assignedDevs;
    private LocalDate createdAt;
    private String createdBy;
    private Status status;
    private boolean isBlocked;
    private int overdueBy;
    private List<RepartitionEntry> repartition;

    private class RepartitionEntry {
        private String developer;
        private List<Integer> assignedTickets;

        public RepartitionEntry(String developer, List<Integer> assignedTickets) {
            this.developer = developer;
            this.assignedTickets = assignedTickets;
        }
    }

    public Milestone(final CommandInput commandInput) {
        this.name = commandInput.getName();
        this.blockingFor = commandInput.getBlockingFor();
        this.dueDate = LocalDate.parse(commandInput.getDueDate());
        this.tickets = commandInput.getTickets();
        this.assignedDevs = commandInput.getAssignedDevs();
        this.openTickets = new ArrayList<>();
        this.closedTickets = new ArrayList<>();
        this.openTickets.addAll(tickets);
        this.createdAt = LocalDate.parse(commandInput.getTimestamp());
        this.createdBy = commandInput.getUsername();
        this.status = Status.ACTIVE;
        this.isBlocked = false;
        this.overdueBy = 0;
        initRepartition();
    }

    private void initRepartition() {
        this.repartition = new ArrayList<>();
        for (String dev : assignedDevs) {
            this.repartition.add(new RepartitionEntry(dev, new ArrayList<>()));
        }
    }

    private int daysUntilDue(final LocalDate currentDate) {
        return Data.dateDiff(currentDate, dueDate);
    }

    private double completionPercentage() {
        if (tickets.isEmpty()) {
            return 100.0;
        }
        return Data.round((double) closedTickets.size() / tickets.size() * 100);
    }

//    private

    public ObjectNode toObjectNode(final ObjectMapper mapper, final String currentDate) {
        ObjectNode milestoneNode = mapper.createObjectNode();
        milestoneNode.put("name", name);
        milestoneNode.putPOJO("blockingFor", blockingFor);
        milestoneNode.put("dueDate", dueDate.toString());
        milestoneNode.put("createdAt", createdAt.toString());
        milestoneNode.putPOJO("tickets", tickets);
        milestoneNode.putPOJO("assignedDevs", assignedDevs);
        milestoneNode.put("createdBy", createdBy);
        milestoneNode.put("status", status.toString());
        milestoneNode.put("isBlocked", isBlocked);
        int daysUntilDue = daysUntilDue(LocalDate.parse(currentDate));
        if (daysUntilDue < 0) {
            milestoneNode.put("daysUntilDue", 0);
            milestoneNode.put("overdueBy", -daysUntilDue);
        } else {
            milestoneNode.put("daysUntilDue", daysUntilDue);
            milestoneNode.put("overdueBy", 0);
        }
        milestoneNode.putPOJO("openTickets", openTickets);
        milestoneNode.putPOJO("closedTickets", closedTickets);
        milestoneNode.put("completionPercentage", completionPercentage());
        ArrayNode repartitionArray = mapper.createArrayNode();
        for (RepartitionEntry entry : repartition) {
            ObjectNode entryNode = mapper.createObjectNode();
            entryNode.put("developer", entry.developer);
            entryNode.putPOJO("assignedTickets", entry.assignedTickets);
            repartitionArray.add(entryNode);
        }
        milestoneNode.set("repartition", repartitionArray);

        return milestoneNode;
    }
}

//{
//        "command" : "viewMilestones",
//        "username" : "gabriel_manager",
//        "timestamp" : "2025-10-21",
//        "milestones" : [ {
//        "name" : "Release 1.0",
//        "blockingFor" : [ "UI-Overhaul" ],
//        "dueDate" : "2025-10-28",
//        "createdAt" : "2025-10-20",
//        "tickets" : [ 1, 2, 4, 5 ],
//        "assignedDevs" : [ "isabella_fullstack" ],
//        "createdBy" : "gabriel_manager",
//        "status" : "ACTIVE",
//        "isBlocked" : false,
//        "daysUntilDue" : 8,
//        "overdueBy" : 0,
//        "openTickets" : [ 1, 2, 4, 5 ],
//        "closedTickets" : [ ],
//        "completionPercentage" : 0.0,
//        "repartition" : [ {
//        "developer" : "isabella_fullstack",
//        "assignedTickets" : [ ]
//        } ]
//        }