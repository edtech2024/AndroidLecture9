package com.example.myapplicationnine

import androidx.lifecycle.*
import com.example.domain.Item
import com.example.domain.UseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(
    val useCase: UseCase
) : ViewModel() {

    var argSearch: String = ""

    private var mediatorType1: MediatorLiveData<List<Item>> = MediatorLiveData<List<Item>>()
    private var mediatorType2: MediatorLiveData<List<Item>> = MediatorLiveData<List<Item>>()

    private var liveDataType: LiveData<List<Item>> = useCase.callMethod().asLiveData()

    var tempListType1: MutableList<Item> = mutableListOf()
    var tempListType2: MutableList<Item> = mutableListOf()

    var itemsListType1: LiveData<List<Item>> = mediatorType1
    var itemsListType2: LiveData<List<Item>> = mediatorType2

    init {

        viewModelScope.launch { useCase.requestItems() }

        mediatorType1.addSource(liveDataType) { source ->
            tempListType1.clear()
            for (item in source) {
                if (item.type == 0) {
                    tempListType1.add(item)
                    mediatorType1.setValue(tempListType1)
                }
            }
        }

        mediatorType2.addSource(liveDataType) { source ->
            tempListType2.clear()
            for (item in source) {
                if (item.type == 1) {
                    tempListType2.add(item)
                    mediatorType2.setValue(tempListType2)
                }
            }
        }
    }

    fun callSortMethod() {
        sortByCount()
    }

    fun callFilterMethod() {
        filtrationByTitle(argSearch)
    }

    fun callResetFilter() {
        argSearch = ""
        sortById()

        mediatorType1.postValue(tempListType1)
        mediatorType2.postValue(tempListType2)
    }

    fun sortById() {
        tempListType1.sortBy { item -> item.id }
        mediatorType1.postValue(tempListType1)

        tempListType2.sortBy { item -> item.id }
        mediatorType2.postValue(tempListType2)
    }

    fun sortByCount() {
        tempListType1.sortBy { item -> item.count }
        mediatorType1.postValue(tempListType1)

        tempListType2.sortBy { item -> item.count }
        mediatorType2.postValue(tempListType2)
    }

    fun filtrationByTitle(title: String) {
        var filteredListT1 = tempListType1.filter { it.title == title }
        mediatorType1.postValue(filteredListT1)

        var filteredListT2 = tempListType2.filter { it.title == title }
        mediatorType2.postValue(filteredListT2)
    }

    fun itemDone(item: Item) {
        viewModelScope.launch {
            useCase.itemPressed(item)
        }
    }

}