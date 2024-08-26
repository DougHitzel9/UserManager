package com.dhitzel.server.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.dhitzel.server.entity.Role;
import com.dhitzel.server.entity.SecureUser;
import com.dhitzel.server.entity.User;

@Repository
public class UserRepository extends AbstractRepository {

    public int delete(long id) throws Exception {

        String delete = "DELETE FROM user WHERE user_id = ?";
 
        Object[] params = new Object[1];
        params[0] = id;

        return delete(delete, params);
    }

    public int execute() throws Exception {

        String execute = "CALL RESET()";
 
        Object[] params = new Object[0];

        return delete(execute, params);
    }

    /**
     * Does not supress warnings. Cast each element of Collection to Object type.
     * 
     * @return
     * @throws Exception
     */
    public List<User> findAll() throws Exception {
        List<User> userList = new ArrayList<>();

        String query = "SELECT u.user_id, u.username, u.password, r.role_id, r.role FROM users u " +
                       "JOIN role r ON r.role_id = u.role_id";

        UserListResultSetHandler resultSetHandler = new UserListResultSetHandler();

        List<?> genericList = listQuery(query, resultSetHandler);

        for (int i=0; i<genericList.size(); i++) {
            User user = (User) genericList.get(i);
            userList.add(user);
        }
        return userList;
    }  

    @SuppressWarnings("unchecked")
    public List<User> findByName(String name) throws Exception {

        String query = "SELECT u.user_id, u.username, u.password, r.role_id, r.role FROM users u " +
                       "JOIN role r ON r.role_id = u.role_id " +
                       "WHERE lower(u.name) LIKE ?)";

        Object[] params = new Object[1];
        params[0] = "%" + name.toLowerCase() + "%";

        UserListResultSetHandler resultSetHandler = new UserListResultSetHandler();

        return (List<User>) listQuery(query, resultSetHandler, params);
    }  

    public User findById(long id) throws Exception {

        String query = "SELECT u.user_id, u.username, u.password, r.role_id, r.role FROM users u " +
                       "JOIN role r ON r.role_id = u.role_id " +
                       "WHERE user_id = ?";

        Object[] params = new Object[1];
        params[0] = id;

        UserResultSetHandler resultSetHandler = new UserResultSetHandler();

        return (User) query(query, resultSetHandler, params);
    }

    public SecureUser findSecureUser(String username) throws UsernameNotFoundException {

        String query = "SELECT u.user_id, u.username, u.password, r.role_id, r.role FROM users u " +
                       "JOIN role r ON r.role_id = u.role_id " +
                       "WHERE username = ?";

        Object[] params = new Object[1];
        params[0] = username;

        UserResultSetHandler resultSetHandler = new UserResultSetHandler();

        try {
            User user = (User) query(query, resultSetHandler, params);

            if (user == null) {
                String message = "Unable to retrieve username " + username;
 
                logger.info(message);
 
                throw new Exception(message);
            }
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            SecureUser secureUser = new SecureUser(user.getUsername(), user.getPassword(), grantedAuthorities);

            logger.info("findSecureUser() - " + secureUser.getPassword());

            return secureUser;    
        } catch (Exception e) {
            logger.warn("findSecureUser() - UsernameNotFoundException - " + e);
 
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    public Long insert(User user) throws Exception {

        String insert = "INSERT INTO users ( username, password, role_id ) VALUES ( ?, ?, ? ) RETURNING user_id";

        String username = user.getUsername();
        String password = "$2y$06$6AJQwwi.o6cvRCpY5mgW7ONuDFgutTAPdk8/Tikxtg120ongnLokK";
 
        Role role = user.getRole();
 
        Object[] params = new Object[3];
        params[0] = username;
        params[1] = password;
        params[2] = role.getRoleId();

        NewUserResultSetHandler resultSetHandler = new NewUserResultSetHandler();

        return (Long) insert(insert, resultSetHandler, params);
    }

    public int update(User user) throws Exception {

        String update = "UPDATE users SET role_id = ? WHERE user_id = ?";

        Long userId = user.getUserId();

        Role role = user.getRole();
 
        Object[] params = new Object[2];
        params[0] = role.getRoleId();
        params[1] = userId;

        return update(update, params);
     }

    /**
     * NewUserResultSetHandler
     */
    protected class NewUserResultSetHandler implements ResultSetHandler<Long> {
        public Long handle(ResultSet rs) {

            Long userId = null;

            try {
                if (rs.next()) {
                    userId = rs.getLong("user_id");
                }
            }
            catch (SQLException sqle) {
                logger.error("Unable to process ResultSet. Exception encountered - SQLException encountered - " + sqle);
            }

            return userId;
        }
    }

    /**
     * UserResultSetHandler
     */
    protected class UserResultSetHandler implements ResultSetHandler<User> {
        public User handle(ResultSet rs) {

            User user = null;

            try {
                if (rs.next()) {
                    long userId = rs.getLong("user_id");
                    long roleId = rs.getLong("role_id");

                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String title = rs.getString("role");

                    Role role = new Role(roleId, title);
 
                    user = new User(userId, username, password, role);
                }
            }
            catch (SQLException sqle) {
                logger.error("Unable to process ResultSet. Exception encountered - SQLException encountered - " + sqle);
            }

            return user;
        }
    }

    /**
     * UserListResultSetHandler
     */
    protected class UserListResultSetHandler implements ResultSetHandler<List<User>> {
        public List<User> handle(ResultSet rs) {

            List<User> userList = new ArrayList<User>();

            try {
                while (rs.next()) {
                    long userId = rs.getLong("user_id");
                    long roleId = rs.getLong("role_id");

                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String title = rs.getString("role");

                    Role role = new Role(roleId, title);

                    User user = new User(userId, username, password, role);
                    userList.add(user);
                }
            }
            catch (SQLException sqle) {
                logger.error("Unable to process ResultSet. Exception encountered - SQLException encountered - " + sqle);
            }

            return userList;
        }
    }
}