package main.milestones;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import main.exceptions.TicketInOtherMilestoneException;
import main.users.user.User;
import main.utils.CommandInput;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class MilestoneHandler {
    private static MilestoneHandler instance;
    private Queue<Milestone> milestones;

    private MilestoneHandler() {
        milestones = new PriorityQueue<>(Comparator.comparing(Milestone::getDueDate));
    }

    public static MilestoneHandler getInstance() {
        if (instance == null) {
            instance = new MilestoneHandler();
        }
        return instance;
    }

    public void addMilestone(Milestone milestone)
            throws TicketInOtherMilestoneException {
        for (Milestone m : milestones) {
            for (Integer ticketId : milestone.getTickets()) {
                if (m.getTickets().contains(ticketId)) {
                    throw new TicketInOtherMilestoneException(m.getName(), ticketId);
                }
            }
        }
        updateBlocking(milestone); // todo maybe change this when implementing observers?
        milestones.add(milestone);

    }

    private void updateBlocking(Milestone milestone){
        List<String> blockingFor = milestone.getBlockingFor();
        for(Milestone m: milestones){
            if(blockingFor.contains(m.getName())){
                m.setBlocked(true);
            }
        }
    }

    public void addMilestone(CommandInput commandInput)
            throws TicketInOtherMilestoneException {
        addMilestone(new Milestone(commandInput));
    }


    public ArrayNode getMilestonesArrayNode(ObjectMapper mapper, String timestamp, User user) {
        ArrayNode milestonesArray = new ArrayNode(null);
        if (user.isManager()) {
            for (Milestone milestone : milestones) {
                if (milestone.getCreatedBy().equalsIgnoreCase(user.getUsername()))
                    milestonesArray.add(milestone.toObjectNode(mapper, timestamp));
            }

        } else if (user.isDeveloper()) {
            for (Milestone milestone : milestones) {
                if (milestone.getAssignedDevs().contains(user.getUsername()))
                    milestonesArray.add(milestone.toObjectNode(mapper, timestamp));
            }
        }

        return milestonesArray;
    }

}
