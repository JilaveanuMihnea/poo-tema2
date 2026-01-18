package main.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.tickets.TicketHandler;
import main.tickets.baseTicket.Ticket;
import main.users.UserHandler;
import main.utils.CommandInput;
import main.utils.OutputHandler;

public class ViewTicketsCommand extends BaseCommand implements Command {
    private final TicketHandler ticketHandler;

    public ViewTicketsCommand(TicketHandler ticketHandler,
                              UserHandler userHandler,
                              CommandInput commandInput,
                              OutputHandler outputHandler,
                              ObjectMapper mapper) {
        super(userHandler, commandInput, outputHandler, mapper);
        this.ticketHandler = ticketHandler;
    }

    @Override
    public void execute() {
        ObjectNode outputNode = createBaseOutputNode("viewTickets");

        if (!validate(outputNode)) {
            outputHandler.addOutput(outputNode);
            return;
        }

        ArrayNode ticketsArray = mapper.createArrayNode();
        for (Ticket ticket : ticketHandler.getTickets(userHandler.getUser(commandInput.getUsername()))) {
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
