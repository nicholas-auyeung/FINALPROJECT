package com.hcl.finalproject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ObjectParser {

    public List<UserAttribute> objectToList(User user);
    public User updateUser(User user, List<UserAttribute> userAttributeList);
    public List<User> setUserProfileImages(List<User> userList);

}
