package com.wonderwebdev.a14_chatapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.wonderwebdev.a14_chatapp.domain.Chat;
import com.wonderwebdev.a14_chatapp.dto.ChatDTO;
import com.wonderwebdev.a14_chatapp.dto.ChatMessageDTO;
import com.wonderwebdev.a14_chatapp.dto.ChatSummaryDTO;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "channel", ignore = true)
    ChatDTO toDto(Chat chat);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "channel", ignore = true)
    Chat toEntity(ChatDTO chatDTO);

    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "publishedAt", source = "publishedAt")
    ChatSummaryDTO toSummaryDto(Chat chat);

    @Mapping(target = "channel", source = "channel")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "userName", source = "user.userName")
    ChatMessageDTO toMessageDto(Chat chat);

    @Mapping(target = "channel.description", ignore = true)
    @Mapping(target = "channel.messages", ignore = true)
    @Mapping(target = "channel.users", ignore = true)
    @Mapping(target = "user.channels", ignore = true)
    @Mapping(target = "user.password", ignore = true)
    @Mapping(target = "channel", source = "channel")
    @Mapping(target = "user", source = "user")
    Chat toEntity(ChatMessageDTO chatMessageDTO);

    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "publishedAt", source = "publishedAt")
    ChatSummaryDTO messageDtoToSummaryDto(ChatMessageDTO chatMessageDTO);
}
