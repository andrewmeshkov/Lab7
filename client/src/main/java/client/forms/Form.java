package client.forms;


import common.exceptions.InvalidFormException;
import common.exceptions.InvalidInputException;

interface Form<T> {
    T build(boolean flag) throws InvalidInputException, InvalidFormException, InvalidInputException, InvalidFormException;
}
