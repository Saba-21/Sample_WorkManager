package com.saba.sample_workmanager

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*

const val DATA_KEY = "DATA_KEY"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = workDataOf(DATA_KEY to "saba")

        val constraints = Constraints
            .Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .setRequiresStorageNotLow(true)
            .setRequiresDeviceIdle(true)
            .build()

        val workerFirst = OneTimeWorkRequestBuilder<WorkerFirst>()
            .setInputData(input)
            .setConstraints(constraints)
            .build()

        val workerSecond = OneTimeWorkRequestBuilder<WorkerSecond>()
            .setInputData(input)
            .setConstraints(constraints)
            .build()

        val operation = WorkManager
            .getInstance()
            .beginWith(workerFirst)
            .then(workerSecond)
            .enqueue()

        operation.state.observe(this, Observer {
            it?.let { state: Operation.State ->
                Log.e("state", state.toString())
            }
        })

        WorkManager
            .getInstance()
            .getWorkInfoByIdLiveData(workerFirst.id)
            .observe(this, Observer {
                it?.let {workInfo ->
                    workInfo
                }
            })

        WorkManager
            .getInstance()
            .getWorkInfoByIdLiveData(workerSecond.id)
            .observe(this, Observer {
                it?.let {workInfo ->
                    workInfo
                }
            })



    }
}
