package com.free.assignmentapplication.data.local

interface LocalRepository {

    fun saveString(key: String, value: String)
    fun returnString(key: String): String

}