package client.commands;


import common.utility.Console;

/**
 * Выполнить скрипт из файла.
 */
public class ExecuteScript extends Command {
  private final Console console;

  public ExecuteScript(Console console) {
    super("execute_script");
    this.console = console;
  }

  /**
   * Выполняет команду
   * @return Успешность выполнения команды.
   */
  @Override
  public boolean apply(String[] arguments) {
    if (arguments[1].isEmpty()) {
      console.println("Nспользование: '" + getName() + "'");
      return false;
    }

    console.println("Выполнение скрипта '" + arguments[1] + "'...");
    return true;
  }
}
