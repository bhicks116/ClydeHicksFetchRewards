package com.darkstarstudios.fetchrewardsexercise.model

import kotlinx.serialization.Serializable

@Serializable
data class Reward(val id: Int, val listId: Int, val name: String?) {
    override fun toString(): String {
        return "Reward(id=$id, listId=$listId, name='$name')"
    }
}
