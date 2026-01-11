package main.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface Command {
    public void execute();
    public void undo();
}
