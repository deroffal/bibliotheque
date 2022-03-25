package fr.deroffal.bibliotheque.livre.domain

data class Page<T>(val datas: List<T>, val totalPage: Int, val totalElements: Long)
