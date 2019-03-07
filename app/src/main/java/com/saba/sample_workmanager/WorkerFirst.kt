package com.saba.sample_workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class WorkerFirst(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val input = inputData.getString(DATA_KEY)

        val output = workDataOf(DATA_KEY to "$input+firstJob")

        return Result.success(output)

    }

}