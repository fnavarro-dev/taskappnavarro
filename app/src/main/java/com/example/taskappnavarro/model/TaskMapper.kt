package com.example.taskappnavarro.model

import com.example.taskappnavarro.model.room.TaskEntity
import com.example.taskappnavarro.model.model.Task


fun Task.toDataEntity(): TaskEntity = TaskEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    creationDate = this.creationDate,
    dueDate = this.dueDate,
    image = this.image,
    completed = this.isDone
)

fun TaskEntity.toDataModel(): Task = Task(
    id = this.id,
    title = this.title,
    content = this.content,
    creationDate = this.creationDate,
    dueDate = this.dueDate,
    image = this.image,
    isDone = this.completed,
)

fun List<TaskEntity>.toListDataModel(): List<Task> = this.map { it.toDataModel() }
fun List<Task>.toListDataEntity(): List<TaskEntity> = this.map { it.toDataEntity() }