package com.example.taskappnavarro.model.model

import com.google.gson.annotations.SerializedName

data class Task(
    val id: String,
    val title: String,
    val content: String,
    @SerializedName("creation_date")
    val creationDate: String,
    @SerializedName("due_date")
    val dueDate: String,
    val image: String,
    val isDone: Boolean
)