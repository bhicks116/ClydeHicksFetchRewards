package com.darkstarstudios.fetchrewardsexercise

import com.darkstarstudios.fetchrewardsexercise.model.Reward

enum class SORT_ORDER {
    ASCENDING,
    DESCENDING
}

enum class SORT_BY {
    LIST_ID_THEN_NAME,
    NAME_THEN_LIST_ID
}

enum class FILTER_BY {
    ALL,
    NOT_EMPTY
}

object SoftFilter {
    fun sort(rewards: List<Reward>,
             orderBy: SORT_ORDER = SORT_ORDER.ASCENDING,
             sortBy: SORT_BY = SORT_BY.LIST_ID_THEN_NAME,
             filterBy: FILTER_BY = FILTER_BY.NOT_EMPTY) : Map<Int, List<Reward>> {
        val filteredRewards = rewards.filter {
                if (filterBy == FILTER_BY.NOT_EMPTY)
                    it.name != null && it.name.isNotEmpty()
                else
                    true
            }
        val sortedRewards = if (sortBy == SORT_BY.LIST_ID_THEN_NAME) {
                filteredRewards.sortedWith(compareBy({it.listId}, {it.name}))
            } else {
                filteredRewards.sortedWith(compareBy({it.name}, {it.listId}))
            }
        val fullySortedRewards = if (orderBy == SORT_ORDER.ASCENDING) {
                sortedRewards
            } else {
                sortedRewards.reversed()
            }
        val groupedAwards = fullySortedRewards.groupBy { it.listId }
        return groupedAwards
    }
}