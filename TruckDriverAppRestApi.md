# TruckDriver App REST API

## Content

* ## [Authentication] (#authentication)

    * ### [Registration] (#registration)
    * ### [Reset Password] (#reset-password)
    * ### [User Exist] (#user-exist)

* ## [Interaction with Users] (#users)

    * ### [Get all users] (#get-users)
    * ### [Get user by Id] (#get-user)
    * ### [Delete user by Id] (#delete-user)
    * ### [Update user] (#update-user)
    * ### [Login user] (#login-user)
    * ### [Change password] (#change-password)

***

#  TruckDriver App REST API

* ## <a name="authentication"></a> Authentication

	* ### <a name="registration"></a> Registration

        #### @POST
        #### /rest/api/auth

        ##### Params:

        Key | Value
        --- | -----
        email | email@gmail.com
        password | secret

        <p/>
        
            {        
                "birthDate": null,
                "email": "ivan@gmail.com",
                "employer": null,
                "firstName": "ivan",
                "id": 0,
                "insertion": "ivanov",
                "jobs": [{
                "id": 0,
                "jobName": "Driver",
                "users": null
                }, {
                "id": 0,
                "jobName": "Operator",
                "users": null
                }],
                "lastName": "ivanovich",
                "password": "qwerty",
                "passwordExpire": 2916942696182,
                "role": null,
                "sex": "Male",
                "workSchedule": {
                "creationTime": 1458471384510,
                "friday": "8-16",
                "id": 0,
                "monday": "3-9",
                "saturday": "13-19",
                "sunday": "",
                "thursday": "8-13",
                "tuesday": "8-18",
                "user": null,
                "wednesday": "8-15"
                },
                "contractHours": 60,
                "enabled": false,
                "fourWeekPayOff": false,
                "zeroHours": false
             }               

        Param Name | Required | Description
        ---------- | -------- | -----------
        email | true | the user email
        password | true | the user password

        ##### Response:

        Status | Description
		------ | -----------
        201 | Created
        400 | Bad Request
        
    * ### <a name="reset-password"></a> Reset Password
    
        #### @PUT
        #### /rest/api/auth/password
    
        ##### Params:
            
        Param Name | Required | Description
        ---------- | -------- | -----------
        email | true | the user email
        
    
        ##### Response:
    
        Status | Description
    	------ | -----------
        200 | OK
        400 | Bad Request
        
    * ### <a name="user-exist"></a> User Exist
        
        #### @GET
        #### /rest/api/auth/exist
        
        ##### Params:
                
        Param Name | Required | Description
        ---------- | -------- | -----------
        email | true | the user email
        
        
        ##### Response:
        
        Status | Description
        ------ | -----------
        200 | OK
        400 | Bad Request
        403 | Forbidden
        
	
* ## <a name="users"></a> Interaction with Users

	* ### <a name="get-users"></a> Get all Users

    	#### @GET
        #### /rest/api/users

        ##### Response:
    
        Status | Description
        ------ | -----------
        200 | OK
        401 | UNAUTHORIZED
        404 | NOT FOUND
        
                     
	* ### <a name="get-user"></a> Get user by Id

    	#### @GET
        #### /rest/api/users/{user_id}

		##### Path Params:

        Path Key | Value
		-------- | -----
        user_id | 13

        <p/>

        Param Name | Required | Description
		---------- | -------- | -----------
        user_id | true | the user`s id


        ##### Response:

        Status | Description
		------ | -----------
        200 | OK
        401 | UNAUTHORIZED
        404 | NOT FOUND

			{
              "id": 13,
              "email": "ivanivan@gmail.com",
              "password": "qwerty",
              "firstName": "ivankp",
              "lastName": "ivanovich",
              "insertion": "ivanov",
              "sex": "Male",
              "fourWeekPayOff": false,
              "zeroHours": false,
              "contractHours": 60,
              "enabled": false,
              "birthDate": null,
              "passwordExpire": 2916942696000,
              "workSchedule": null,
              "role": "ADMIN",
              "employer": null,
              "jobs": [
                {
                  "id": 36,
                  "jobName": "Driver"
                },
                {
                  "id": 37,
                  "jobName": "Operator"
                }
              ]
            }

        Param Name | Nullable | Description
		---------- | -------- | -----------
        is | true | the user id
		email | true | email
        ...

	* ### <a name="delete-user"></a> Delete user by Id

    	#### @DELETE
        #### /rest/api/users/{user_id}

		##### Path Params:

        Path Key | Value
		-------- | -----
        user_id | 13

        <p/>

        Param Name | Required | Description
		---------- | -------- | -----------
        user_id | true | the user`s id


        ##### Response:

        Status | Description
		------ | -----------
        204 | No content
        401 | UNAUTHORIZED
        404 | NOT FOUND

	* ### <a name="update-user"></a> Update user

    	#### @PUT
        #### /rest/api/users/{user_id}

		##### Path Params:

        Path Key | Value
		-------- | -----
        user_id | 13

        <p/>

        Param Name | Required | Description
		---------- | -------- | -----------
        user_id | true | the user`s id

			{
              "id": 13,
              "email": "ivanivan@gmail.com",
              "password": "qwerty",
              "firstName": "ivankp",
              "lastName": "ivanovich",
              "insertion": "ivanov",
              "sex": "Male",
              "fourWeekPayOff": false,
              "zeroHours": false,
              "contractHours": 60,
              "enabled": false,
              "birthDate": null,
              "passwordExpire": 2916942696000,
              "workSchedule": null,
              "role": "ADMIN",
              "employer": null,
              "jobs": [
                {
                  "id": 36,
                  "jobName": "Driver"
                },
                {
                  "id": 37,
                  "jobName": "Operator"
                }
              ]
            }
        
        ##### Response:

        Status | Description
		------ | -----------
        204 | No content
        401 | UNAUTHORIZED
        
	* ### <a name="login-user"></a> Login user

    	#### @GET
        #### /rest/api/users/login

        ##### Response:

        Code | Code Message| Description
		------ | -----------|---------
        200 | OK | OK
        401 | UNAUTHORIZED | Unauthorized
        403 | Forbidden | You have temporary password
        404 | Not Found | User not found
        409 | Conflict | Password expired
        
    * ### <a name="change-password"></a> Change password
    
        #### @PUT
        #### /rest/api/users/password

        ##### Params:

        Key | Value
        --- | -----
        email | test@test.com
        oldPassword | 12345
        newPassword | qwerty

        <p/>
        
            {        
                "email": "ivan@gmail.com",
                "oldPassword": 12345,
                "newPassword": qwerty
             }               

        Param Name | Required | Description
        ---------- | -------- | -----------
        email | true | the user email
        oldPassword | true | the user`s old password
        newPassword | true | the user`s new password

        ##### Response:

        Status | Description
        ------ | -----------
        200 | OK
        404 | Not Found
        400 | Bad Request