package org.example;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.example.configurations.TaskTrackerConfiguration;
import org.example.dao.TaskDAO;
import org.example.models.Task;
import org.example.resources.TaskResource;
import org.hibernate.SessionFactory;
import io.dropwizard.hibernate.HibernateBundle;

public class TaskTrackerApplication extends Application<TaskTrackerConfiguration> {

    private final HibernateBundle<TaskTrackerConfiguration> hibernateBundle = new HibernateBundle<TaskTrackerConfiguration>(Task.class) {
        @Override
        public org.hibernate.cfg.Configuration getHibernateConfiguration() {
            return super.getHibernateConfiguration();
        }

        @Override
        public io.dropwizard.db.DataSourceFactory getDataSourceFactory(TaskTrackerConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(String[] args) throws Exception {
        new TaskTrackerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<TaskTrackerConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(TaskTrackerConfiguration configuration, Environment environment) {
        final SessionFactory sessionFactory = hibernateBundle.getSessionFactory();
        final TaskDAO taskDAO = new TaskDAO(sessionFactory);
        final TaskResource taskResource = new TaskResource(taskDAO);

        environment.jersey().register(taskResource);
    }
}
