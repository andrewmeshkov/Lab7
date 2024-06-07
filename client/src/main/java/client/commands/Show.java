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
 * Выводит все элементы коллекции.
 */
public class Show extends Command {
  private final Console console;
  private final UDPClient client;

  public Show(Console console, UDPClient client) {
    super("show");
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

      Response response = client.sendAndReceiveCommand(new Request(Commands.SHOW, Client.getCurrentUser()));
      if (response.getStatus() == Status.ERROR) {
        throw new APIException(response.getString());
      }

      console.println(response.getString());
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
