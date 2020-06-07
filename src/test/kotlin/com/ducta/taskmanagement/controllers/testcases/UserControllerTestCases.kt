package com.ducta.taskmanagement.controllers.testcases

import com.ducta.taskmanagement.dto.UserCredentials
import com.ducta.taskmanagement.dto.UserRegisterDto

class UserControllerTestCases {

    companion object {

        fun getTestCaseForLoginFailed() = UserCredentials(
                "fake-user",
                "Fake-password"
        )

        fun getTestCaseForLoginSuccess() = UserCredentials(
                "admin",
                "Admin@1997"
        )

        fun getTestCaseRegisterUsernameAlreadyExisted() = UserRegisterDto(
                "admin",
                "testemail@gmail.com",
                "Password@123",
                "Ta Minh Duc"
        )

        fun getTestCaseRegisterEmailAlreadyExisted() = UserRegisterDto(
                "newaccount",
                "ab@hotmail.net",
                "asdfA@1231",
                "Test name"
        )

        fun getTestCaseRegisterUser() = UserRegisterDto(
                "tminhduc2811",
                "tminhduc2811@gmail.com",
                "Tcddd2811@1997",
                "Ta Minh Duc"
        )

        fun getTestCasePasswordNotStrongEnough() = UserRegisterDto(
                "testaccount",
                "email@gmail.com",
                "123456T",
                "Ta Minh Duc"
        )
    }
}