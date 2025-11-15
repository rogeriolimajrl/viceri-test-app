package com.rogerio.viceri_test_tec.data.remote

data class AddressDto(
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?
)

data class CompanyDto(
    val name: String?,
    val catchPhrase: String?,
    val bs: String?
)

data class UserDto(
    val id: Int,
    val name: String?,
    val username: String?,
    val email: String?,
    val address: AddressDto?,
    val phone: String?,
    val website: String?,
    val company: CompanyDto?
)
