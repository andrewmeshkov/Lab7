package client.commands;


import client.Client;
import client.network.UDPClient;
import common.exceptions.*;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import common.utility.Commands;
import common.utility.Console;

import java.io.IOException;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
  private final Console console;
  private final UDPClient client;

  public Clear(Console console, UDPClient client) {
    super("clear");
    this.console = console;
    this.client = client;
  }

  /**
   * Выполняет команду
   * @return Успешность выполнения команды.
   */
  @Override
  public boolean apply(String[] arguments) {
    try {
      if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

      Response response = client.sendAndReceiveCommand(new Request(Commands.CLEAR, Client.getCurrentUser()));
      if (response.getStatus() == Status.ERROR) {
        throw new APIException(response.getString());
      }

      console.println("Коллекция очищена!");
      return true;
    } catch (WrongAmountOfElementsException exception) {
      console.printError("Неправильное количество аргументов!");
      console.println("Nспользование: '" + getName() + "'");
    } catch(IOException e) {
      console.printError("Ошибка взаимодействия с сервером");
    } catch (APIException | ErrorResponseException e) {
      console.printError(e.getMessage());
    }
    return false;
  }
}
