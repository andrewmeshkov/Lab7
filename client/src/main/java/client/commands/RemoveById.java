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
 * Удаляет элемент из коллекции.
 */
public class RemoveById extends Command {
  private final Console console;
  private final UDPClient client;

  public RemoveById(Console console, UDPClient client) {
    super("remove_by_id");
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
      if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
      var id = Integer.parseInt(arguments[1]);
      System.out.println(id);

      Response response = client.sendAndReceiveCommand(new Request(Commands.REMOVE_BY_ID, Client.getCurrentUser(), new String[]{id+""}));
      System.out.println(response.getStatus());
      if(response.getStatus() == Status.OWNER_ERROR){
        console.printError("Попытка удаление чужого продукта!");
        return true;
      }
      if (response.getStatus() == Status.ERROR) {
        throw new APIException(response.getString());
      }

      console.println("Продукт успешно удален.");
      return true;
    } catch (WrongAmountOfElementsException exception) {
      console.printError("Неправильное количество аргументов!");
      console.println("Nспользование: '" + getName() + "'");
    } catch (NumberFormatException exception) {
      console.printError("ID должен быть представлен числом!");
    } catch(IOException e) {
      console.printError("Ошибка взаимодействия с сервером");
    } catch (APIException | ErrorResponseException e) {
      console.printError(e.getMessage());
    }
    return false;
  }
}
