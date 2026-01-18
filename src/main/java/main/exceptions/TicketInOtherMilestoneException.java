package main.exceptions;

import lombok.Getter;

@Getter
public class TicketInOtherMilestoneException extends Exception{
    private String milestoneName;
    private int ticketId;

    public TicketInOtherMilestoneException(String milestoneName, int ticketId) {
        this.milestoneName = milestoneName;
        this.ticketId = ticketId;
    }
}
