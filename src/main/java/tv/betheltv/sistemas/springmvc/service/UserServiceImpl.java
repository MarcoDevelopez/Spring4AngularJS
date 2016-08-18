package tv.betheltv.sistemas.springmvc.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import tv.betheltv.sistemas.springmvc.model.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	private static final AtomicLong	counter = new AtomicLong();
	private static List<User>				users;
	
	static {
		users = populateDummyUsers();
	}

	@Override
	public User findById(long id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	@Override
	public User findByName(String name) {
		for (User user : users) {
			if (user.getUsername().equalsIgnoreCase(name)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public void saveUser(User user) {
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	@Override
	public void updateUser(User user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	@Override
	public void deleteUserById(long id) {
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			if (user.getId() == id) {
				iterator.remove();
			}
		}
	}

	@Override
	public List<User> findAllUsers() {
		return users;
	}

	@Override
	public void deleteAllUsers() {
		users.clear();
	}

	@Override
	public boolean isUserExist(User user) {
		return findByName(user.getUsername()) != null;
	}
	
	private static List<User> populateDummyUsers() {
		List<User> users = new ArrayList<User>();
		users.add(new User(counter.incrementAndGet(), "emoya", "San Martin de Porres", "emoya@betheltv.pe"));
		users.add(new User(counter.incrementAndGet(), "ohuipa", "Rimac", "ohuipa@betheltv.pe"));
		users.add(new User(counter.incrementAndGet(), "mlopez", "San Juan de Miraflores", "mlopez@betheltv.pe"));
		return users;
	}

}
