package com.kms.mygram.dto;

import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomePageDto{

    private List<Story> storyList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
}
