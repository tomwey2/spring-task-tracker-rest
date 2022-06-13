package de.tom.demo.taskapp.entities

data class LoginForm(val username: String, val password: String)
data class RegisterForm(val name: String, val username: String, val email: String, val password: String)

data class TaskForm(val text: String, val description: String?, val day: String, val reminder: Boolean,
                    val state: String?)
data class ResponseMessage(val message: String, val text: String?)
data class LoginResponseMessage(val username: String, val roles: List<String>, val accessToken: String, val refreshToken: String)

data class RefreshTokenRequestMessage(val refreshToken: String)
data class RefreshTokenResponseMessage(val accessToken: String, val refreshToken: String)


