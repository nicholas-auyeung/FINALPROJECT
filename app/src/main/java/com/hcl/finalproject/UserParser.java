package com.hcl.finalproject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserParser implements ObjectParser{

    private List<UserAttribute> userAttributeList = new ArrayList<>();

    public List<UserAttribute> objectToList(User user){

        String attributeName = "Name";
        List<String> attributeNameItems = new ArrayList<>();
        attributeNameItems.add(user.getName());

        String attributeUsername = "Username";
        List<String> attributeUsernameItems = new ArrayList<>();
        attributeUsernameItems.add(user.getUsername());

        String attributeEmail = "Email";
        List<String> attributeEmailItems = new ArrayList<>();
        attributeEmailItems.add(user.getEmail());

        String attributeAddress = "Address";
        List<String> attributeAddressItems = new ArrayList<>();

        String attributeStreet = "Street";
        List<String> attributeStreetItems = new ArrayList<>();
        attributeStreetItems.add(user.getAddress().getStreet());

        String attributeSuite = "Suite";
        List<String> attributeSuiteItems = new ArrayList<>();
        attributeSuiteItems.add(user.getAddress().getSuite());

        String attributeCity = "City";
        List<String> attributeCityItems = new ArrayList<>();
        attributeCityItems.add(user.getAddress().getCity());

        String attributeZipCode = "Zip Code";
        List<String> attributeZipCodeItems = new ArrayList<>();
        attributeZipCodeItems.add(user.getAddress().getZipcode());

        String attributeGeo = "Geo";
        List<String> attributeGeoItems = new ArrayList<>();

        String attributeLat = "Lat";
        List<String> attributeLatItems = new ArrayList<>();
        attributeLatItems.add(user.getAddress().getGeo().getLat());

        String attributeLng = "Lng";
        List<String> attributeLngItems = new ArrayList<>();
        attributeLngItems.add(user.getAddress().getGeo().getLng());

        String attributePhone = "Phone";
        List<String> attributePhoneItems = new ArrayList<>();
        attributePhoneItems.add(user.getPhone());

        String attributeWebsite = "Website";
        List<String> attributeWebsiteItems = new ArrayList<>();
        attributeWebsiteItems.add(user.getWebsite());

        String attributeCompany = "Company";
        List<String> attributeCompanyItems = new ArrayList<>();

        String attributeCName = "Company Name:";
        List<String> attributeCNameItems = new ArrayList<>();
        attributeCNameItems.add(user.getCompany().getName());

        String attributeCatchPhrase = "Catch Phrase";
        List<String> attributeCatchPhraseItems = new ArrayList<>();
        attributeCatchPhraseItems.add(user.getCompany().getCatchPhrase());

        String attributeBS = "BS";
        List<String> attributeBSItems = new ArrayList<>();
        attributeBSItems.add(user.getCompany().getBs());

        userAttributeList.add(new UserAttribute(attributeName, attributeNameItems));
        userAttributeList.add(new UserAttribute(attributeUsername, attributeUsernameItems));
        userAttributeList.add(new UserAttribute(attributeEmail, attributeEmailItems));
        userAttributeList.add(new UserAttribute(attributeAddress, attributeAddressItems));
        userAttributeList.add(new UserAttribute(attributeStreet, attributeStreetItems));
        userAttributeList.add(new UserAttribute(attributeSuite, attributeSuiteItems));
        userAttributeList.add(new UserAttribute(attributeCity, attributeCityItems));
        userAttributeList.add(new UserAttribute(attributeZipCode, attributeZipCodeItems));
        userAttributeList.add(new UserAttribute(attributeGeo, attributeGeoItems));
        userAttributeList.add(new UserAttribute(attributeLat, attributeLatItems));
        userAttributeList.add(new UserAttribute(attributeLng, attributeLngItems));
        userAttributeList.add(new UserAttribute(attributePhone, attributePhoneItems));
        userAttributeList.add(new UserAttribute(attributeWebsite, attributeWebsiteItems));
        userAttributeList.add(new UserAttribute(attributeCompany, attributeCompanyItems));
        userAttributeList.add(new UserAttribute(attributeCName, attributeCNameItems));
        userAttributeList.add(new UserAttribute(attributeCatchPhrase, attributeCatchPhraseItems));
        userAttributeList.add(new UserAttribute(attributeBS, attributeBSItems));


        return userAttributeList;
    }
}
