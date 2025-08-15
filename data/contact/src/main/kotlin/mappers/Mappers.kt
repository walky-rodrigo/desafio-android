package mappers

import database.entity.ContactEntity
import model.Contact
import remote.dto.ContactDto

fun ContactDto.toContact(): Contact? {
    return Contact(
        id = this.id ?: return null,
        name = this.name ?: return null,
        username = this.username ?: return null,
        img = this.img ?: return null
    )
}

fun ContactEntity.toContact(): Contact {
    return Contact(
        id = this.id,
        name = this.name ,
        username = this.username,
        img = this.img
    )
}

fun Contact.toContactEntity(): ContactEntity {
    return ContactEntity(
        id = this.id,
        name = this.name ,
        username = this.username,
        img = this.img
    )
}

fun List<ContactDto>.toContactList(): List<Contact> {
    return this.mapNotNull { it.toContact() }
}

fun List<ContactEntity>.toContactModelList(): List<Contact> {
    return this.map { it.toContact() }
}

fun List<Contact>.toContactEntityList(): List<ContactEntity> {
    return this.map { it.toContactEntity() }
}


