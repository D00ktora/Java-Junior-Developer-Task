
![Simple_REST_API (1)](https://github.com/D00ktora/Java-Junior-Developer-Task/assets/121710132/7489e152-ef3f-429a-80cf-6602866c270f)
# Java-Junior-Developer-Task
A simple Rest API  with CRUD operations, writan in Java language, that allow you to mannage users information and store it into SQL database.

## INTRO
 What will find in this project.

    * Create User
    * Read User information
    * Update User information
    * Delete User
    * Simple web visualisation.

## INSTALATION
Follow the steps bellow in order to run and consume this project.
      
#### Clone the project or simple download it.
https://github.com/D00ktora/Java-Junior-Developer-Task.git

#### Instal IntelliJ or other Java IDE, that support Java.
https://www.jetbrains.com/idea/download

#### Instal MySQL database.
https://dev.mysql.com/downloads/installer/

#### Instal Postman or other platform that can build and use APIs.
https://www.postman.com/downloads

#### Change following parameters in .yaml file.
[.yaml](https://github.com/D00ktora/Java-Junior-Developer-Task/blob/main/src/main/resources/application.yaml)

      1. username: root --> Change with your database user name.
      2. password: ${MY_PASSWORD} --> Change with your database password.
      3. url: jdbc:mysql://localhost:3306/markovski_database?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC --> Change "markovski_database" with propper name for you database.

**Now can run the project.**

## API Reference
**In order to reach the API references you need to put in hltm Authorization headers:**
   *  Email and Password encripted witl Basic64 --> Send this as POST request to `http://localhost:8080/api/token` end point. Return will be `String` with `JWT` token, generated for this account, that will be availible for 60 minutes.
   *  JWT Token --> Sned this to any of the end points bellow.

### 1. Get all users. (Sorted by last name ascending then by date of birth ascending)

```http
  GET http://localhost:8080
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
|  | | No required parameters are needed |

**RESPONS**

| Type     | Description                |
| :------- | :------------------------- |
| `JSON` | List of all availible users in database. |


### 2. Get singl users.

```http
  GET http://localhost:8080/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Id of the user |

**RESPONS**

| Type     | Description                |
| :------- | :------------------------- |
| `JSON` | Single User information |


### 3. Get users by them first name.

```http
  GET http://localhost:8080/fn={firstName}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `first name` | `String` | **Required**. First name of searched users |

**RESPONS**

| Type     | Description                |
| :------- | :------------------------- |
| `JSON` | List of all users with that first name |

### 4. Get users by them last name.

```http
  GET http://localhost:8080/ln={lastName}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `lastName` | `String` | **Required**. Last name of searched users |

**RESPONS**

| Type     | Description                |
| :------- | :------------------------- |
| `JSON` | List of all users with that last name |


### 5. Get users by them fphone number.

```http
  GET http://localhost:8080/p={phone}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `phone` | `String` | **Required**. Last name of searched users |

**RESPONS**

| Type     | Description                |
| :------- | :------------------------- |
| `JSON` | List of all users with that phone number |

### 6. Get users by them date of birth.

```http
  GET http://localhost:8080/d={date}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `date` | `String` | **Required**. Date must be in format yyyy-mm-dd |

**RESPONS**

| Type     | Description                |
| :------- | :------------------------- |
| `JSON` | List of all users with that date of birth |

### 7. Get page with users.

```http
  GET http://localhost:8080/pagination/{offset}/{size}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `offset` | `String` | **Required**. From wich user the page will start |
| `size` | `String` | **Required**. How many users will be listed on the page |

**RESPONS**

| Type     | Description                |
| :------- | :------------------------- |
| `JSON` | List with users base on offset and size criteria |

### 8. Get users by them email.

```http
  GET http://localhost:8080/email={email}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `email` | `String` | **Required**. Email of searched users |

**RESPONS**

| Type     | Description                |
| :------- | :------------------------- |
| `JSON` | List of all users with that email |

### 9. Create user.
Body that is need to be setup:
```
{
  "firstName": "John",
  "lastName": "Smith",
  "dateOfBirth": "1990-10-03",
  "phoneNumber": "+395881234567",
  "email": "Smith@gmail.com",
  "password": "Password"
}
```

```http
  POST http://localhost:8080/create
```

| Parameter in body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `firstName` | `String` | **Required**. First name of the user |
| `lastName` | `String` | **Required**. Last name of the user |
| `dateOfBirth` | `String` | **Required**. Date of birth of user in format yyyy-mm-dd |
| `phoneNumber` | `String` | **Required**. Phone number of the user |
| `email` | `String` | **Required**. Email of the users |
| `Password` | `String` | **Required**. Password of the users |

**RESPONS**

| Type     | Description                |
| :------- | :------------------------- |
| `JSON` | User that is created |

### 10. Update user.
Body that is need to be setup:
```
{
    "firstName": "Stilyan1231231231313",
    "lastName": "Petrov123123123123",
    "dateOfBirth": "1990-10-03",
    "phoneNumber": "089xxxxxxx",
    "email": "1x.x.x.xxxx@gmail.com"
}
```

```http
  POST http://localhost:8080/modify/{id}
```
 Parameter in body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Id of the user that is need to be updated |

| Parameter in body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `firstName` | `String` | **Required**. First name of the user |
| `lastName` | `String` | **Required**. Last name of the user |
| `dateOfBirth` | `String` | **Required**. Date of birth of user in format yyyy-mm-dd |
| `phoneNumber` | `String` | **Required**. Phone number of the user |
| `email` | `String` | **Required**. Email of the users |

**RESPONS**

| Type     | Description                |
| :------- | :------------------------- |
| `JSON` | User that is updated |


### 11. Delete user.

```http
  POST http://localhost:8080/delete/{id}
```
 Parameter in body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Id of the user that is need to be updated |



## Known Issues (Work in progress)

* Implementation in security user responsibilitys (USER to be able to change his information and ADMIN to be able to update, delete and create user).
* Writ down Unit and Integration tests.
* Automatic migration scripts for the Database are present – tools like Flyway or Liquidbase can be used. Need to get more information how the tools work and to implement it.
