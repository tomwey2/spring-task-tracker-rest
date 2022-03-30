package de.tom.demo.springrestfulwebservice.entities.tasks

import de.tom.demo.springrestfulwebservice.TaskNotFoundException
import de.tom.demo.springrestfulwebservice.entities.Task
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

/**
 * Testing the TaskService with JUnit5 and MockK.
 * Mocking the TaskRepository
 * Using the AssertJ instead of JUnit (jupiter) assertions because AssertJ is easy to use and more readable.
 */

// no SpringBootTest annotations in order to execute the test functions fast and without the framework
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockKExtension::class)
class TaskServiceTest {
    val testTask = Task("4711", "New Task",
        LocalDateTime.of(2022, 2, 24, 18, 0), true)
    val idExist = testTask.id ?: ""
    val idNotExist = "5678"

    val repository = mockk<TaskRepository>()
    val underTest = TaskService(repository)

    @BeforeAll
    fun setup() {
        println(">> Mocking the repository calls with test data")
        every { repository.findTasks() } returns listOf<Task>()
        every { repository.findByIdOrNull("") } returns null
        every { repository.findByIdOrNull(idNotExist) } returns null
        every { repository.findByIdOrNull(idExist) } returns testTask

        every { repository.save(testTask) } returns testTask
        every { repository.delete(any()) } returns Unit
    }

    @Test
    @DisplayName("Test the getTasks() function")
    fun getTasks() {
        underTest.getTasks()
        verify { underTest.getTasks() }
    }

    @Test
    @DisplayName("Test the getTask(id) with existing id")
    fun getTask() {
        assertEquals(testTask, underTest.getTask(idExist))
    }

    @Test()
    @DisplayName("Test the getTask(id) with not existing id")
    fun failedGetTask() {
        assertThrows( TaskNotFoundException::class.java) { underTest.getTask("") }
        assertThrows( TaskNotFoundException::class.java) { underTest.getTask(idNotExist) }
    }

    @Test
    @DisplayName("Test the addTask(task)")
    fun addTask() {
        assertEquals(testTask, underTest.addTask(testTask))
    }

    @Test
    @DisplayName("Test the deleteTask(id) with existing id")
    fun deleteTask() {
        justRun { underTest.deleteTask(idExist) }
    }

    @Test()
    @DisplayName("Test the deleteTask(id) with not existing id")
    fun failedDeleteTask() {
        assertThrows( TaskNotFoundException::class.java) { underTest.deleteTask("") }
        assertThrows( TaskNotFoundException::class.java) { underTest.deleteTask(idNotExist) }
    }

    @Test
    @DisplayName("Test the updateTask(id) with existing id")
    fun updateTask() {
        assertEquals(testTask, underTest.updateTask(idExist, testTask))
    }

    @Test
    @DisplayName("Test the updateTask(id) with not existing id")
    fun failedUpdateTask() {
        assertThrows( TaskNotFoundException::class.java) { underTest.updateTask(idNotExist, testTask) }
    }

}