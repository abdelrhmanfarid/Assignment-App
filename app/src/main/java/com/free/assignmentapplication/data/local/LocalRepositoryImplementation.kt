package com.free.assignmentapplication.data.local

import com.free.assignmentapplication.data.local.LocalRepository
import com.free.assignmentapplication.data.local.prefrences.PreferencesManager
import javax.inject.Inject

class LocalRepositoryImplementation @Inject constructor(

    private val prefManager: PreferencesManager
) : LocalRepository {


    override fun saveString(key: String, value: String) {
        prefManager.saveString(key,value)
    }

    override fun returnString(key: String): String {
        return prefManager.returnString(key)
    }


}