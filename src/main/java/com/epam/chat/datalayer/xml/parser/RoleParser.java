package com.epam.chat.datalayer.xml.parser;

import com.epam.chat.datalayer.dto.Role;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Objects;

public class RoleParser {
    public static final String ROLE = "Role";

    public Role getRoleValueElement(Element element, String s) {
        NodeList nodeList = element.getElementsByTagName(ROLE);
        NodeList node = null;
        for(int i = 0; i<nodeList.getLength(); i++) {
            node = nodeList.item(i).getChildNodes();
        }
        for (int i = 0; i < Objects.requireNonNull(node).getLength(); i++) {
            if(s.equals(node.item(i).getNodeName())){
                return Role.valueOf(node.item(i).getTextContent());
            }
        }
        return Role.valueOf(node.toString());
    }
}
