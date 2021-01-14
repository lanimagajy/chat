package com.epam.chat.datalayer.xml.parser;

import com.epam.chat.datalayer.dto.Status;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Objects;

public class StatusParser {


    public static final String STATUS = "Status";

    public Status getStatusValueElement(Element element, String s) {
        NodeList nodeList = element.getElementsByTagName(STATUS);
        NodeList node = null;
        for(int i = 0; i<nodeList.getLength(); i++) {
            node = nodeList.item(i).getChildNodes();
        }
        for (int i = 0; i < Objects.requireNonNull(node).getLength(); i++) {
            if(s.equals(node.item(i).getNodeName())){
                return Status.valueOf(node.item(i).getTextContent());
            }
        }
        return Status.valueOf(node.toString());
    }


}
