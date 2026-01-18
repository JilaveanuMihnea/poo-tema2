package main.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.exceptions.AnonymousReportException;
import main.exceptions.ReportOutOfTestingException;
import main.exceptions.UserNotFoundException;
import main.tickets.TicketHandler;
import main.users.UserHandler;
import main.utils.CommandInput;
import main.utils.OutputHandler;
import main.utils.ProjectState;
import main.utils.TicketInput;

public class ReportTicketCommand extends BaseCommand implements Command {
    private TicketHandler ticketHandler;
    private ProjectState projectState;

    public ReportTicketCommand(TicketHandler ticketHandler,
                               UserHandler userHandler,
                               CommandInput commandInput,
                               ProjectState projectState,
                               OutputHandler outputHandler,
                               ObjectMapper mapper) {
        super(userHandler, commandInput, outputHandler, mapper);
        this.ticketHandler = ticketHandler;
        this.projectState = projectState;
    }

    @Override
    public void execute() {
        ObjectNode outputNode = createBaseOutputNode("reportTicket");

        if(!validate(outputNode)) {
            outputHandler.addOutput(outputNode);
            return;
        }
        try {
            validateState(projectState);
        } catch (ReportOutOfTestingException e) {
            outputNode.put("error", "Tickets can only be reported during testing phases.");
            outputHandler.addOutput(outputNode);
            return;
        }
        try {
            ticketHandler.addTicket(commandInput.getParams(), commandInput.getTimestamp());
        } catch (AnonymousReportException e) {
            outputNode.put("error", "Anonymous reports are only allowed for tickets of type BUG.");
            outputHandler.addOutput(outputNode);
        }
    }

    private void validateState(ProjectState projectState)
            throws ReportOutOfTestingException {
        if (projectState != ProjectState.TESTING) {
            throw new ReportOutOfTestingException();
        }
    }

    @Override
    public void undo() {
        // Implementation for undoing the report ticket action
    }

}
