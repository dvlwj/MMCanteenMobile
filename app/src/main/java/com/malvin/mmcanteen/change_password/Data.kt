package com.malvin.mmcanteen.change_password

class ChangePasswordStatus(val status: Int)

class Data(val status: Int, val user: User, val token: String)

class User(val id: Int, val username: String, val role: String)