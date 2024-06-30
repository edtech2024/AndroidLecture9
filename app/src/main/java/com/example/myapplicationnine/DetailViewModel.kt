package com.example.myapplicationnine

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Item
import com.example.domain.UseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class DetailViewModel @Inject constructor(val useCase: UseCase, val bundle: Bundle?) : ViewModel() {

    var argAction: String? = null
    var argId: Int? = null
    var argTitle: String = ""
    var argDescription: String = "null"
    var argPriority: Int? = null
    var argType: Int = 0
    var argType1: Boolean
    var argType2: Boolean
    var argCount: String = "0"
    var argFrequency: String = "0"
    var argColor: String = "0"
    var argUid: String = ""

    var simpleDateFormat = SimpleDateFormat("ddMMyyyyHHmmss")
    var argDate: Long = simpleDateFormat.format(Date()).toLong()
    var argDateTime: String = simpleDateFormat.format(Date()).toString()

    var argDoneDatesL: List<Long> = listOf()
    var argDoneDates: String = ""

    init{
        argType1 = false
        argType2 = false

        readBundle(bundle)
    }

    fun chooseTypeFirst(){
        argType1 = true
        argType2 = false
        argType = 0
    }

    fun chooseTypeSecond(){
        argType1 = false
        argType2 = true
        argType = 1
    }

    private fun readBundle(bundle:Bundle?){
        argAction = bundle?.getString("Action", "")
        argId = bundle?.getInt("Id",0 )
        argTitle = bundle?.getString("Title", "Naming").toString()
        argDescription = bundle?.getString("Description", "text").toString()
        argPriority = bundle?.getString("Priority", "2")!!.toInt()
        argType = bundle?.getString("TYPE", "0")!!.toInt()
        argCount = bundle?.getString("Count", "0").toString()
        argFrequency = bundle?.getString("Frequency", "0").toString()
        argColor = bundle?.getString("Color", "0").toString()
        argUid = bundle?.getString("Uid", "").toString()
        argDoneDates = bundle?.getString("Done_dates", "0").toString()

        if (argType != 0) {
            argType2 = true
            argType1 = false
        }

        if (argType != 1) {
            argType1 = true
            argType2 = false
        }

    }

    // Launching a new coroutine to insert the data in a non-blocking way
    private fun callCreateMethod(item: Item){
        viewModelScope.launch {
            useCase.create(item)
        }
    }

    // Launching a new coroutine to update the data in a non-blocking way
    private fun callUpdateMethod(item: Item){
        viewModelScope.launch {
            useCase.update(item)
        }
    }

    fun callClick(){
        if (argAction == "Create"){
            callCreateMethod(makeItem())
        } else {
            callUpdateMethod(makeItem())
        }
    }

    private fun makeItem(): Item {

        val item = Item(id = null, uid = argUid,
            title = argTitle, description = argDescription,
            priority = argPriority,
            type = argType.toInt(),
            count = argCount?.toInt(),
            frequency = argFrequency?.toInt(),
            color = 0, date = argDate.toString().toLong(),
            done_dates = argDoneDatesL
        )

        return item
    }

}