package com.corporation8793.medicinal_herb.herb_wp.rest.data.board

data class Post(val id : String,
                val date : String,
                val type : String,
                val title : String,
                val content : String,
                val excerpt : String,
                val author : String,
                val featured_media : String,
                val categories : String,
                val tags : String)
