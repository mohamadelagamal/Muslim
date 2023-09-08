package com.example.muslim.model.ziker

import java.io.Serializable

data class ZikerNames(val id:Int?=null
                      , val category:String?=null
                      , var image:String?=null
                      ,val audio:String?=null
                      , val filename:String?=null
                      , val arry: List<ZikerContent>?=null):Serializable
data class ZikerContent(val id:Int?=null
                        ,val text:String?=null
                        ,val count:Int?=null
                        ,val audio:String?=null
                        ,val filename:String?=null):Serializable
