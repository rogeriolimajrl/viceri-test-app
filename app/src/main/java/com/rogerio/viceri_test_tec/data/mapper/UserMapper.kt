package com.rogerio.viceri_test_tec.data.mapper

import com.rogerio.viceri_test_tec.data.local.UserEntity
import com.rogerio.viceri_test_tec.data.remote.UserDto

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        username = username,
        email = email,
        city = address?.city,
        phone = phone,
        website = website,
        companyName = company?.name
    )
}
