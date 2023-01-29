package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {
	
	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	private TaskController taskController;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao()  {
		Task task = new Task();
		task.setDueDate(LocalDate.now());
		try {
			taskController.save(task);
			Assert.fail("Não deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task task = new Task();
		task.setTask("Descricao");
		try {
			taskController.save(task);
			Assert.fail("Não deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}	
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task task = new Task();
		task.setTask("Descricao");
		task.setDueDate(LocalDate.of(2010, 01, 01));
		try {
			taskController.save(task);
			Assert.fail("Não deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task task = new Task();
		task.setTask("Descricao");
		task.setDueDate(LocalDate.now());
		taskController.save(task);
		Mockito.verify(taskRepo).save(task);
	}
}
