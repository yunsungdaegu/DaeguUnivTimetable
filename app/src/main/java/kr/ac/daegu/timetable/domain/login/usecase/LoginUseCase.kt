package kr.ac.daegu.timetable.domain.login.usecase

import kr.ac.daegu.timetable.domain.login.repository.LoginRepository

class LoginUseCase(private val repo: LoginRepository) {

    suspend operator fun invoke(
        studentId: String,
        password: String
    ) = runCatching {
        repo.login(studentId, password)
    }
}