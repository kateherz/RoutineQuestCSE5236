package com.example.routinequestcse5236.model

class TaskAdapter(private val tasks: ArrayList<Task>) {
}

enum class TaskDifficulty {EASY, MEDIUM, HARD, EXPERT}

data class Task(val name: String, val difficulty: TaskDifficulty, var completed: Boolean = false)
