JBargain for OzBargain.com.au
===================

JBargain is an unofficial Java-based API for OzBargain.com.au. There is currently only one implementation of the API
which uses web scraping to fetch data from the website.


## Usage Examples

### Instantiation
```java
JBargain api = new ScraperJBargain();
```

### Retrieve a post
```java
DealPost post = (DealPost) api.getPostById("1");

post.getTitle(); // 20% Off Everything in Stock at Kooring, 1-4 November
post.getAuthor().getUsername(); // scotty
```

### Retrive post comments
```java
Post post = api.getPostById("1000");

Iterator<Comment> comments = post.getComments();
while (comments.hasNext()) {
    Comment comment = comments.next();
}
```

### Retrieve popular deals
```java
// It is up to you how you manage tags
Tag tag = new Tag("Popular Deals", "/deals/popular");

// Get the first 10 popular deals
Iterator<Teaser> teasers = api.getFeedByTag(tag);
int count = 0;
while (teasers.hasNext() && count++ < 10) {
    teasers.next().getTitle();
}
```

### Authenticate a User
```java
User user = api.authenticateUser("username", "password");

user.replyTo(post)
    .addContent("Plenty of stock at my store")
    .setAssociated(true)
    .reply();

user.vote(comment, Vote.POSITIVE);
```


 