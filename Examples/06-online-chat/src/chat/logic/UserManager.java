package chat.logic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author blecherl
 */
public class UserManager {

    private final Set<String> usersSet;

    public UserManager() {
        usersSet = new HashSet<>();
    }

    public void addUser(String username) {
        usersSet.add(username);
    }

    public void removeUser(String username) {
        usersSet.remove(username);
    }

    public Set<String> getUsers() {
        return Collections.unmodifiableSet(usersSet);
    }

    public boolean isUserExists(String username) {
        return usersSet.contains(username);
    }
}
