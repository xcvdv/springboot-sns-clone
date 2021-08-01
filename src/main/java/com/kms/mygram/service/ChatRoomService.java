package com.kms.mygram.service;

import com.kms.mygram.domain.ChatRoom;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.ChatRoomDto;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    public ChatRoom getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(() ->
                new ApiException("chatroom이 존재하지 않습니다."));
    }

    public List<ChatRoomDto> getChatRoomsDto(User user) {
        List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
        chatRoomRepository.findAllByUserId(user.getUserId()).forEach(chatRoom -> {
            ChatRoomDto chatRoomDto = new ChatRoomDto();
            chatRoomDto.setChatRoomId(chatRoom.getChatRoomId());
            chatRoomDto.setOtherUser(!chatRoom.getUserOne().getUserId().equals(user.getUserId()) ? chatRoom.getUserOne() : chatRoom.getUserTwo());
            chatRoomDtoList.add(chatRoomDto);
        });
        return chatRoomDtoList;
    }

    public ChatRoom createChatRoom(Long userId, Long targetUserId) {
        User userTwo = userService.getUserById(targetUserId);
        if (chatRoomRepository.findByUserOneIdAndUserTwoId(userId, targetUserId).isPresent())
            throw new ApiException("이미 존재하는 ChatRoom 입니다.");
        User userOne = userService.getUserById(userId);
        ChatRoom chatRoom = ChatRoom.builder()
                .userOne(userOne)
                .userTwo(userTwo)
                .build();
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoomDto getChatRoomDto(User user, Long chatroomId) {
        ChatRoom chatRoom = chatRoomRepository.findByIdAndUserId(chatroomId, user.getUserId()).orElseThrow(() ->
                new ApiException("잘못된 요청입니다. 채팅방을 확인해 주세요.")
        );
        return ChatRoomDto.builder()
                .otherUser(!chatRoom.getUserOne().getUserId().equals(user.getUserId()) ? chatRoom.getUserOne() : chatRoom.getUserTwo())
                .chatRoomId(chatroomId)
                .build();
    }
}
