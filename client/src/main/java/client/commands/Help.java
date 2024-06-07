package client.commands;


import client.Client;
import client.network.UDPClient;
import common.exceptions.ErrorResponseException;
import common.network.Request;
import common.network.Response;
import common.utility.Commands;
import common.utility.Console;

import java.io.IOException;

/**
 * Выводит справку по доступным командам
 */
public class Help extends Command {
  private final Console console;
  private final UDPClient client;

  public Help(Console console, UDPClient client) {
    super("help");
    this.console = console;
    this.client = client;
  }

  /**
   * Выполняет команду
   * @return Успешность выполнения команды.
   */
  @Override
  public boolean apply(String[] arguments) {
    if (!arguments[1].isEmpty()) {
      console.printError("Неправильное количество аргументов!");
      console.println("Nспользование: '" + getName() + "'");
      return false;
    }

    try {
      Response response = client.sendAndReceiveCommand(new Request(Commands.HELP, Client.getCurrentUser()));
      console.print(response.getString());
      return true;
    } catch(IOException e) {
      console.printError("Ошибка взаимодействия с сервером");
    } catch (ErrorResponseException e) {
      console.printError(e.getMessage());
    }
    return false;
  }
}
