package com.example.muslim.database.ziker

data class ZikerNames(val id:Int?=null
                      ,val category:String?=null
                      ,val image:Int?=null
                      ,val text:String?=null
                      ,val filename:String?=null
                      ,val zikerContent: List<ZikerContent>?=null)

