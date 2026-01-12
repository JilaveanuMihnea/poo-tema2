package main.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic skeleton for loading input JSON file as a Map.
 * Students should implement deeper parsing themselves.
 */
@Getter
public final class InputLoader {
    private final ArrayList<CommandInput> commands;
    private final ArrayList<UserInput> users;

    public InputLoader(final String filePathCommands, final String filePathUsers) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<CommandInput> list = mapper.readValue(new File(filePathCommands),
                        new com.fasterxml.jackson.core.type.TypeReference<List<CommandInput>>() {});
        this.commands = new ArrayList<>(list);
        List<UserInput> users = mapper.readValue(new File(filePathUsers),
                new com.fasterxml.jackson.core.type.TypeReference<List<UserInput>>() {});
        this.users = new ArrayList<>(users);
    }
}