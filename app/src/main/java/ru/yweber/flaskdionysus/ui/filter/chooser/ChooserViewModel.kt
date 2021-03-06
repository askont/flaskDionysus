package ru.yweber.flaskdionysus.ui.filter.chooser

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.state.FilterChooserItemState
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.model.entity.FilterEntity
import ru.yweber.flaskdionysus.model.entity.ItemTypeFilter
import ru.yweber.flaskdionysus.model.interactor.FilterUseCase
import ru.yweber.flaskdionysus.system.ResourceManager
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.filter.chooser.state.ChooserState
import toothpick.InjectConstructor

/**
 * Created on 22.04.2020
 * @author YWeber */

@InjectConstructor
class ChooserViewModel(
    private val type: ItemTypeFilter,
    private val useCase: FilterUseCase,
    private val globalRouter: GlobalRouter,
    private val resourceManager: ResourceManager
) : BaseViewModel<ChooserState>() {
    override val defaultState: ChooserState
        get() = ChooserState(
            items = listOf(),
            showSearch = type == ItemTypeFilter.NOT_CONTAINS || type == ItemTypeFilter.CONTAINS
        )

    private val cacheCurrentSelect = mutableListOf<Int>()

    init {
        allComponent()
    }

    private fun allComponent(isInit: Boolean = true) {
        useCase.getFilter()
            .take(1)
            .onEach { list ->
                val ingredient = convertToItemsState(list)
                action.value = currentState.copy(
                    items = ingredient,
                    isInitWindows = isInit,
                    searchEmpty = false,
                    searchAdvice = typeToSearchAdvice(type)
                )
            }
            .onCompletion {
                isSelectedOldItems()
            }
            .launchIn(viewModelScope)

    }

    private fun convertToItemsState(filters: List<FilterEntity>): List<FilterChooserItemState> {
        return filters.filter {
            when (type) {
                ItemTypeFilter.CONTAINS -> {
                    it.type == FilterEntity.Type.INGREDIENT
                }
                ItemTypeFilter.NOT_CONTAINS -> {
                    it.type == FilterEntity.Type.INGREDIENT
                }
                ItemTypeFilter.FORTRESS -> {
                    it.type == FilterEntity.Type.FORTRESS_LEVELS
                }
                ItemTypeFilter.VOLUMES -> {
                    it.type == FilterEntity.Type.VOLUMES
                }
                ItemTypeFilter.COMPLICATION -> {
                    it.type == FilterEntity.Type.COMPLICATION_LEVELS
                }
                ItemTypeFilter.OTHER -> {
                    it.type == FilterEntity.Type.OTHER
                }
            }
        }.map {
            FilterChooserItemState(it.id, it.name)
        }.map {
            if (cacheCurrentSelect.contains(it.id)) {
                it.copy(select = true)
            } else it
        }
    }

    private fun typeToSearchAdvice(type: ItemTypeFilter): String {
        return when (type) {
            ItemTypeFilter.CONTAINS -> {
                resourceManager.getString(R.string.advice_ingredient)
            }
            ItemTypeFilter.NOT_CONTAINS -> {
                resourceManager.getString(R.string.advice_not_ingredient)
            }
            ItemTypeFilter.FORTRESS -> {
                resourceManager.getString(R.string.advice_fortress)
            }
            ItemTypeFilter.VOLUMES -> {
                resourceManager.getString(R.string.advice_volumes)
            }
            ItemTypeFilter.COMPLICATION -> {
                resourceManager.getString(R.string.advice_complication)
            }
            ItemTypeFilter.OTHER -> {
                resourceManager.getString(R.string.advice_other)
            }
        }
    }


    private fun isSelectedOldItems() {
        useCase.getSelectComponent()
            .onEach { selectMap ->
                val selected = selectMap[type] ?: listOf()
                selected.forEach {
                    if (!cacheCurrentSelect.contains(it)) {
                        cacheCurrentSelect.add(it)
                    }
                }
                val selectedItem = currentState.items.map {
                    if (cacheCurrentSelect.contains(it.id)) {
                        it.copy(select = true)
                    } else {
                        it
                    }
                }
                action.value = currentState.copy(items = selectedItem, isInitWindows = false)
            }
            .launchIn(viewModelScope)
    }

    fun selectComponent(id: Int) {
        if (cacheCurrentSelect.contains(id)) {
            cacheCurrentSelect.remove(id)
        } else {
            cacheCurrentSelect.add(id)
        }
        action.value = currentState.copy(items = currentState.items.map {
            if (id == it.id) {
                it.copy(select = !it.select)
            } else {
                it
            }
        }, isInitWindows = false)
    }

    fun searchComponent(name: String?) {
        val searchName = name ?: ""
        if (searchName.isNotEmpty()) {
            launch {
                useCase.searchComponent(searchName)
                    .take(1)
                    .onCompletion { isSelectedOldItems() }
                    .collect { filters ->
                        val ingredient = convertToItemsState(filters)
                        action.value = currentState.copy(
                            items = ingredient,
                            isInitWindows = false,
                            searchEmpty = ingredient.isEmpty(),
                            search = searchName
                        )
                    }
            }
        } else {
            allComponent(false)
        }
    }

    fun approveFilter() {
        launch {
            useCase.setSelect(
                type,
                cacheCurrentSelect
            )
            globalRouter.dismiss(Screens.ChooserDialogHolder(type))

        }
    }
}