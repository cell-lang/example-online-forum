import java.time.LocalDate;
import java.util.Optional;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;


class User {
  public int        id;
  public String     username;
  public LocalDate  signupDate;
  public String     firstName;
  public String     lastName;
  public LocalDate  dateOfBirth;

  public Set<Membership> memberships = new HashSet<Membership>();
  public Set<Friendship> friendships = new HashSet<Friendship>();

  public OnlineForum onlineForum;


  public void join(Group group, LocalDate date) {
    Membership membership = new Membership(this, group, date, 0);
    memberships.add(membership);
    group.addMembership(membership);
  }

  public void befriend(User user, LocalDate since) {
    Friendship friendship = new Friendship(this, user, since);
    friendships.add(friendship);
    user.friendships.add(friendship);
  }

  public void delete() {
    onlineForum.remove(this);

    for (Membership m : memberships)
      m.group.removeMember(this);

    for (Friendship f : friendships) {
      User otherUser = f.user1 == this ? f.user2 : f.user1;
      otherUser.friendships.remove(this);
    }
  }

  public void leaveGroup(Group group) {
    if (memberships.remove(group))
      group.removeMember(this);
  }
}


class Group {
  public int    id;
  public String name;
  public User   admin;

  public Set<Membership> memberships = new HashSet<Membership>();

  public OnlineForum onlineForum;


  public void addMembership(Membership membership) {
    memberships.add(membership);
  }

  public void delete() {
    onlineForum.remove(this);

    for (Membership m : memberships)
      m.user.leaveGroup(this);
  }

  public void removeMember(User user) {
    if (memberships.remove(user)) {
      user.leaveGroup(this);
      if (user == admin) {
        Optional<Membership> newAdminMembership = memberships.stream().collect(
          Collectors.maxBy((m1, m2) -> m1.karma - m2.karma)
        );
        if (newAdminMembership.isPresent())
          admin = newAdminMembership.get().user;
        else
          delete();
      }
    }
  }
}


class Membership {
  public User       user;
  public Group      group;
  public LocalDate  joinedOn;
  public int        karma;


  public Membership(User user, Group group, LocalDate joinedOn, int karma) {
    this.user = user;
    this.group = group;
    this.joinedOn = joinedOn;
    this.karma = karma;
  }
}


class Friendship {
  public User       user1;
  public User       user2;
  public LocalDate  since;


  public Friendship(User user1, User user2, LocalDate since) {
    this.user1 = user1;
    this.user2 = user2;
    this.since = since;
  }
}


class OnlineForum {
  int nextId = 0;

  public Set<User>  users = new HashSet<User>();
  public Set<Group> groups = new HashSet<Group>();

  MultiMap<LocalDate, User> usersBySignupDate = new MultiMap<LocalDate, User>();


  public void add(User user) {
    users.add(user);
    usersBySignupDate.add(user.signupDate, user);
  }

  public void remove(User user) {
    if (users.remove(user)) {
      user.delete();
      usersBySignupDate.remove(user.signupDate, user);
    }
  }

  public void remove(Group group) {
    if (groups.remove(group))
      group.delete();
  }

  public Collection<User> usersWhoSignedUpOn(LocalDate date) {
    return usersBySignupDate.get(date);
  }
}

////////////////////////////////////////////////////////////////////////////////

class MultiMap<K, V> {
  Map<K, Set<V>> map = new HashMap<K, Set<V>>();

  public Collection<V> get(K key) {
    Set<V> values = map.get(key);
    return values != null ? values : new HashSet<V>();
  }

  public void add(K key, V value) {
    Set<V> values = map.get(key);
    if (values == null) {
      values = new HashSet<V>();
      map.put(key, values);
    }
    values.add(value);
  }

  public void remove(K key, V value) {
    Set<V> values = map.get(key);
    if (values != null)
      values.remove(value);
  }
}