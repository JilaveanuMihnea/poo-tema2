package main.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.tickets.TicketHandler;
import main.tickets.baseTicket.Ticket;
import main.users.UserHandler;
import main.utils.CommandInput;
import main.utils.OutputHandler;

public class ViewTicketsCommand implements Command {
    private final TicketHandler ticketHandler;
    private final UserHandler userHandler;
    private final CommandInput command;
    private final OutputHandler outputHandler;
    private final ObjectMapper mapper;

    public ViewTicketsCommand(TicketHandler ticketHandler,
                              UserHandler userHandler,
                              CommandInput commandInput,
                              OutputHandler outputHandler,
                              ObjectMapper mapper) {
        this.ticketHandler = ticketHandler;
        this.outputHandler = outputHandler;
        this.mapper = mapper;
        this.command = commandInput;
        this.userHandler = userHandler;
    }

    @Override
    public void execute() {
        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("command", "viewTickets");
        outputNode.put("username", command.getUsername());
        outputNode.put("timestamp", command.getTimestamp());

        ArrayNode ticketsArray = mapper.createArrayNode();
        for (Ticket ticket : ticketHandler.getTickets(userHandler.getUser(command.getUsername()))) {
            ticketsArray.add(ticket.toObjectNode(mapper));
        }

        outputNode.set("tickets", ticketsArray);
        outputHandler.addOutput(outputNode);
    }

    @Override
    public void undo() {
        // Implementation for undoing the view tickets action (if applicable)
    }
}
