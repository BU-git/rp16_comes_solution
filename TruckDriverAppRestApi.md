# TruckDriver App REST API

## Content

* ## [Authentication] (#auth)

    * ### [Registration] (#reg)
    
    * ### [Reset Password] (#reset-pass)
    
    * ### [User Exist] (#u-exist)

* ## [Interaction with Users] (#users)

    * ### [Get all users] (#get-users)
    
    * ### [Get user by Id] (#get-user)
    
    * ### [Delete user by Id] (#del-user)
    
    * ### [Update user] (#put-user)
    
    * ### [Login user] (#log-user)
    
    * ### [Change password] (#pass-change)

    * ### [Get user`s Work Schedule] (#get-workschedule)

    * ### [Update user`s Work Schedule] (#put-workschedule)

    * ### [Verify user after registration] (#verify)
***

#  TruckDriver App REST API

* ## <a name="auth"></a> Authentication

	* ### <a name="reg"></a> Registration

        #### @POST
        #### /rest/api/auth

        ##### Params:

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

        Param Name | Nullable | Description
		---------- | -------- | -----------
        is | true | the user id
		email | true | email
        ...|...|...
        
        ##### Response:

        Status | Description
		------ | -----------
        201 | Created
        400 | Bad Request
        
    * ### <a name="reset-pass"></a> Reset Password
    
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
        
    * ### <a name="u-exist"></a> User Exist
        
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
        ...|...|...

	* ### <a name="del-user"></a> Delete user by Id

    	#### @DELETE
        #### /rest/api/users/{user_id}

		##### Path Params:

        Param Name | Required | Description
		---------- | -------- | -----------
        user_id | true | the user`s id


        ##### Response:

        Status | Description
		------ | -----------
        204 | No content
        401 | UNAUTHORIZED
        404 | NOT FOUND

	* ### <a name="put-user"></a> Update user

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

        ##### Params:

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
        200 | OK
        401 | UNAUTHORIZED
        403 | FORBIDDEN
        
	* ### <a name="log-user"></a> Login user

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
        412 |Precondition Failed| Account is not verified
        
    * ### <a name="pass-change"></a> Change password
    
        #### @PUT
        #### /rest/api/users/{id}/password

		##### Path Params:

        Path Key | Value
		-------- | -----
        user_id | 13

        <p/>

        Param Name | Required | Description
		---------- | -------- | -----------
        user_id | true | the user`s id

        ##### Params:

        Key | Value
        --- | -----
        oldPassword | 12345
        newPassword | qwerty

        <p/>
        
            {        
                "oldPassword": 12345,
                "newPassword": qwerty
             }               

        Param Name | Required | Description
        ---------- | -------- | -----------
        oldPassword | true | the user`s old password
        newPassword | true | the user`s new password

        ##### Response:

        Status | Description
        ------ | -----------
        204 | No Content
        404 | Not Found
        400 | Bad Request
        
	* ### <a name="get-workschedule"></a> Get user`s Work Schedule

    	#### @GET
        #### /rest/api/users/{user_id}/workschedule

		##### Path Params:

        Path Key | Value
		-------- | -----
        user_id | 3

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
              "id": 1,
              "creationTime": 1420149780000,
              "sunday": "5",
              "monday": "6",
              "tuesday": "7",
              "wednesday": "8",
              "thursday": "9",
              "friday": "10",
              "saturday": "11"
            }

	* ### <a name="put-workschedule"></a> Update user`s Work Schedule

    	#### @PUT
        #### /rest/api/users/{user_id}/workschedule

		##### Path Params:

        Path Key | Value
		-------- | -----
        user_id | 3

        <p/>

        Param Name | Required | Description
		---------- | -------- | -----------
        user_id | true | the user`s id

			{
              "id": 1,
              "creationTime": 1420149780000,
              "sunday": "5",
              "monday": "6",
              "tuesday": "7",
              "wednesday": "8",
              "thursday": "9",
              "friday": "10",
              "saturday": "11"
            }

        ##### Response:

        Status | Description
		------ | -----------
        200 | OK
        401 | UNAUTHORIZED
        404 | NOT FOUND


    * ### <a name="verify"></a> Verifying user after registration

        #### @GET
        #### /rest/api/users/verify

        ##### Path Params:

        Path Key | Value
        -------- | -----
        user_id | 3

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

* ## <a name="summary"></a> Getting the Summary of shifts

	* ### <a name="get-summary"></a> Get Summary for indicated period

    	#### @GET
        #### /rest/api/users/{user_id}/summary/{year}/{period}

        ##### Response:

        Status | Description
        ------ | -----------
        200 | OK
        400 | BAD REQUEST
        404 | NOT FOUND
