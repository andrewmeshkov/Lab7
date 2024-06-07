package client.commands;

import client.Client;
import client.network.UDPClient;
import common.exceptions.APIException;
import common.exceptions.ErrorResponseException;
import common.exceptions.WrongAmountOfElementsException;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import common.utility.Commands;
import common.utility.Console;

import java.io.IOException;

public class CountLessThanMetersAboveSeaLevel extends Command{


  private final Console console;
  private final UDPClient client;

  public CountLessThanMetersAboveSeaLevel(Console console, UDPClient client) {
    super("count_less_than_meters_above_sea_level");
    this.console = console;
    this.client = client;
  }

  @Override
  public boolean apply(String[] arguments) {
    try {
      if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
      var meters = Integer.parseInt(arguments[1]);

      Response response = client.sendAndReceiveCommand(new Request(Commands.COUNT_LESS_THAN_SEA, Client.getCurrentUser(), new String[]{meters+""}));
      if (response.getStatus() == Status.ERROR) {
        throw new APIException(response.getString());
      }

      console.println(response.getString());
      return true;
    } catch (WrongAmountOfElementsException exception) {
      console.printError("Неправильное количество аргументов!");
      console.println("Nспользование: '" + getName() + "'");
    } catch (NumberFormatException exception) {
      console.printError("Высота должна быть представлена числом!");
    } catch(IOException e) {
      console.printError("Ошибка взаимодействия с сервером");
    } catch (APIException | ErrorResponseException e) {
      console.printError(e.getMessage());
    }
    return false;
  }
}
