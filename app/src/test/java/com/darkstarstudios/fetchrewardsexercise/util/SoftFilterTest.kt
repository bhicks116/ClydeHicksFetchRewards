package com.darkstarstudios.fetchrewardsexercise.util

import com.darkstarstudios.fetchrewardsexercise.model.Reward

import org.junit.Test
import org.junit.Assert.*

class SoftFilterTest {
    @Test
    fun sort_sortsByListIdThenNameAscending() {
        val rewards = listOf(
            Reward(id = 1, listId = 1, name = "B"),
            Reward(id = 2, listId = 2, name = "A"),
            Reward(id = 3, listId = 2, name = "B"),
            Reward(id = 4, listId = 1, name = "A")
        )
        val sortedRewards = SoftFilter.sort(rewards, SORT_ORDER.ASCENDING, SORT_BY.LIST_ID_THEN_NAME, FILTER_BY.ALL)
        assertEquals(
            mapOf(
                1 to listOf(
                    Reward(id = 4, listId = 1, name = "A"),
                    Reward(id = 1, listId = 1, name = "B")),
                2 to listOf(
                    Reward(id = 2, listId = 2, name = "A"),
                    Reward(id = 3, listId = 2, name = "B")),
            ),
            sortedRewards
        )
    }

    @Test
    fun sort_sortsByNameThenListIdDescending() {
        val rewards = listOf(
            Reward(id = 1, listId = 1, name = "B"),
            Reward(id = 2, listId = 2, name = "A"),
            Reward(id = 3, listId = 2, name = "B"),
            Reward(id = 4, listId = 1, name = "A")
        )
        val sortedRewards = SoftFilter.sort(rewards, SORT_ORDER.DESCENDING, SORT_BY.NAME_THEN_LIST_ID, FILTER_BY.ALL)
        assertEquals(
            mapOf(
                1 to listOf(
                    Reward(id = 1, listId = 1, name = "B"),
                    Reward(id = 4, listId = 1, name = "A")),
                2 to listOf(
                    Reward(id = 3, listId = 2, name = "B"),
                    Reward(id = 2, listId = 2, name = "A")),
            ),
            sortedRewards
        )
    }

    @Test
    fun sort_filtersOutEmptyNames() {
        val rewards = listOf(
            Reward(id = 1, listId = 1, name = "B"),
            Reward(id = 2, listId = 2, name = ""),
            Reward(id = 3, listId = 2, name = "B"),
            Reward(id = 4, listId = 1, name = null)
        )
        val sortedRewards = SoftFilter.sort(rewards, SORT_ORDER.ASCENDING, SORT_BY.LIST_ID_THEN_NAME, FILTER_BY.NOT_EMPTY)
        assertEquals(
            mapOf(
                1 to listOf(
                    Reward(id = 1, listId = 1, name = "B")),
                2 to listOf(
                    Reward(id = 3, listId = 2, name = "B")),
            ),
            sortedRewards
        )
    }

    @Test
    fun sort_handlesEmptyList() {
        val rewards = emptyList<Reward>()
        val sortedRewards = SoftFilter.sort(rewards, SORT_ORDER.ASCENDING, SORT_BY.LIST_ID_THEN_NAME, FILTER_BY.ALL)
        assertTrue(sortedRewards.isEmpty())
    }

    @Test
    fun sort_handlesSingleElementList() {
        val rewards = listOf(Reward(id = 1, listId = 1, name = "A"))
        val sortedRewards = SoftFilter.sort(rewards, SORT_ORDER.ASCENDING, SORT_BY.LIST_ID_THEN_NAME, FILTER_BY.ALL)
        assertEquals(mapOf(1 to listOf(Reward(id = 1, listId = 1, name = "A"))), sortedRewards)
    }
}