package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.milestones.MilestoneHandler;
import main.tickets.TicketHandler;
import main.users.UserHandler;
import main.utils.ProjectState;
import main.utils.CommandInput;
import main.utils.InputLoader;
import main.utils.OutputHandler;
import main.commands.*;

/**
 * main.App represents the main application logic that processes input commands,
 * generates outputs, and writes them to a file
 */
public class App {
    private App() {
    }

    private static final String INPUT_USERS_FIELD = "input/database/users.json";

    private static final ObjectWriter WRITER =
            new ObjectMapper().writer().withDefaultPrettyPrinter();

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static ProjectState projectState = ProjectState.TESTING;


    /**
     * Runs the application: reads commands from an input file,
     * processes them, generates results, and writes them to an output file
     *
     * @param inputPath path to the input file containing commands
     * @param outputPath path to the file where results should be written
     */
    public static void run(final String inputPath, final String outputPath) throws IOException {
        List<ObjectNode> outputs = new ArrayList<>();
        OutputHandler outputHandler = new OutputHandler(outputs);

        // Loading input
        InputLoader input = new InputLoader(inputPath, INPUT_USERS_FIELD);
        List<CommandInput> commands = input.getCommands();

        projectState = ProjectState.TESTING;
        projectState.startTestingPeriod(commands.getFirst().getTimestamp());


        UserHandler userHandler = UserHandler.getInstance();
        userHandler.loadUsers(input.getUsers());

        MilestoneHandler milestoneHandler = MilestoneHandler.getInstance();
        TicketHandler ticketHandler = TicketHandler.getInstance();


        // TODO 2: process commands.

        for (CommandInput command : commands) {
            if (projectState == ProjectState.TESTING && projectState.updateProjectState(command.getTimestamp())) {
                projectState = ProjectState.DEVELOPMENT;
            }

            if (command.getCommand().equals("reportTicket")) {
                new ReportTicketCommand(
                        ticketHandler,
                        userHandler,
                        command,
                        projectState,
                        outputHandler,
                        MAPPER
                ).execute();
            } else if (command.getCommand().equals("viewTickets")) {
                new ViewTicketsCommand(
                        ticketHandler,
                        userHandler,
                        command,
                        outputHandler,
                        MAPPER
                ).execute();
            } else if (command.getCommand().equals("createMilestone")) {
                new CreateMilestoneCommand(
                        ticketHandler,
                        userHandler,
                        milestoneHandler,
                        command,
                        outputHandler,
                        MAPPER
                ).execute();
            } else if (command.getCommand().equals("viewMilestones")) {
                new ViewMilestonesCommand(
                        userHandler,
                        milestoneHandler,
                        command,
                        outputHandler,
                        MAPPER
                ).execute();
            }
        }

        // TODO 3: create objectnodes for output, add them to outputs list.

        // DO NOT CHANGE THIS SECTION IN ANY WAY
        try {
            File outputFile = new File(outputPath);
            outputFile.getParentFile().mkdirs();
            WRITER.withDefaultPrettyPrinter().writeValue(outputFile, outputs);
        } catch (IOException e) {
            System.out.println("error writing to output file: " + e.getMessage());
        }
    }

}
