package com.wonderwebdev.a14_chatapp.service;

import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import com.wonderwebdev.a14_chatapp.domain.Channel;
import com.wonderwebdev.a14_chatapp.dto.ChannelDTO;
import com.wonderwebdev.a14_chatapp.dto.ChatSummaryDTO;
import com.wonderwebdev.a14_chatapp.dto.UserSummaryDTO;
import com.wonderwebdev.a14_chatapp.exception.ResourceNotFoundException;
import com.wonderwebdev.a14_chatapp.mapper.ChannelMapper;
import com.wonderwebdev.a14_chatapp.mapper.ChatMapper;
import com.wonderwebdev.a14_chatapp.mapper.UserMapper;
import com.wonderwebdev.a14_chatapp.repository.ChannelRepository;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;
    private final ChatMapper chatMapper;
    private final UserMapper userMapper;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ChannelService.class);

    public ChannelService(ChannelRepository channelRepository, ChannelMapper channelMapper, ChatMapper chatMapper, UserMapper userMapper) {
        this.channelRepository = channelRepository;
        this.channelMapper = channelMapper;
        this.chatMapper = chatMapper;
        this.userMapper = userMapper;
    }

    public ChannelDTO findChannelById(Long id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found"));
        return mapToChannelDTO(channel);
    }

    public List<ChannelDTO> findAllChannels() {
        List<Channel> channels = channelRepository.findAll();
        return channels.stream()
                .map(this::mapToChannelDTO)
                .collect(Collectors.toList());
    }

    public List<ChatSummaryDTO> findMessagesByChannelId(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found"));
        return channel.getMessages().stream()
                .map(chat -> {
                    ChatSummaryDTO chatSummaryDTO = chatMapper.toSummaryDto(chat);
                    chatSummaryDTO.setUserName(chat.getUser().getUserName());
                    return chatSummaryDTO;
                })
                .collect(Collectors.toList());
    }

    private ChannelDTO mapToChannelDTO(Channel channel) {
        ChannelDTO channelDTO = channelMapper.toDto(channel);

        List<ChatSummaryDTO> chatSummaryDTOs = channel.getMessages().stream()
                .map(chat -> {
                    ChatSummaryDTO chatSummaryDTO = chatMapper.toSummaryDto(chat);
                    chatSummaryDTO.setUserName(chat.getUser().getUserName());
                    return chatSummaryDTO;
                })
                .collect(Collectors.toList());
        channelDTO.setMessages(chatSummaryDTOs);

        List<UserSummaryDTO> userSummaryDTOs = channel.getUsers().stream()
                .map(userMapper::toSummaryDto)
                .collect(Collectors.toList());
        channelDTO.setUsers(userSummaryDTOs);

        return channelDTO;
    }
    public int getParticipantCount(Long channelId) {
        return channelRepository.findById(channelId)
                .map(channel -> {
                    int count = channel.getUsers().size();
                    logger.info("Channel ID: {} has {} participants", channelId, count);
                    return count;
                })
                .orElseGet(() -> {
                    logger.warn("Channel ID: {} not found", channelId);
                    return 0;
                });
    }
}
