package main.tickets;


import lombok.Getter;
import lombok.NoArgsConstructor;
import main.tickets.baseTicket.Ticket;
import main.tickets.baseTicket.TicketFactory;
import main.utils.TicketInput;

import java.util.*;

@Getter
public class TicketHandler {
    private List<Ticket> tickets;

    public TicketHandler(){
        this.tickets = new ArrayList<Ticket>();
    }

    public void addTicket(TicketInput ticketInput){
        TicketFactory factory = new FactoryChoice().getFactory(ticketInput.getType());
        Ticket ticket = factory.createTicket(ticketInput);
        tickets.add(ticket);
    }
}
