package com.example.holynskyi.firstapplication.db;

/**
 * Created by holynskyi on 09.08.17.
 */

public final class DatabaseStructure {
    public static final class tables {
        public static final String users = "users";
        public static final String cars = "cars";
    }

    public static final class columns {

        public static final class user {
            public static final String id = "id";
            public static final String userName = "name";
            public static final String userPassword = "password";
            public static final String userCars = "user_cars";
        }


        public static final class car {
            public static final String id = "id";
            public static final String title1 = "title1";
            public static final String title2 = "title2";
            public static final String someData = "data";
            public static final String userId = "userId";
        }
    }
}
