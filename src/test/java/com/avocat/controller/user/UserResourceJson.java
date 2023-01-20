package com.avocat.controller.user;

public abstract class UserResourceJson {

    private UserResourceJson(){}

    public static String getUserUpdate() {
        return """
                {
                    "id": "c17ceaa8-5475-450b-9409-f3545548ca6d",
                    "username": "owtestintegration@xemail.com",
                    "privileges": [
                        {
                            "id": "2234d142-7a4a-4a8c-8431-b9a7a24c41de",
                            "name": "ROLE_ADMIN"
                        },
                        {
                            "id": "06d608f3-3058-44bc-9fe4-d12c36a43f0c",
                            "name": "ROLE_USER"
                        }
                    ]                                        
                }
                """;
    }
}
