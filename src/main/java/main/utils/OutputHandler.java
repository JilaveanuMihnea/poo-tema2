package main.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class OutputHandler {
    List<ObjectNode> outputs;

    public OutputHandler(List<ObjectNode> outputs) {
        this.outputs = outputs;
    }

    public void addOutput(ObjectNode output) {
        outputs.add(output);
    }
}
