package IntegrationTesting.stubs;

import java.util.ArrayList;

import com.mycompany.swtproject.User;
import com.mycompany.swtproject.UserFilereader;

public class UserFilereaderStub extends UserFilereader {

    @Override
    public ArrayList<User> ReadUsers(String path) {
        ArrayList<User> users = new ArrayList<>();

        User u1 = new User("john doe", "123456789", new String[]{"I123"});
        users.add(u1);

        User u2 = new User("jane smith", "987654321", new String[]{"TM456"});
        users.add(u2);

        return users;
    }
}
