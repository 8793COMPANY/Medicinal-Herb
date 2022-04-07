package com.corporation8793.medicinal_herb.herb_wp.rest.data.board

data class Post(val id : String,
                val date : String,
                val type : String,

                val title : Title,

                val content : Content,

                val excerpt : Excerpt,

                val author : String,
                val featured_media : String,
                val categories : Array<String>,
                val tags : Array<String>)

data class Title(val rendered : String)
data class Content(val rendered : String)
data class Excerpt(val rendered : String)