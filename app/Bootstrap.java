import java.util.List;

import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {
    @Override
    public void doJob() {
        if (User.count() == 0)
            Fixtures.load("initial-data.yaml");
        else {
            List<User> users = User.findAll();
            for (User user : users) {
                System.out.println(user.name);
            }
        }
    }
}
