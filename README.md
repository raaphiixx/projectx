# Project X

This project was created to implement and test some knowledge about caching, this isn't the first time that i start 
this project, but this time i challenged myself to commit each step, so every will be new every week. I know this 
text there are some orthography errors because english isn't my mother language, so that will be another challenger too.

## Time to explain what is the project...
The X or Twitter is a social media that used by million of people round the world, and there are some functions that 
interesting to implement caching, and show how "magic" is the cache power.

### Those are the steps that i'll follow to make this project:
Some topics will be deleted or change during the process.


1- User authentication and management:

* Register a new user: POST /auth/register
* User login: POST /auth/login
* Get single user by Id: GET /users/{userId}
* Update user details: PUT /users/{userId}
* Delete a user: DELETE /users/{userId}

2- Tweets creation, retrieval, and deletion:

* Create a tweet: POST /tweets
* Get a single tweet: GET /tweets/{tweetId}
* Delete a tweet: DELETE /tweets/{tweetId}

3- Following and unfollowing users:

* Get followers: GET /users/{userId}/followers
* Get followings : GET /users/{userId}/following
* Follow userId: POST /users/{userId}/following
* Unfollow user: DELETE /users/{sourceUserId}/following/{targetUserId}

4- Liking and unliking tweets:

* Like a tweet: POST /users/{userId}/likes
* Get user' liked tweets: GET /users/{userId}/likes
* Get tweet's liking users: GET /tweets/{tweetId}/likes
* Unlike a tweet: DELETE /users/{userId}/likes/{tweetId}

5- Retweeting and undoing retweets:

* Retweet a tweet: POST /users/{userId}/retweets
* Get tweet's retweets: GET /tweets/{tweetId}/retweeters
* Undo a retweet: DELETE /users/{userId}/retweets/{tweetId}

6- View Home Timeline and User Timeline.

* Get a single user's Home timeline: Get /users/{userId}/timelines/home
* Get a single user's Tweet timeline: Get /users/{userId}/timelines/tweet

### UML Project:

![Diagram User - Post](medias/diagram_2.png)


## Project details:

24/02/2025 -> Starting using H2 database, because it's easy to configuration and really powerfull for testing. Each commit will have a message to explain changes between commits.

27/02/2025 -> New entity called PostLike created for store data about who gave a like on each post, and wasn't necessary more fields like date or hour, because for now, those informations aren't necessary. Some changes was made on User and Post enity, removing the ID on constructor, the Spring will take this work.

28/02/2025 -> Created a PostRT entity using the same structure from PostLike, UserFollow was create too. Diagram image was change to insert new structure about the project. The first part for now is complet, the next step will be services and controllers.

06/03/2025 -> First service created, for now just for list all Users; Record class used to DTO. In this part i've got some problems to integrate everything, in first time i tried to use a Set Post entity in Record class, but after some reasearch i prefer just create a Set Long and ask for ids, this will be better in performance.

08/03/2025 -> First component created, Post and User convert DTO inside a class to organize the application. This part was really complicated, because i didn't know how insert PostRT and Likes to convert, after some tries i got it. Now it's possible to list all Users and Posts. The next step is adding some filters, like search by id or name.

10/03/2025 -> FindById implemented in both classes; In this part was necessary handler some exceptions and return a "friendly" message for the user, so, two custom exceptions was created (UserNotFoundException and PostNotFoundException), and the package infra are used to handle the error message and controller exceptions.

11/03/2025 -> Email added for user, and new filters implemented, now it is possible to search a post content(isn't necessary all text, just a piece of the text) and search users by email, for this was necessary add a new component, the class URL, to decode text using Http main process.

15/03/2025 -> Authentication process, that was really complicated... At the first part, all process was inside AUthenticationController class, but it was a mess, controller consulting Entity without DTO, AuthenticationService and more. After a few days, and testing every day i found the solution and organize better the code. Now it's possible register new user and default role is USER, and login, returning a token to authenticate other requests.

21/03/2025 -> Created a method to insert new posts, that part was "easy", but for a few days, i forgot a little part POST NEEDS A OWNER, so every test i made return a NullPointerException, and i didn't understand why, so after think about i remember this little problem. This part will be commit in another day, because i forgot to write before push my last commit.

31/03/2025 -> In this moment was add delete method, but for that is necessary insert the correct token, username and password, if the username or password was incorrect a exception is launched UserNotDeletedException, if all information was correct, the user receive a message informing the success about the operation. For delete a user, the endpoint is the same for findAll(), but is necessary change the method to **DELETE**.

03/04/2025 -> Delete method for post was add, the user only can delete your OWN posts. This was the first time that i insert a method that receive two different parameters, the username and password and the post id.

04/04/2025 -> Now it is possible check information about following and followed users, two records was add to give a response for each method.

## Endpoints

* **User**
  * **GET** 
    * **findAll()** -> http://localhost:8080/users
    * **findById()** -> http://localhost:8080/users/{id}
    * **findByEmail()** -> http://localhost:8080/users/emailsearch?text={user@email.com}
    * **getFollowed()** -> http://localhost:8080/users/{userId}/followed
    * **getFollowing()** -> http://localhost:8080/users/{userId}/following
  * **DELETE**
    * **delete()** -> http://localhost:8080/users
  * **UPDATE**
    * **update()** -> http://localhost:8080/users

* **Post**
  * **GET** 
    * **findAll()** -> http://localhost:8080/posts
    * **findById()** -> http://localhost:8080/posts/{id}
    * **findbyContent** -> http://localhost:8080/posts/contentsearch?text={part of text}
  * **POST**
    * **insert()** -> http://localhost:8080/posts/insert
  * **DELETE**
      * **delete()** -> http://localhost:8080/posts

* **Authentication**
  * **POST**
    * **register()** -> http://localhost:8080/auth/register
    * **login()** -> http://localhost:8080/auth/login