package com.hcl.finalproject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserParser implements ObjectParser{

    private List<UserAttribute> userAttributeList = new ArrayList<UserAttribute>();

    public List<UserAttribute> objectToList(User user){

        String attributeName = "Name:";
        String attributeNameItem = user.getName();

        String attributeUsername = "Username:";
        String attributeUsernameItem = user.getUsername();

        String attributeEmail = "Email:";
        String attributeEmailItem = user.getEmail();

        String attributeAddress = "Address";
        String attributeAddressItem = "FALSE";


        String attributeStreet = "Street:";
        String attributeStreetItem = user.getAddress().getStreet();

        String attributeSuite = "Suite:";
        String attributeSuiteItem = user.getAddress().getSuite();

        String attributeCity = "City:";
        String attributeCityItem = user.getAddress().getCity();


        String attributeZipCode = "Zip Code:";
        String attributeZipCodeItem = user.getAddress().getZipcode();

        String attributeGeo = "Geo";
        String attributeGeoItem = "FALSE";

        String attributeLat = "Lat:";
        String attributeLatItem = user.getAddress().getGeo().getLat();

        String attributeLng = "Lng:";
        String attributeLngItem = user.getAddress().getGeo().getLng();

        String attributePhone = "Phone:";
        String attributePhoneItem = user.getPhone();

        String attributeWebsite = "Website:";
        String attributeWebsiteItem = user.getWebsite();

        String attributeCompany = "Company";
        String attributeCompanyItem = "FALSE";

        String attributeCName = "Company Name:";
        String attributeCNameItem = user.getCompany().getName();

        String attributeCatchPhrase = "Catch Phrase:";
        String attributeCatchPhraseItem = user.getCompany().getCatchPhrase();

        String attributeBS = "BS:";
        String attributeBSItem = user.getCompany().getBs();


        userAttributeList.add(new UserAttribute(attributeName, attributeNameItem));
        userAttributeList.add(new UserAttribute(attributeUsername, attributeUsernameItem));
        userAttributeList.add(new UserAttribute(attributeEmail, attributeEmailItem));
        userAttributeList.add(new UserAttribute(attributeAddress, attributeAddressItem));
        userAttributeList.add(new UserAttribute(attributeStreet, attributeStreetItem));
        userAttributeList.add(new UserAttribute(attributeSuite, attributeSuiteItem));
        userAttributeList.add(new UserAttribute(attributeCity, attributeCityItem));
        userAttributeList.add(new UserAttribute(attributeZipCode, attributeZipCodeItem));
        userAttributeList.add(new UserAttribute(attributeGeo, attributeGeoItem));
        userAttributeList.add(new UserAttribute(attributeLat, attributeLatItem));
        userAttributeList.add(new UserAttribute(attributeLng, attributeLngItem));
        userAttributeList.add(new UserAttribute(attributePhone, attributePhoneItem));
        userAttributeList.add(new UserAttribute(attributeWebsite, attributeWebsiteItem));
        userAttributeList.add(new UserAttribute(attributeCompany, attributeCompanyItem));
        userAttributeList.add(new UserAttribute(attributeCName, attributeCNameItem));
        userAttributeList.add(new UserAttribute(attributeCatchPhrase, attributeCatchPhraseItem));
        userAttributeList.add(new UserAttribute(attributeBS, attributeBSItem));


        return userAttributeList;
    }

}
