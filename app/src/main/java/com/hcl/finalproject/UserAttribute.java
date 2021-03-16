package com.hcl.finalproject;

import java.util.List;

public class UserAttribute {

    private String attribute;
    private List<String> attributeDetails;

    public UserAttribute(String attribute, List<String> attributeDetails) {
        this.attribute = attribute;
        this.attributeDetails = attributeDetails;
    }

    public String getAttribute() {
        return attribute;
    }

    public List<String> getAttributeDetails() {
        return attributeDetails;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setAttributeDetails(List<String> attributeDetails) {
        this.attributeDetails = attributeDetails;
    }

    @Override
    public String toString() {
        return "UserAttribute{" +
                "attribute='" + attribute + '\'' +
                ", attributeDetails=" + attributeDetails +
                '}';
    }
}
