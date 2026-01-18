package main.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.exceptions.TicketInOtherMilestoneException;
import main.milestones.MilestoneHandler;
import main.tickets.TicketHandler;
import main.users.UserHandler;
import main.utils.CommandInput;
import main.utils.OutputHandler;

import java.util.List;

public class CreateMilestoneCommand extends BaseCommand implements Command{
    private TicketHandler ticketHandler;
    private MilestoneHandler milestoneHandler;
    public CreateMilestoneCommand(TicketHandler ticketHandler,
                                  UserHandler userHandler,
                                  MilestoneHandler milestoneHandler,
                                  CommandInput commandInput,
                                  OutputHandler outputHandler,
                                  ObjectMapper mapper) {
        super(userHandler, commandInput, outputHandler, mapper);
        this.ticketHandler = ticketHandler;
        this.milestoneHandler = milestoneHandler;
    }

    @Override
    public void execute() {
        ObjectNode outputNode = createBaseOutputNode("createMilestone");

        if(!validate(outputNode)) {
            outputHandler.addOutput(outputNode);
            return;
        }

        try {
            milestoneHandler.addMilestone(commandInput);
        } catch (TicketInOtherMilestoneException e) {
            outputNode.put("error", "Tickets id already assigned to milestone " + e.getMilestoneName());
            outputHandler.addOutput(outputNode);
            return;
        }
    }



    @Override
    public void undo(){

    }
}
