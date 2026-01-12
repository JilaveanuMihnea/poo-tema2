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

public class ReportTicketCommand implements Command {
    private TicketHandler ticketHandler;
    public UserHandler userHandler;
    private CommandInput commandInput;
    private ProjectState projectState;
    private OutputHandler outputHandler;
    private ObjectMapper mapper;

    public ReportTicketCommand(TicketHandler ticketHandler,
                               UserHandler userHandler,
                               CommandInput commandInput,
                               ProjectState projectState,
                               OutputHandler outputHandler,
                               ObjectMapper mapper) {
        this.ticketHandler = ticketHandler;
        this.commandInput = commandInput;
        this.outputHandler = outputHandler;
        this.mapper = mapper;
        this.projectState = projectState;
        this.userHandler = userHandler;
    }

    @Override
    public void execute() {
        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("command", "reportTicket");
        outputNode.put("username", commandInput.getUsername());
        outputNode.put("timestamp", commandInput.getTimestamp());
        try {
            validate(commandInput.getUsername(), projectState);
        } catch (ReportOutOfTestingException e) {
            outputNode.put("error", "Tickets can only be reported during testing phases.");
            outputHandler.addOutput(outputNode);
            return;
        } catch (UserNotFoundException e) {
            outputNode.put("error", "The user " + commandInput.getUsername() + " does not exist.");
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

    private void validate(String username, ProjectState projectState)
            throws ReportOutOfTestingException,
                   UserNotFoundException {
        if (projectState != ProjectState.TESTING) {
            throw new ReportOutOfTestingException();
        }
        if (!userHandler.isUser(username)) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void undo() {
        // Implementation for undoing the report ticket action
    }

}
