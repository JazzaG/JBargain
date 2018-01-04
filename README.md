JBargain for OzBargain.com.au
===================

JBargain is an unofficial Java-based API for OzBargain.com.au. There is currently only one implementation of the API
which uses web scraping to fetch data from the website.


## Usage Examples
As of yet there's not a lot you can do with the API, and so this section won't have a lot of content until I start
implementing more features. Check out the Issues page to see what I have planned.

### Retrieve a post
```java
JBargain api = new ScraperJBargain();
DealPost post = (DealPost) api.getPostById("1");

post.getTitle(); // 20% Off Everything in Stock at Kooring, 1-4 November
post.getAuthor().getUsername(); // scotty
```

### Authenticate a User
```java
JBargain api = new ScraperJBargain();
User user = api.authenticateUser("username", "password");

user.replyTo(post, "Cheers, bought one", false);
user.replyTo(comment, "RRP is not a bargain!", false);
```


 