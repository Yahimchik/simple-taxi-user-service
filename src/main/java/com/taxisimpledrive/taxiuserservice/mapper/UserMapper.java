package com.taxisimpledrive.taxiuserservice.mapper;


import com.taxisimpledrive.taxiuserservice.dto.RegisterResponseDTO;
import com.taxisimpledrive.taxiuserservice.model.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface UserMapper {
    RegisterResponseDTO convertToResponse(User user);
}
