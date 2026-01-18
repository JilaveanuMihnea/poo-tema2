package main.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.milestones.Milestone;
import main.milestones.MilestoneHandler;
import main.users.UserHandler;
import main.utils.CommandInput;
import main.utils.OutputHandler;

public class ViewMilestonesCommand extends BaseCommand implements Command {
    private MilestoneHandler milestoneHandler;
    public ViewMilestonesCommand(UserHandler userHandler,
                                    MilestoneHandler milestoneHandler,
                                 CommandInput commandInput,
                                 OutputHandler outputHandler,
                                 ObjectMapper mapper) {
        super(userHandler, commandInput, outputHandler, mapper);
        this.milestoneHandler = milestoneHandler;
    }

    @Override
    public void execute() {
        ObjectNode outputNode = createBaseOutputNode("viewMilestones");
        if (!validate(outputNode)) {
            outputHandler.addOutput(outputNode);
            return;
        }
        outputNode.set("milestones", milestoneHandler.getMilestonesArrayNode(
                mapper, commandInput.getTimestamp(),
                userHandler.getUser(commandInput.getUsername())
        ));
        outputHandler.addOutput(outputNode);
    }

    @Override
    public void undo() {

    }
}
