package server;

/**
 * Class which executes different server actions
 */
public class ServerCommands {

    /**
     * Executes different server actions
     * @param command Command got from the Client
     * @return Answer from the server for the Client
     */
    public static String serverAction(String command) {
        Config config = Config.getInstance();
        Difficulty difficulty = Difficulty.getInstance();
        Levels levels = Levels.getInstance();
        String serverMessage = "";
        String[] commands = command.split("-");
        switch (commands[0]) {
            case "getConfig" -> serverMessage = config.loadConfig();
            case "getDifficulties" -> serverMessage = difficulty.loadDifficulties(Integer.parseInt(commands[1]));
            case "getScenario" -> serverMessage = config.loadScenario();
            case "getRanking" -> serverMessage = ScoreBoard.getRanking();
            case "getLevel" -> serverMessage = levels.load(Integer.parseInt(commands[1]));
            case "saveScore" -> {
                ScoreBoard.saveScore(commands[1], Integer.parseInt(commands[2]));
                serverMessage = "Score saved";
            }
            default -> System.out.println("Invalid command!");
        }
        return serverMessage;
    }
}
