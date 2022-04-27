package de.tom.demo.springrestfulwebservice.config

import de.tom.demo.springrestfulwebservice.entities.Task
import de.tom.demo.springrestfulwebservice.entities.tasks.TaskRepository
import de.tom.demo.springrestfulwebservice.entities.users.UserRepository
import de.tom.demo.springrestfulwebservice.entities.users.UserService
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
class DataConfiguration {
    val testTasks = listOf(
        Task(null, "Food shopping", LocalDateTime.of(2022, 2, 24, 18, 0), true),
        Task(null, "Doctor appointment", LocalDateTime.of(2022, 2, 24, 18, 0), true),
        Task(null, "School party preparation", LocalDateTime.of(2022, 2, 24, 18, 0), true),
    )

    @Bean
    fun databaseInitializer(
        userService: UserService,
        userRepository: UserRepository,
        taskRepository: TaskRepository) = ApplicationRunner {

        userService.registerUser("John Doe", "john.doe@test.com", "1234")
        userService.registerUser("Jane Doe", "jane.doe@test.com", "1234")
        userService.registerUser("Admin", "admin@test.com", "1234", "ROLE_ADMIN")

        testTasks.map {
            taskRepository.save(it)
        }
    }

}

