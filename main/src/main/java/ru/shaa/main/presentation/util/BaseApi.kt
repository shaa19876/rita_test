package ru.shaa.main.presentation.util

sealed class BaseApi (val name: String, var url: String){
    class Dog: BaseApi("Dog api", "https://dog.ceo/api/breeds/image/")
    class Nationalize: BaseApi("Nationalize api", "https://api.nationalize.io/")
    class Custom: BaseApi("Custom api", "")
}