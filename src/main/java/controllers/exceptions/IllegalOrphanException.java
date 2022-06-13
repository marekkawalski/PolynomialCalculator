package controllers.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * This class was generated automatically.
 *
 * @author Marek Kawalski
 * @version 1.0
 */
public class IllegalOrphanException extends Exception {

    private List<String> messages;

    public IllegalOrphanException(List<String> messages) {
        super((messages != null && messages.size() > 0 ? messages.get(0) : null));
        if (messages == null) {
            this.messages = new ArrayList<String>();
        } else {
            this.messages = messages;
        }
    }

    public List<String> getMessages() {
        return messages;
    }
}