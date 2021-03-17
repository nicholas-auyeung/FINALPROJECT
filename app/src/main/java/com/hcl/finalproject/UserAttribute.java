package com.hcl.finalproject;

import java.util.ArrayList;
import java.util.List;

public class UserAttribute {

    private String attribute;
    private String attributeDetails;

    public UserAttribute(String attribute, String attributeDetails) {
        this.attribute = attribute;
        this.attributeDetails = attributeDetails;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getAttributeDetails() {
        return attributeDetails;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setAttributeDetails(String attributeDetails) {
        this.attributeDetails = attributeDetails;
    }
}
