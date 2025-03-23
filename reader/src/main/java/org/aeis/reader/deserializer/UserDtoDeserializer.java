package org.aeis.reader.deserializer;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.aeis.reader.dto.userdto.UserDTO;
import org.aeis.reader.dto.userdto.UserInfoDto;
import org.aeis.reader.dto.userdto.UserInstructorInfo;
import org.aeis.reader.dto.userdto.UserStudentInfo;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class UserDtoDeserializer extends JsonDeserializer<UserDTO> {


    @Override
    public UserDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        JsonNode userInfoNode = node.get("user_info");
        String role = userInfoNode.get("role").asText();

        if ("STUDENT".equalsIgnoreCase(role)) {
            return p.getCodec().treeToValue(node, UserStudentInfo.class);
        } else if ("INSTRUCTOR".equalsIgnoreCase(role)) {
            return p.getCodec().treeToValue(node, UserInstructorInfo.class);
        } else  {
            return p.getCodec().treeToValue(node, UserInfoDto.class);
        }

    }


}
