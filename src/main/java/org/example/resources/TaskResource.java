package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.TaskDAO;
import org.example.models.Task;

import java.util.List;
import java.util.Optional;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final TaskDAO taskDAO;

    public TaskResource(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @GET
    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getTaskById(@PathParam("id") Long id) {
        Optional<Task> task = taskDAO.findById(id);
        return task.map(value -> Response.ok(value).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Task createTask(Task task) {
        return taskDAO.create(task);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") Long id) {
        Optional<Task> task = taskDAO.findById(id);
        if (task.isPresent()) {
            taskDAO.delete(task.get());
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
