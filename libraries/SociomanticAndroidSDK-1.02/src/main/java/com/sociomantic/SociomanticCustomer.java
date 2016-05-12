package com.sociomantic;

import com.sociomantic.utility.JsonUtility;
import com.sociomantic.utility.StringHash;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by John Nilsen on 13.12.13 at Sociomantic Labs.
 */
public class SociomanticCustomer {

    private Map<SCMCustomerKey, String> customerMap = new HashMap<SCMCustomerKey, String>();

    private enum SCMCustomerKey {
        IDENTIFIER,
        TARGETING,
        MHASH,
        AGEGROUP,
        GENDER,
        EDUCATION,
        SEGMENT
    }

    public enum Gender {
        MALE, FEMALE
    }

    public enum AgeGroup {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
    }

    public enum Education{
        ZERO, ONE, TWO, THREE, FOUR
    }

    public boolean containsData() {
        return !customerMap.isEmpty();
    }

    public void setIdentifier(String identifier) {
        customerMap.put(SCMCustomerKey.IDENTIFIER, identifier);
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        customerMap.put(SCMCustomerKey.AGEGROUP, (ageGroup.ordinal() + 1) + "");
    }

    public String getJsonString() {
        String jsonString = "{";
        if(!customerMap.isEmpty()){
            jsonString = jsonString + JsonUtility.getJsonParameterStringFromMap(customerMap);
        }
        return jsonString + "}";
    }

    public void setEmail(String email)  {
        customerMap.put(SCMCustomerKey.MHASH, StringHash.convertToSHA256(email));
    }

    public void setTargeting(boolean isTargetingActivated) {
        if(isTargetingActivated){
            customerMap.put(SCMCustomerKey.TARGETING, 1 + "");
        } else {
            customerMap.put(SCMCustomerKey.TARGETING, 0 + "");
        }
    }

    public void setGender(Gender gender) {
        customerMap.put(SCMCustomerKey.GENDER, gender.ordinal() + "");
    }

    public void setEducation(Education education) {
        customerMap.put(SCMCustomerKey.EDUCATION, education.ordinal() + "");
    }

    public void setSegment(int segmentCode) {
        customerMap.put(SCMCustomerKey.SEGMENT, segmentCode + "");
    }
}
