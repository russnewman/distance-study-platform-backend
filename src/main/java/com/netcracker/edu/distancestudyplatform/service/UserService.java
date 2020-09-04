package com.netcracker.edu.distancestudyplatform.service;

import com.netcracker.edu.distancestudyplatform.dto.UserDto;
import com.netcracker.edu.distancestudyplatform.model.User;

public interface UserService {
    User findByEmail(String email);
    UserDto getInfoByEmail(String email);
}
