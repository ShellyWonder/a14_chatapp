package com.wonderwebdev.a14_chatapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.wonderwebdev.a14_chatapp.domain.Chat;
import com.wonderwebdev.a14_chatapp.dto.ChatDTO;

@Mapper(componentModel = "spring")
public interface ChatMapper {
ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);
ChatDTO toDto(Chat chat);
}
