package main.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.exceptions.UserNotFoundException;
import main.tickets.TicketHandler;
import main.users.UserHandler;
import main.utils.CommandInput;
import main.utils.OutputHandler;

public class BaseCommand {
    protected UserHandler userHandler;
    protected CommandInput commandInput;
    protected OutputHandler outputHandler;
    protected ObjectMapper mapper;

    protected BaseCommand(UserHandler userHandler,
                          CommandInput commandInput,
                          OutputHandler outputHandler,
                          ObjectMapper mapper) {
        this.userHandler = userHandler;
        this.commandInput = commandInput;
        this.outputHandler = outputHandler;
        this.mapper = mapper;
    }

    protected boolean validate(ObjectNode outputNode) {
        try {
            validateUsername();
            validateRole();
        } catch (UserNotFoundException e) {
            outputNode.put("error", "The user " + commandInput.getUsername() + " does not exist.");
            return false;
        }
        return true;
    }

    protected ObjectNode createBaseOutputNode(String commandName) {
        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("command", commandName);
        outputNode.put("username", commandInput.getUsername());
        outputNode.put("timestamp", commandInput.getTimestamp());
        return outputNode;
    }

    private void validateRole() {

    }

    private void validateUsername() throws UserNotFoundException {
        if (!userHandler.userExists(commandInput.getUsername())) {
            throw new UserNotFoundException();
        }
    }
}
