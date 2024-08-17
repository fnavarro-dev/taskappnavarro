package com.example.taskappnavarro.model

import com.example.taskappnavarro.model.room.DataEntity
import com.example.taskappnavarro.model.model.Task


fun Task.toDataEntity(): DataEntity = DataEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    creationDate = this.creationDate,
    dueDate = this.dueDate,
    image = this.image,
    completed = this.isDone
)

fun DataEntity.toDataModel(): Task = Task(
    id = this.id,
    title = this.title,
    content = this.content,
    creationDate = this.creationDate,
    dueDate = this.dueDate,
    image = this.image,
    isDone = this.completed,
)

fun List<DataEntity>.toListDataModel(): List<Task> = this.map { it.toDataModel() }
fun List<Task>.toListDataEntity(): List<DataEntity> = this.map { it.toDataEntity() }