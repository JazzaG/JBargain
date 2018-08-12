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


## Milestones

### Initial release
 - [ ] Complete the implementation of existing interfaces
 - [ ] Handle different types of comments (moderator, unpublished)
 - [x] Modify User.vote() to accept a Votable instance
 - [ ] Accept post submissions
 - [ ] User profile support
 - [ ] Create methods to fetch post categories as Tag instances (i.e. forum categories, deal categories, 'New Deals', 
 etc)
 - [ ] Explore options to maintain granularity of interfaces where appropriate (e.g. Votable interface; not everything
  that can be voted on can be voted negatively, the list of voters cannot be retrieved, etc)
 - [ ] Ensure Android compatibility

### Some time in the future
 - [ ] Support for other methods of user authentication
 - [ ] Support for unpublished submissions
 - [ ] Account management
 - [ ] View revision history of submissions
 - [ ] Support for user notifications
 - [ ] Support for reports
 - [ ] Support for bookmarks
 - [ ] Support for subscriptions

### Far, far in the future 
 - [ ] Handle pages (pages as listed in the nav bar, product pages, etc)
 - [ ] Support for the live action feature (maybe)