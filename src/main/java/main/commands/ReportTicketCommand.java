package main.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.tickets.TicketHandler;
import main.utils.OutputHandler;
import main.utils.TicketInput;

public class ReportTicketCommand implements Command {
    private TicketHandler ticketHandler;
    private TicketInput ticketInput;
    private OutputHandler outputHandler;
    private ObjectMapper mapper;

    public ReportTicketCommand(TicketHandler ticketHandler,
                               TicketInput ticketInput,
                               OutputHandler outputHandler,
                               ObjectMapper mapper) {
        this.ticketHandler = ticketHandler;
        this.ticketInput = ticketInput;
        this.outputHandler = outputHandler;
        this.mapper = mapper;
    }

    @Override
    public void execute() {
        ticketHandler.addTicket(ticketInput);
    }

    @Override
    public void undo() {
        // Implementation for undoing the report ticket action
    }

}
