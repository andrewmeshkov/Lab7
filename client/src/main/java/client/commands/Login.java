package client.commands;

import client.Client;
import client.forms.UserForm;
import client.network.UDPClient;
import common.exceptions.*;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import common.network.User;
import common.utility.Commands;
import common.utility.Console;

import java.io.IOException;

/**
 * Аутентифицирует пользователя по логину и паролю.
 */
public class Login extends Command {
  private final Console console;
  private final UDPClient client;

  public Login(Console console, UDPClient client) {
    super("login");
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
      //if (SessionHandler.getCurrentUser() != null) {console.println("Вы уже вошли в аккаунт!"); ;return true;}
      if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
      console.println("Вход в аккаунт:");

      User user = (new UserForm(console)).build(true);

      Response response = client.sendAndReceiveCommand(new Request(Commands.LOGIN, user));
      if (response.getStatus() == Status.ERROR) {
        console.printError(response.getString());
        return false;
      }

      Client.setCurrentUser(response.getUser());
      console.println("Пользователь " + response.getUser().getName() +
        " с id=" + response.getUser().getId() + " успешно аутентифицирован!");
      return true;

    } catch (WrongAmountOfElementsException exception) {
      console.printError("Неправильное количество аргументов!");
      console.println("Nспользование: '" + getName() + "'");
    } catch (InvalidFormException exception) {
      console.printError("Введенные данные не валидны! Пользователь на аутентифицирован");
    } catch(IOException | InvalidInputException e) {
      console.printError("Ошибка взаимодействия с сервером");
    } catch (ErrorResponseException e) {
      console.printError(e.getMessage());
    }
    return false;
  }
}
