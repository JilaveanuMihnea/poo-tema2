package main.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.tickets.TicketHandler;
import main.tickets.baseTicket.Ticket;
import main.utils.CommandInput;
import main.utils.OutputHandler;

public class ViewTicketsCommand implements Command {
    private TicketHandler ticketHandler;
    private CommandInput command;
    private OutputHandler outputHandler;
    private ObjectMapper mapper;

    public ViewTicketsCommand(TicketHandler ticketHandler,
                              CommandInput commandInput,
                              OutputHandler outputHandler,
                              ObjectMapper mapper) {
        this.ticketHandler = ticketHandler;
        this.outputHandler = outputHandler;
        this.mapper = mapper;
        this.command = commandInput;
    }

    @Override
    public void execute() {
        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("command", "viewTickets");
        outputNode.put("username", command.getUsername());
        outputNode.put("timestamp", command.getTimestamp());

        ArrayNode ticketsArray = mapper.createArrayNode();
        for (Ticket ticket : ticketHandler.getTickets()) {
            ticketsArray.add(ticket.toObjectNode(mapper));
        }

        outputNode.put("tickets", ticketsArray);
        outputHandler.addOutput(outputNode);
    }

    @Override
    public void undo() {
        // Implementation for undoing the view tickets action (if applicable)
    }
}
