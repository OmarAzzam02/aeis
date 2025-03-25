package org.aeis.reader.util;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.aeis.reader.dto.userdto.UserDTO;
import org.aeis.reader.dto.userdto.UserInfoDto;
import org.aeis.reader.dto.userdto.UserInstructorInfo;
import org.aeis.reader.dto.userdto.UserStudentInfo;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

@Log4j2
public class UserDtoDeserializer extends JsonDeserializer<UserDTO> {


    @Override
    public UserDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        JsonNode userInfoNode = node.get("user_info");

        JsonNode roleNode = null;
        if(userInfoNode!=null)
          roleNode = userInfoNode.get("role");

        else
          roleNode = node.get("role");


        String role = (roleNode != null && !roleNode.isNull()) ? roleNode.asText() : "UNKNOWN";


        if ("STUDENT".equalsIgnoreCase(role)) {
            return p.getCodec().treeToValue(node, UserStudentInfo.class);
        } else if ("INSTRUCTOR".equalsIgnoreCase(role)) {
            return p.getCodec().treeToValue(node, UserInstructorInfo.class);
        } else if ("ADMIN".equalsIgnoreCase(role)) {
            return p.getCodec().treeToValue(node, UserInfoDto.class);
        } else {
            throw new IOException("Unknown user role: " + role);
        }

    }


}
