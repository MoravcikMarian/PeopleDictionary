package com.example.database;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class GeneratePerson {
    private static final String[] manFirstName = {"John","Mark","Peter","Kevin","Thomas"};
    private static final String[] womanFirstName = {"Sophia","Charlotte","Lucy","Grace","Amelia"};
    private static final String[] lastName = {"Adams","Smith","Bailey","Banks","Carter"};
    private static final int[] age = {18,20,37,45,60};
    private static final String[] address = {"Rubin","Kimmel","Calle de Goya","Ceska","Masarykova"};
    private static final HashMap<String, Integer> cities;
    static {
        cities = new HashMap<>();
        cities.put("New York", 10001);
        cities.put("Brno", 60200);
        cities.put("Madrid", 28001);
    }
    static PersonalDetails newPerson() {
        int max = 5;
        SecureRandom rnd = new SecureRandom();
        PersonalDetails details = new PersonalDetails();

        if(rnd.nextBoolean()) {
            details.gender = "man";
            details.name = manFirstName[rnd.nextInt(max)] + " " + lastName[rnd.nextInt(max)];
        }
        else {
            details.gender = "woman";
            details.name = womanFirstName[rnd.nextInt(max)] + " " + lastName[rnd.nextInt(max)];
        }

        details.age = age[rnd.nextInt(max)];
        details.address = address[rnd.nextInt(5)] + " " + rnd.nextInt(40);

        Set<String> keySet = cities.keySet();
        List<String> keyList = new ArrayList<>(keySet);

        String randomKey = keyList.get(rnd.nextInt(keyList.size()));
        Integer randomValue = cities.get(randomKey);
        details.city = randomKey;
        details.zip = randomValue;

        return details;
    }
}
